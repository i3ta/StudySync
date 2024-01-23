package spr2024.cs2340.group9.studysync.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
class CourseTime {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int courseId;
    public int startTime;
    public int endTime;
}
