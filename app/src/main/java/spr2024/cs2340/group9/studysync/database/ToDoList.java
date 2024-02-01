package spr2024.cs2340.group9.studysync.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Locale;

@Entity
public class ToDoList {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    @Ignore
    public ToDoListItem[] toDoListItems;


    public ToDoList(String name) {
        this.name = name;
    }


    @Ignore
    public ToDoList(ToDoList list) {
        name = list.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
