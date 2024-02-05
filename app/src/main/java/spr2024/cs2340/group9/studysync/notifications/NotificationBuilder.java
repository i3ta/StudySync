package spr2024.cs2340.group9.studysync.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import spr2024.cs2340.group9.studysync.MainActivity;
import spr2024.cs2340.group9.studysync.R;

public class NotificationBuilder {
    private static final String CHANNEL_ID = "studysync_notifications"; // Set your channel id
    private static Context context;
    private static boolean initialized = false;

    public static void init(Context context) {
        if (initialized) {
            return;
        }
        NotificationBuilder.context = context;
        buildNotificationChannel();
        initialized = true;
    }

    private static void buildNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = context.getString(R.string.channel_name);
            String desc = context.getString(R.string.channel_desc);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(desc);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 2000});
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void notify(String name, String desc) {
        buildNotification(name, desc);
    }

    private static void buildNotification(String name, String desc) {
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_ID)
                .setContentTitle(name)
                .setContentText(desc)
                .setSmallIcon(R.drawable.outline_nest_clock_farsight_analog_24)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(desc))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            builder.setChannelId(CHANNEL_ID);
        }
        // notificationId is a unique int for each notification that you must define
        int notificationId = 1;
        notificationManager.notify(notificationId, builder.build());
    }
}