package spr2024.cs2340.group9.studysync.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@Entity
public class ToDoList {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    @Ignore
    public ToDoListItem[] toDoListItems;

    @Ignore
    @Override
    @NotNull
    public String toString() {
        return String.format(Locale.getDefault(),
                "To-Do List %d:\n" +
                        "- name: %s\n" +
                        "- items: %s",
                id, name, toDoListItems);
    }
}
