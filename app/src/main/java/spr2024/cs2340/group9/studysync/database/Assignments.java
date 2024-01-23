package spr2024.cs2340.group9.studysync.database;

import android.content.Context;

import androidx.room.Room;

import java.util.Date;

/**
 * Class to interface with assignments database
 */
public class Assignments {
    private static AssignmentDatabase assignmentDB;
    private static AssignmentDao assignmentDao;

    /**
     * Initialize object.
     * @param applicationContext context of the current application; use getApplicationContext() to get
     */
    public Assignments(Context applicationContext) {
        if (assignmentDB == null) {
            assignmentDB = Room.databaseBuilder(applicationContext, AssignmentDatabase.class,
                    "Assignment").build();
        }
        if (assignmentDao == null) {
            assignmentDao = assignmentDB.assignmentDao();
        }
    }

    /**
     * Get all assignments.
     * @return assignments
     */
    public Assignment[] getAll() {
        return assignmentDao.getAll();
    }

    /**
     * Insert assignments into database.
     * @param assignments assignments
     */
    public void insert(Assignment... assignments) {
        assignmentDao.insert(assignments);
    }

    /**
     * Delete an assignment from the database.
     * @param assignment assignment to delete
     */
    public void delete(Assignment assignment) {
        assignmentDao.delete(assignment);
    }

    /**
     * Delete an assignment from the database based on the id.
     * @param id id of assignment to delete
     */
    public void delete(int id) {
        assignmentDao.delete(id);
    }

    /**
     * Get an assignment with the assignment id.
     * @param id assignment id
     * @return assignment
     */
    public Assignment get(int id) {
        return assignmentDao.get(id);
    }

    /**
     * Get all assignments associated with a course.
     * @param courseId course id
     * @return assignments
     */
    public Assignment[] getCourse(int courseId) {
        return assignmentDao.getCourse(courseId);
    }

    /**
     * Get all assignments with due dates within a time frame.
     * @param startDate start of time frame
     * @param endDate end of time frame
     * @return assignments
     */
    public Assignment[] getBetween(Date startDate, Date endDate) {
        return assignmentDao.getBetween(startDate.getTime(), endDate.getTime());
    }

    /**
     * Get all assignments with a specific ordering scheme.
     * @param order ordering scheme
     * @return assignments
     */
    public Assignment[] getOrder(Order order) {
        return assignmentDao.getAll(order.columnName);
    }
}
