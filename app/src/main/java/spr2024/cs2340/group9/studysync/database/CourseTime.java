package spr2024.cs2340.group9.studysync.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
class CourseTime {
    @PrimaryKey
    public int id;

    public int courseId;
    public int startTime;
    public int endTime;

    @Ignore
    private static int currentId = 0;

    public CourseTime(int id, int courseId, int startTime, int endTime) {
        this.id = id;
        this.courseId = courseId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Ignore
    public CourseTime(int courseId, int startTime, int endTime) {
        this(currentId++, courseId, startTime, endTime);
    }

    @Ignore
    static int getNewId() {
        return currentId++;
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
        CourseTime t = (CourseTime) o;
        return id == t.id && courseId == t.id && startTime == t.startTime && endTime == t.endTime;
    }
}
