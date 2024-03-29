package spr2024.cs2340.group9.studysync.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CourseDao {
    /**
     * Insert course(s) into Course table in database.
     * Repeated courses will be REPLACED.
     * @param courses course(s) to insert to database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Course... courses);

    /**
     * Delete a course from the Course table in the database.
     * @param course course to delete from database
     */
    @Delete
    void delete(Course course);

    /**
     * Get all rows from Course database.
     * @return all rows from the Course database as Course objects
     */
    @Query("SELECT * FROM Course")
    Course[] getAll();

    /**
     * Get course with the provided courseId.
     * @param courseId course to find
     * @return course with id = courseId
     */
    @Query("SELECT * FROM Course WHERE id = :courseId")
    Course get(int courseId);

    /**
     * Get courses that have the provided courseIds.
     * @param courseIds courses to find
     * @return courses where id is in courseIds
     */
    @Query("SELECT * FROM Course WHERE id IN (:courseIds)")
    Course[] get(int[] courseIds);

    /**
     * Clear Course table.
     */
    @Query("DELETE FROM Course")
    void clear();

    /**
     * Get maximum current id.
     * @return id
     */
    @Query("SELECT MAX(id) from Course")
    int getId();
}
