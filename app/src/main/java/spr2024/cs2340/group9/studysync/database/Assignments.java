package spr2024.cs2340.group9.studysync.database;

import android.content.Context;

import androidx.room.Room;

import java.util.Date;

/**
 * Class to interface with assignments database
 */
public class Assignments {
    private static AssignmentDatabase db;
    private static AssignmentDao assignmentDao;

    /**
     * Private empty constructor to force static methods.
     */
    private Assignments() {}

    /**
     * Initialize object.
     * @param applicationContext context of the current application; use getApplicationContext() to get
     */
    public static void init(Context applicationContext) {
        if (db == null) {
            db = Room.databaseBuilder(applicationContext, AssignmentDatabase.class,
                    "Assignment")
                    .allowMainThreadQueries()
                    .build();
            assignmentDao = db.assignmentDao();
        }
    }

    /**
     * Get all assignments.
     * @return assignments
     */
    public static Assignment[] getAll() {
        if (db == null) {
            throw new IllegalStateException("Database function can not be run because the database has not been initialized.");
        }
        return assignmentDao.getAll();
    }

    /**
     * Insert assignments into database.
     * @param assignments assignments
     */
    public static void insert(Assignment... assignments) {
        if (db == null) {
            throw new IllegalStateException("Database function can not be run because the database has not been initialized.");
        }
        assignmentDao.insert(assignments);
    }

    /**
     * Delete an assignment from the database.
     * @param assignment assignment to delete
     */
    public static void delete(Assignment assignment) {
        if (db == null) {
            throw new IllegalStateException("Database function can not be run because the database has not been initialized.");
        }
        assignmentDao.delete(assignment);
    }

    /**
     * Delete an assignment from the database based on the id.
     * @param id id of assignment to delete
     */
    public static void delete(int id) {
        if (db == null) {
            throw new IllegalStateException("Database function can not be run because the database has not been initialized.");
        }
        assignmentDao.delete(id);
    }

    /**
     * Get an assignment with the assignment id.
     * @param id assignment id
     * @return assignment
     */
    public static Assignment get(int id) {
        if (db == null) {
            throw new IllegalStateException("Database function can not be run because the database has not been initialized.");
        }
        return assignmentDao.get(id);
    }

    /**
     * Get all assignments associated with a course.
     * @param courseId course id
     * @return assignments
     */
    public static Assignment[] getCourse(int courseId) {
        if (db == null) {
            throw new IllegalStateException("Database function can not be run because the database has not been initialized.");
        }
        return assignmentDao.getCourse(courseId);
    }

    /**
     * Get all assignments with due dates within a time frame.
     * @param startDate start of time frame
     * @param endDate end of time frame
     * @return assignments
     */
    public static Assignment[] getBetween(Date startDate, Date endDate) {
        if (db == null) {
            throw new IllegalStateException("Database function can not be run because the database has not been initialized.");
        }
        return assignmentDao.getBetween(startDate.getTime(), endDate.getTime());
    }

    /**
     * Get all assignments with a specific ordering scheme.
     * @param order ordering scheme
     * @return assignments
     */
    public static Assignment[] getOrder(Order order) throws IllegalArgumentException {
        if (db == null) {
            throw new IllegalStateException("Database function can not be run because the database has not been initialized.");
        }
        if (order == Order.START_TIME || order == Order.END_TIME) {
            throw new IllegalArgumentException("Assignments cannot be ordered by START_TIME or" +
                    "END_TIME.");
        }
        return assignmentDao.getAll(order.columnName);
    }

    /**
     * Clear Assignment table.
     */
    public static void clear() {
        if (db == null) {
            throw new IllegalStateException("Database function can not be run because the database has not been initialized.");
        }
        assignmentDao.clear();
    }
}
