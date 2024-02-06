package spr2024.cs2340.group9.studysync.notifications;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import spr2024.cs2340.group9.studysync.database.Assignment;
import spr2024.cs2340.group9.studysync.database.Assignments;
import spr2024.cs2340.group9.studysync.database.Course;
import spr2024.cs2340.group9.studysync.database.Courses;
import spr2024.cs2340.group9.studysync.database.Exam;
import spr2024.cs2340.group9.studysync.database.Exams;

public class NotificationWorker extends Worker {
    public NotificationWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params){
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        System.out.println("NotificationWorker doWork...");
        NotificationBuilder.init(getApplicationContext());
        generateNotificationsForCourses();
        generateNotificationsForAssignments();
        generateNotificationsForExams();

        // notification loop
        WorkRequest request = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setInitialDelay(timeToNextMinute(), TimeUnit.MILLISECONDS)
                .build();
        WorkManager.getInstance(getApplicationContext()).enqueue(request);
        insertDatabase(request.getId());

        return Result.success();
    }

    private long timeToNextMinute() {
        Calendar calendar = Calendar.getInstance();
        int seconds = 60 - calendar.get(Calendar.SECOND);
        int millis = 1000 - calendar.get(Calendar.MILLISECOND);
        return seconds * 1000L + millis;
    }

    private void insertDatabase(UUID id) {
        NotificationDatabaseHelper.init(getApplicationContext());
        NotificationDatabaseHelper.insert(id);
    }

    private Date truncTime(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private boolean sameTime(Date date1, Date date2) {
        return truncTime(date1).equals(truncTime(date2));
    }

    private void generateNotificationsForCourses() {
        Courses.init(getApplicationContext());
        Calendar cal = Calendar.getInstance();
        Course[] courses = Courses.getOnDay(cal.get(Calendar.DAY_OF_WEEK) - 1);
        for (Course c: courses) {
            if (!c.notify) {
                continue;
            }
            Date nextStart = c.getNextStart();
            Date notif = new Date(nextStart.getTime() - (long) c.notifyBefore * 60 * 1000);
            if (sameTime(cal.getTime(), notif)) {
                String notifTitle = String.format("Upcoming Course: %s", c.name);
                String notifDesc = String.format(Locale.getDefault(),
                        "The course \"%s\" is starting %s.",
                        c.name,
                        (c.notifyBefore == 0 ? "now" : String.format(Locale.getDefault(), "in %d minutes", c.notifyBefore)));
                NotificationBuilder.notify(notifTitle, notifDesc);
            }
        }
    }

    private void generateNotificationsForAssignments() {
        Assignments.init(getApplicationContext());
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, -2); // If time is just after the creation time database won't get it, so  we increase the range
        Calendar tmr = Calendar.getInstance();
        tmr.add(Calendar.DATE, 1);
        Assignment[] assignments = Assignments.getBetween(now.getTime(), tmr.getTime());
        now.add(Calendar.MINUTE, 2);
        for (Assignment a: assignments) {
            if (!a.notify) {
                continue;
            }
            if (sameTime(a.getNotifyDate(), now.getTime())) {
                String notifTitle = String.format("Upcoming Assignment: %s", a.name);
                String notifDesc = String.format(Locale.getDefault(),
                        "The assignment \"%s\" is due %s.",
                        a.name,
                        (a.notifyBefore == 0 ? "now" : String.format(Locale.getDefault(), "in %d minutes", a.notifyBefore)));
                NotificationBuilder.notify(notifTitle, notifDesc);
            }
        }
    }

    private void generateNotificationsForExams() {
        Exams.init(getApplicationContext());
        Calendar now = Calendar.getInstance();
        Exam[] exams = Exams.getAll();
        for (Exam e: exams) {
            if (!e.notify) {
                continue;
            }
            if (sameTime(e.getNotifyDate(), now.getTime())) {
                String notifTitle = String.format("Upcoming Exam: %s", e.name);
                String notifDesc = String.format(Locale.getDefault(),
                        "The exam \"%s\" is occurring %s.",
                        e.name,
                        (e.notifyBefore == 0 ? "now" : String.format(Locale.getDefault(), "in %d minutes", e.notifyBefore)));
                NotificationBuilder.notify(notifTitle, notifDesc);
            }
        }
    }
}
