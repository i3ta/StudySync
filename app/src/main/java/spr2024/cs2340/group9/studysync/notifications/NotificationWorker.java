package spr2024.cs2340.group9.studysync.notifications;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Calendar;
import java.util.Date;

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
        NotificationBuilder.init(getApplicationContext());
        generateNotificationsForCourses();
        generateNotificationsForAssignments();
        generateNotificationsForExams();
        return Result.success();
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
                NotificationBuilder.notify(e.name, "", e.notifyBefore);
            }
        }
    }
}
