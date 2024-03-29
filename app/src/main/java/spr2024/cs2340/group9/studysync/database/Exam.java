package spr2024.cs2340.group9.studysync.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Locale;

@Entity
public class Exam {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String location;
    public long startTime;
    public boolean notify;
    public int notifyBefore;


    public Exam(String name, String location, long startTime, boolean notify, int notifyBefore) {
        this.name = name;
        this.location = location;
        this.startTime = startTime;
        this.notify = notify;
        this.notifyBefore = notifyBefore;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime.getTime();
    }

    public Date getStartTime() {
        return new Date(startTime);
    }

    public Date getNotifyDate() {
        return new Date(startTime - (long) notifyBefore * 60 * 1000);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getNotifyBefore() {
        return notifyBefore;
    }

    public void setNotifyBefore(int notifyBefore) {
        this.notifyBefore = notifyBefore;
    }


    @Override
    @NotNull
    public String toString() {
        return String.format(Locale.getDefault(),
                "Exam %d: name: %s, startTime: %s, notifyBefore: %d",
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

    public int getId() {
        return id;
    }
}
