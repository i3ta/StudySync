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
    public boolean notify;
    public int notifyBefore;

    @Ignore
    static int currentId = -10;

    public Assignment(int id, String name, int courseId, long dueDate, boolean notify, int notifyBefore) {
        if (currentId < 0) {
            throw new IllegalStateException("Database has to be initialized before class can be constructed.");
        }
        this.id = id;
        this.name = name;
        this.courseId = courseId;
        this.dueDate = dueDate;
        this.notify = notify;
        this.notifyBefore = notifyBefore;
    }

    @Ignore
    public Assignment(String name, int courseId, long dueDate, boolean notify, int notifyBefore) {
        this(currentId++, name, courseId, dueDate, notify, notifyBefore);
    }

    @Ignore
    public Assignment(String name, int courseId, Date dueDate, boolean notify, int notifyBefore) {
        this(name, courseId, dueDate.getTime(), notify, notifyBefore);
    }

    @Ignore
    public Date getDueDate() {
        return new Date(dueDate);
    }

    @Ignore
    public Date getNotifyDate() {
        return new Date(dueDate - (long) notifyBefore * 60 * 1000);
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
