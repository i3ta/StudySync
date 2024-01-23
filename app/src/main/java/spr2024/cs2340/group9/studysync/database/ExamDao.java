package spr2024.cs2340.group9.studysync.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ExamDao {
    /**
     * Insert exams.
     * @param exams exams
     */
    @Insert
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
    @Query("SELECT * FROM Exam WHERE startTime <= :startTime AND endTime >= :endTime")
    Exam[] getBetween(long startTime, long endTime);

    /**
     * Get all exams with a specific ordering scheme.
     * @param ordering ordering scheme
     * @return exams
     */
    @Query("SELECT * FROM Exam ORDER BY :ordering")
    Exam[] getAll(String ordering);
}
