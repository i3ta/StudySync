package spr2024.cs2340.group9.studysync.notifications;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import spr2024.cs2340.group9.studysync.MainActivity;
import spr2024.cs2340.group9.studysync.R;

public class NotificationBuilder {
    private static String CHANNEL_ID = "studysync_notifications"; // Set your channel id
    private static Context context;
    private static Activity activity;
    private static boolean initialized = false;

    public static void init(Context context) {
        if (initialized) {
            return;
        }
        NotificationBuilder.context = context;
        NotificationBuilder.activity = activity;
        buildNotificationChannel();
        initialized = true;
    }

    private static void buildNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            System.out.println("Building Notification Channel...");
            String name = context.getString(R.string.channel_name);
            String desc = context.getString(R.string.channel_desc);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(desc);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void notify(String name, String desc, int timeToEvent) {
        buildNotification(name, String.valueOf(timeToEvent), desc);
    }

    private static void buildNotification(String name, String time, String desc) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(name)
                .setContentText(time)
                .setSmallIcon(R.drawable.outline_nest_clock_farsight_analog_24)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(desc))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define
        int notificationId = 1;
        notificationManager.notify(notificationId, builder.build());
    }
}