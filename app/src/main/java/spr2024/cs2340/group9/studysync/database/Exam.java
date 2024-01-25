package spr2024.cs2340.group9.studysync.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Locale;

@Entity
public class Exam {
    @PrimaryKey
    public int id;

    public String name;
    long startTime;
    long endTime;
    public int notifyBefore;

    @Ignore
    private static int currentId = 0;

    public Exam(int id, String name, long startTime, long endTime, int notifyBefore) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notifyBefore = notifyBefore;
    }

    @Ignore
    public Exam(String name, long startTime, long endTime, int notifyBefore) {
        this(currentId++, name, startTime, endTime, notifyBefore);
    }

    @Ignore
    public Exam(String name, Date startTime, Date endTime, int notifyBefore) {
        this(name, startTime.getTime(), endTime.getTime(), notifyBefore);
    }

    @Ignore
    public void setStartTime(Date startTime) {
        this.startTime = startTime.getTime();
    }

    @Ignore
    public Date getStartTime() {
        return new Date(startTime);
    }

    @Ignore
    public void setEndTime(Date endTime) {
        this.endTime = endTime.getTime();
    }

    @Ignore
    public Date getEndTime() {
        return new Date(endTime);
    }

    @Ignore
    @Override
    @NotNull
    public String toString() {
        return String.format(Locale.getDefault(),
                "Exam %d: name: %s, startTime: %s, endTime: %s, notifyBefore: %d",
                id, name, getStartTime(), getEndTime(), notifyBefore);
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
        Exam e = (Exam) o;
        return id == e.id && name.equals(e.name) && startTime == e.startTime && endTime == e.endTime
                && notifyBefore == e.notifyBefore;
    }
}
