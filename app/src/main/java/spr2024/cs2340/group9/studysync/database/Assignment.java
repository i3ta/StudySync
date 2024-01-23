package spr2024.cs2340.group9.studysync.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Locale;

@Entity
public class Assignment {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public int courseId;
    public long dueDate;
    public int notifyBefore;

    @Ignore
    public Date getDueDate() {
        return new Date(dueDate);
    }

    @Ignore
    public void setDueDate(Date date) {
        dueDate = date.getTime();
    }

    @Ignore
    @Override
    @NotNull
    public String toString() {
        return String.format(Locale.getDefault(),
                "Assignment %d:\n" +
                "- name: %s\n" +
                "- courseId: %d\n" +
                "- dueDate: %s\n" +
                "- notifyBefore: %d",
                id, name, courseId, getDueDate(), notifyBefore);
    }
}
