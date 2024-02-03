package spr2024.cs2340.group9.studysync.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@Entity
public class ToDoListItem {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int toDoListId;
    public String name;
    public boolean complete;


    public ToDoListItem(int toDoListId, String name, boolean complete) {
        this.toDoListId = toDoListId;
        this.name = name;
        this.complete = complete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getToDoListId() {
        return toDoListId;
    }

    public void setToDoListId(int toDoListId) {
        this.toDoListId = toDoListId;
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
