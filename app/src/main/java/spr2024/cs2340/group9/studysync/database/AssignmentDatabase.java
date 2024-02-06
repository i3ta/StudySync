package spr2024.cs2340.group9.studysync.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Assignment.class}, version = 2)
abstract class AssignmentDatabase extends RoomDatabase {
    abstract AssignmentDao assignmentDao();
}
