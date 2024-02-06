package spr2024.cs2340.group9.studysync.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Exam.class}, version = 3)
public abstract class ExamDatabase extends RoomDatabase {
    public abstract ExamDao examDao();
}
