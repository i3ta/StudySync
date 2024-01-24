package spr2024.cs2340.group9.studysync.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity
public class Course {
    @PrimaryKey
    public int id;

    public String name;
    public String instructorName;
    public int color;
    public int notifyBefore;

    @Ignore
    public TimePeriod[] courseTimes;
    @Ignore
    private static int currentId = 0;

    public Course(String name, String instructorName, int color, int notifyBefore) {
        id = currentId++;
        this.name = name;
        this.instructorName = instructorName;
        this.color = color;
        this.notifyBefore = notifyBefore;
    }

    public Course(String name, String instructorName, int color, int notifyBefore, TimePeriod[] courseTimes) {
        this(name, instructorName, color, notifyBefore);
        this.courseTimes = courseTimes;
    }

    public Course(Course c) {
        id = c.id;
        name = c.name;
        instructorName = c.instructorName;
        color = c.color;
        notifyBefore = c.notifyBefore;
        if (c.courseTimes != null) {
            courseTimes = new TimePeriod[c.courseTimes.length];
            for (int i = 0; i < c.courseTimes.length; i++) {
                courseTimes[i] = new TimePeriod(c.courseTimes[i]);
            }
        }
    }

    @NonNull
    @Ignore
    @Override
    public String toString() {
        return String.format("<Class %d: courseName = %s, instructorName = %s, color = %d, " +
                        "notifyBefore = %d, courseTimes: " + Arrays.toString(courseTimes),
                id, name, instructorName, color, notifyBefore);
    }

    @Ignore
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Course c = (Course) o;
        if (courseTimes != null && c.courseTimes == null) {
            if (courseTimes.length != c.courseTimes.length) {
                return false;
            }
            if (courseTimes != c.courseTimes) {
                for (int i = 0; i < courseTimes.length; i++) {
                    if (!courseTimes[i].equals(c.courseTimes[i])) {
                        return false;
                    }
                }
            }
        } else if (courseTimes != c.courseTimes) {
            return false;
        }
        return id == c.id && name.equals(c.name) && instructorName.equals(c.instructorName)
                && color == c.color && notifyBefore == c.notifyBefore;
    }
}
