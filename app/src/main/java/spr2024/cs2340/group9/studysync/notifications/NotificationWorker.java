package spr2024.cs2340.group9.studysync.notifications;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWorker extends Worker {
    public NotificationWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params){
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Get notifications for courses
        // Get notifications for assignments
        // Get notifications for exams
        return Result.success();
    }
}
