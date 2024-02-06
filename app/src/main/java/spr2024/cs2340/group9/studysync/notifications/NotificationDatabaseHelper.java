package spr2024.cs2340.group9.studysync.notifications;

import android.content.Context;

import androidx.room.Room;

import java.util.UUID;

public class NotificationDatabaseHelper {
    public static NotificationDatabase db;
    public static NotificationDao dao;

    private NotificationDatabaseHelper() {}

    public static void init(Context applicationContext) {
        if (db == null) {
            db = Room.databaseBuilder(applicationContext, NotificationDatabase.class, "NotificationRequests")
                    .allowMainThreadQueries()
                    .build();
            dao = db.getNotificationDao();
        }
    }

    public static NotificationRequest[] get() {
        return dao.get();
    }

    public static void insert(NotificationRequest request) {
        dao.insert(request);
    }

    public static void insert(UUID id) {
        dao.insert(new NotificationRequest(id));
    }

    public static void clear() {
        dao.clear();
    }
}
