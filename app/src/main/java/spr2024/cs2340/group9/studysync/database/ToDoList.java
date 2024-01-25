package spr2024.cs2340.group9.studysync.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Locale;

@Entity
public class ToDoList {
    @PrimaryKey
    public int id;

    public String name;

    @Ignore
    public ToDoListItem[] toDoListItems;

    @Ignore
    private static int currentId = 0;

    public ToDoList(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Ignore
    public ToDoList(String name) {
        this(currentId++, name);
    }

    @Ignore
    public ToDoList(ToDoList list) {
        id = list.id;
        name = list.name;
    }

    @Ignore
    @Override
    @NotNull
    public String toString() {
        return String.format(Locale.getDefault(),
                "To-Do List %d: name: %s, items: %s",
                id, name, Arrays.toString(toDoListItems));
    }

    @Ignore
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ToDoList l = (ToDoList) o;
        return id == l.id && name.equals(l.name) && Arrays.equals(toDoListItems, l.toDoListItems);
    }
}
