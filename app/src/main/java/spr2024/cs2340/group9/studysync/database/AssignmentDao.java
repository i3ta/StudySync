package spr2024.cs2340.group9.studysync.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
interface AssignmentDao {
    /**
     * Insert assignments into table.
     * @param assignments assignments to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Assignment... assignments);

    /**
     * Delete assignment from table.
     * @param assignment assignment to delete
     */
    @Delete
    void delete(Assignment assignment);

    /**
     * Delete assignment from table by id.
     * @param id id to delete
     */
    @Query("DELETE FROM Assignment WHERE id = :id")
    void delete(int id);

    /**
     * Get all assignments.
     * @return assignments
     */
    @Query("SELECT * FROM Assignment")
    Assignment[] getAll();

    /**
     * Get assignment with id.
     * Ordered by due date.
     * @param id assignment id
     * @return assignment
     */
    @Query("SELECT * FROM Assignment WHERE id = :id ORDER BY dueDate ASC")
    Assignment get(int id);

    /**
     * Get assignments for course.
     * Ordered by due date.
     * @param courseId course id
     * @return assignments
     */
    @Query("SELECT * FROM Assignment WHERE courseId = :courseId ORDER BY dueDate ASC")
    Assignment[] getCourse(int courseId);

    /**
     * Get assignments due within a specific time frame.
     * Ordered by due date.
     * @param startDate start time in milliseconds
     * @param endDate end time in milliseconds
     * @return assignments
     */
    @Query("SELECT * FROM Assignment WHERE dueDate >= :startDate AND dueDate <= :endDate ORDER BY dueDate ASC")
    Assignment[] getBetween(long startDate, long endDate);

    /**
     * CLear Assignment table.
     */
    @Query("DELETE FROM Assignment")
    void clear();

    /**
     * Get maximum id being used.
     * @return current maximum id
     */
    @Query("SELECT MAX(id) FROM Assignment")
    int getId();
}
