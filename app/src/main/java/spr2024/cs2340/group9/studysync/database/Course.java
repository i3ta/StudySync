package spr2024.cs2340.group9.studysync.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Course {
    @PrimaryKey
    public int id;

    public String courseName;
    public String instructorName;
    public int color;
    public int notifyBefore;

    @Ignore
    public TimePeriod[] classTimes;
}
