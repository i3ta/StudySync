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

    @Ignore
    @Override
    @NotNull
    public String toString() {
        return String.format(Locale.getDefault(),
                "(item %d, list %d, %s, %s)",
                id, toDoListId, name, (complete ? "complete": "incomplete"));
    }
}
