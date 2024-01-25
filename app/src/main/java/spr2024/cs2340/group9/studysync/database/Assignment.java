package spr2024.cs2340.group9.studysync.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Locale;

@Entity
public class Assignment {
    @PrimaryKey
    public int id;

    public String name;
    public int courseId;
    public long dueDate;
    public int notifyBefore;

    @Ignore
    private static int currentId = 0;

    public Assignment(int id, String name, int courseId, long dueDate, int notifyBefore) {
        this.id = id;
        this.name = name;
        this.courseId = courseId;
        this.dueDate = dueDate;
        this.notifyBefore = notifyBefore;
    }

    @Ignore
    public Assignment(String name, int courseId, long dueDate, int notifyBefore) {
        this(currentId++, name, courseId, dueDate, notifyBefore);
    }

    @Ignore
    public Assignment(String name, int courseId, Date dueDate, int notifyBefore) {
        this(name, courseId, dueDate.getTime(), notifyBefore);
    }

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
                "Assignment %d: name: %s, courseId: %d, dueDate: %s, notifyBefore: %d",
                id, name, courseId, getDueDate(), notifyBefore);
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
        Assignment a = (Assignment) o;
        return id == a.id && name.equals(a.name) && courseId == a.courseId && dueDate == a.dueDate
                && notifyBefore == a.notifyBefore;
    }
}
