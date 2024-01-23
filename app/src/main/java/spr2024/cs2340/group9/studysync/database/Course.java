package spr2024.cs2340.group9.studysync.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity
public class Course {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String instructorName;
    public int color;
    public int notifyBefore;

    @Ignore
    public TimePeriod[] courseTimes;

    @NonNull
    @Ignore
    @Override
    public String toString() {
        return String.format("Class %d:\n" +
                "- courseName = %s\n" +
                "- instructorName = %s\n" +
                "- color = %d\n" +
                "- notifyBefore = %d\n" +
                "- courseTimes: " + Arrays.toString(courseTimes),
                id, name, instructorName, color, notifyBefore);
    }
}
