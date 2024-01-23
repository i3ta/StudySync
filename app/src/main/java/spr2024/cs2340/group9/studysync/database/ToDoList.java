package spr2024.cs2340.group9.studysync.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ToDoList {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
}
