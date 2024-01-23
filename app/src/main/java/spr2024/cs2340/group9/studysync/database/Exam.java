package spr2024.cs2340.group9.studysync.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Exam {
    @PrimaryKey
    public int id;

    public String assignmentTitle;
    public int startTime;
    public int endTime;
    public int notifyBefore;
}
