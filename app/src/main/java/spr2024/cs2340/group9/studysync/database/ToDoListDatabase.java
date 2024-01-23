package spr2024.cs2340.group9.studysync.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ToDoList.class, ToDoListItem.class}, version = 1)
public abstract class ToDoListDatabase extends RoomDatabase {
    abstract ToDoListDao toDoListDao();
    abstract ToDoListItemDao toDoListItemDao();
}
