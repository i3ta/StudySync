package spr2024.cs2340.group9.studysync.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CourseTime.class}, version = 1)
abstract class CourseTimeDatabase extends RoomDatabase {
    public abstract CourseTimeDao courseTimeDao();
}
