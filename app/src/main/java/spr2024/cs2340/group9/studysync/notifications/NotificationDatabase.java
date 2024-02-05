package spr2024.cs2340.group9.studysync.notifications;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {NotificationRequest.class}, version = 1)
public abstract class NotificationDatabase extends RoomDatabase {
    abstract NotificationDao getNotificationDao();
}
