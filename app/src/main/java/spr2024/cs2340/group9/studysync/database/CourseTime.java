package spr2024.cs2340.group9.studysync.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CourseTime {
    @PrimaryKey
    public int id;

    public int courseId;
    public int startTime;
    public int endTime;
}
