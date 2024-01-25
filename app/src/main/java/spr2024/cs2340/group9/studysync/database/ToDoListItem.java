package spr2024.cs2340.group9.studysync.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@Entity
public class ToDoListItem {
    @PrimaryKey
    public int id;

    public int toDoListId;
    public String name;
    public boolean complete;

    @Ignore
    static int currentId = -10;

    public ToDoListItem(int id, int toDoListId, String name, boolean complete) {
        if (currentId < 0) {
            throw new IllegalStateException("Database must be initialized before object can be constructed.");
        }
        this.id = id;
        this.toDoListId = toDoListId;
        this.name = name;
        this.complete = complete;
    }

    @Ignore
    public ToDoListItem(int toDoListId, String name, boolean complete) {
        this(currentId++, toDoListId, name, complete);
    }

    @Ignore
    @Override
    @NotNull
    public String toString() {
        return String.format(Locale.getDefault(),
                "(item %d, list %d, %s, %s)",
                id, toDoListId, name, (complete ? "complete": "incomplete"));
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
        ToDoListItem i = (ToDoListItem) o;
        return id == i.id && toDoListId == i.toDoListId && name.equals(i.name)
                && complete == i.complete;
    }
}
