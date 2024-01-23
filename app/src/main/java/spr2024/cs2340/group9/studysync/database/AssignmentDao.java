package spr2024.cs2340.group9.studysync.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

//TODO: Add get ordering
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
     * @param id assignment id
     * @return assignment
     */
    @Query("SELECT * FROM Assignment WHERE id = :id")
    Assignment get(int id);

    /**
     * Get assignments for course.
     * @param courseId course id
     * @return assignments
     */
    @Query("SELECT * FROM Assignment WHERE courseId = :courseId")
    Assignment[] getCourse(int courseId);
}
