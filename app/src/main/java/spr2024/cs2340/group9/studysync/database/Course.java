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
    private TimeSlot[] courseTimes;
    @Ignore
    static int currentId = -10;

    public Course(int id, String name, String instructorName, int color, int notifyBefore) {
        if (currentId < 0) {
            throw new IllegalStateException("Database must be initialized before classes can be constructed.");
        }
        this.id = id;
        this.name = name;
        this.instructorName = instructorName;
        this.color = color;
        this.notifyBefore = notifyBefore;
    }

    @Ignore
    public Course(String name, String instructorName, int color, int notifyBefore) {
        this(currentId++, name, instructorName, color, notifyBefore);
    }

    public Course(String name, String instructorName, int color, int notifyBefore, TimeSlot[] courseTimes) {
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
            courseTimes = new TimeSlot[c.courseTimes.length];
            for (int i = 0; i < c.courseTimes.length; i++) {
                courseTimes[i] = new TimeSlot(c.courseTimes[i]);
            }
        }
    }

    @Ignore
    public TimeSlot[] getCourseTimes() {
        return courseTimes;
    }

    @Ignore
    public void setCourseTimes(TimeSlot[] courseTimes) {
        if (courseTimes == null) {
            this.courseTimes = null;
            return;
        }
        for (TimeSlot t: courseTimes) {
            if (t.getCourseId() != id) {
                throw new IllegalArgumentException("Course id must be equal to this course id");
            }
        }
        this.courseTimes = new TimeSlot[courseTimes.length];
        for (int i = 0; i < courseTimes.length; i++) {
            this.courseTimes[i] = new TimeSlot(courseTimes[i]);
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
        return id == c.id && name.equals(c.name) && instructorName.equals(c.instructorName)
                && color == c.color && notifyBefore == c.notifyBefore
                && Arrays.equals(courseTimes, c.courseTimes);
    }
}
