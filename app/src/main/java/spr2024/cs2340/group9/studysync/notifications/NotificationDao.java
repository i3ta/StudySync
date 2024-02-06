package spr2024.cs2340.group9.studysync.notifications;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NotificationDao {
    @Insert
    void insert(NotificationRequest... requests);

    @Query("SELECT * FROM NotificationRequest")
    NotificationRequest[] get();

    @Query("DELETE FROM NotificationRequest")
    void clear();
}
