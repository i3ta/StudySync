package spr2024.cs2340.group9.studysync.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class, CourseTime.class}, version = 1)
abstract class CourseDatabase extends RoomDatabase {
    public abstract CourseDao courseDao();
    public abstract CourseTimeDao courseTimeDao();
}
