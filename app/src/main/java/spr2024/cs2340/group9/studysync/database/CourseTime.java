package spr2024.cs2340.group9.studysync.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
class CourseTime {
    @PrimaryKey(autoGenerate = true)
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

    public CourseTime() {
        id = currentId++;
    }

    public CourseTime(int courseId, int startTime, int endTime) {
        this(currentId++, courseId, startTime, endTime);
    }

    @Ignore
    static int getNewId() {
        return currentId++;
    }
}
