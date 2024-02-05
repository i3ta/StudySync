package spr2024.cs2340.group9.studysync.notifications;

import android.app.Notification;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import spr2024.cs2340.group9.studysync.database.Assignment;
import spr2024.cs2340.group9.studysync.database.Assignments;
import spr2024.cs2340.group9.studysync.database.Course;
import spr2024.cs2340.group9.studysync.database.Courses;
import spr2024.cs2340.group9.studysync.database.Exam;
import spr2024.cs2340.group9.studysync.database.Exams;

public class NotificationWorker extends Worker {
    private static Date lastNotified;
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

        Date now = new Date();
        // notification loop
        WorkRequest request = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setInitialDelay(1, TimeUnit.MINUTES)
                .build();
        WorkManager.getInstance(getApplicationContext()).enqueue(request);
        insertDatabase(request.getId());

        return Result.success();
    }

    private void insertDatabase(UUID id) {
        NotificationDatabaseHelper.init(getApplicationContext());
        NotificationDatabaseHelper.insert(id);
    }

    private boolean sameTime(Date date1, Date date2) {
        return Math.abs(date1.getTime() - date2.getTime()) < 30 * 1000;
    }

    private void generateNotificationsForCourses() {
        Courses.init(getApplicationContext());
        Calendar cal = Calendar.getInstance();
        Course[] courses = Courses.getOnDay(cal.get(Calendar.DAY_OF_WEEK) - 1);
        for (Course c: courses) {
            Date nextStart = c.getNextStart();
            Date notif = new Date(nextStart.getTime() - (long) c.notifyBefore * 60 * 1000);
            if (sameTime(cal.getTime(), notif)) {
                NotificationBuilder.notify(c.name, "", c.notifyBefore);
            }
        }
    }

    private void generateNotificationsForAssignments() {
        Assignments.init(getApplicationContext());
        Calendar now = Calendar.getInstance();
        Calendar tmr = Calendar.getInstance();
        tmr.add(Calendar.DATE, 1);
        Assignment[] assignments = Assignments.getBetween(now.getTime(), tmr.getTime());
        for (Assignment a: assignments) {
            if (sameTime(a.getNotifyDate(), now.getTime())) {
                NotificationBuilder.notify(a.name, "", a.notifyBefore);
            }
        }
    }

    private void generateNotificationsForExams() {
        Exams.init(getApplicationContext());
        Calendar now = Calendar.getInstance();
        Exam[] exams = Exams.getAll();
        for (Exam e: exams) {
            if (sameTime(e.getNotifyDate(), now.getTime())) {
                System.out.printf("Notifying %s\n", e);
                NotificationBuilder.notify(e.name, "", e.notifyBefore);
            }
        }
    }
}
