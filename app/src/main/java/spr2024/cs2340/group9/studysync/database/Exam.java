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
    public int notifyBefore;

    static int currentId = -10;

    public Exam(int id, String name, long startTime, int notifyBefore) {
        if (currentId < 0) {
            throw new IllegalStateException("Database must be initialized before object can be constructed.");
        }
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.notifyBefore = notifyBefore;
    }


    public Exam(String name, long startTime, long endTime, int notifyBefore) {
        this(currentId++, name, startTime, notifyBefore);
    }

    public Exam(String name, Date startTime, Date endTime, int notifyBefore) {
        this(name, startTime.getTime(), endTime.getTime(), notifyBefore);
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime.getTime();
    }

    public Date getStartTime() {
        return new Date(startTime);
    }


    @Override
    @NotNull
    public String toString() {
        return String.format(Locale.getDefault(),
                "Exam %d: name: %s, startTime: %s, endTime: %s, notifyBefore: %d",
                id, name, getStartTime(), notifyBefore);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Exam e = (Exam) o;
        return id == e.id && name.equals(e.name) && startTime == e.startTime
                && notifyBefore == e.notifyBefore;
    }
}
