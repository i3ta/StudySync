package spr2024.cs2340.group9.studysync.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
interface CourseTimeDao {
    /**
     * Insert course times into table.
     * Repeated course times will be REPLACED.
     * @param courseTimes course times to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CourseTime... courseTimes);

    /**
     * Delete course time from table.
     * @param courseTime course time to delete
     */
    @Delete
    void delete(CourseTime courseTime);

    /**
     * Delete course times from table with a specific courseId.
     * @param courseId course id to delete
     */
    @Query("DELETE FROM CourseTime WHERE courseId = :courseId")
    void deleteCourse(int courseId);

    /**
     * Get all course times.
     * @return array of CourseTime objects
     */
    @Query("SELECT * FROM CourseTime")
    CourseTime[] getAll();

    /**
     * Get all courses with a specific course id.
     * @param courseId course id
     * @return array of CourseTime objects with the course id
     */
    @Query("SELECT * FROM CourseTime WHERE courseId = :courseId")
    CourseTime[] get(int courseId);

    /**
     * Get course id of courses within a specific time frame.
     * @param startTime start of the time frame
     * @param endTime end of the time frame
     * @return int array of course ids
     */
    @Query("SELECT DISTINCT courseId FROM courseTime where endTime >= :startTime AND startTime <= :endTime")
    int[] getCourseIdBetween(int startTime, int endTime);

    /**
     * Clear CourseTime table.
     */
    @Query("DELETE FROM CourseTime")
    void clear();

    /**
     * Get maximum id from table.
     * @return id
     */
    @Query("SELECT MAX(id) from CourseTime")
    int getId();
}
