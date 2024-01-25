package spr2024.cs2340.group9.studysync.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ExamDao {
    /**
     * Insert exams.
     * @param exams exams
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Exam... exams);

    /**
     * Delete exam.
     * @param exam exam
     */
    @Delete
    void delete(Exam exam);

    /**
     * Delete exam with exam id.
     * @param id exam id
     */
    @Query("DELETE FROM Exam WHERE id = :id")
    void delete(int id);

    /**
     * Get all exams.
     * @return exams
     */
    @Query("SELECT * FROM Exam ORDER BY startTime")
    Exam[] getAll();

    /**
     * Get all exams within a specific time frame.
     * @param startTime start time
     * @param endTime end time
     * @return exams
     */
    @Query("SELECT * FROM Exam WHERE endTime >= :startTime AND startTime <= :endTime")
    Exam[] getBetween(long startTime, long endTime);

    /**
     * Clear Exam table.
     */
    @Query("DELETE FROM Exam")
    void clear();

    /**
     * Get maximum id from Exam table.
     * @return id
     */
    @Query("SELECT MAX(id) FROM Exam")
    int getId();
}
