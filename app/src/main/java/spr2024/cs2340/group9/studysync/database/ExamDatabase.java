package spr2024.cs2340.group9.studysync.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Exam.class}, version = 1)
public abstract class ExamDatabase extends RoomDatabase {
    abstract ExamDao examDao();
}
