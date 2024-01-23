package spr2024.cs2340.group9.studysync.database;

import android.content.Context;

import androidx.room.Room;

import java.util.Date;

public class Exams {
    private static ExamDatabase db;
    private static ExamDao examDao;

    /**
     * Create new instance of the Exam database.
     * @param applicationContext context of the current application; use getApplicationContext() to get
     */
    public Exams(Context applicationContext) {
        if (db == null) {
            db = Room.databaseBuilder(applicationContext, ExamDatabase.class, "Exam").build();
            examDao = db.examDao();
        }
    }

    /**
     * Insert exams.
     * @param exams exams to insert
     */
    public void insert(Exam... exams) {
        examDao.insert(exams);
    }

    /**
     * Delete an exam.
     * @param exam exam to delete
     */
    public void delete(Exam exam) {
        examDao.delete(exam);
    }

    /**
     * Delete an exam with the id.
     * @param id exam id to delete
     */
    public void delete(int id) {
        examDao.delete(id);
    }

    /**
     * Get all exams.
     * @return exams
     */
    public Exam[] getAll() {
        return examDao.getAll();
    }

    /**
     * Get all exams between startTime and endTime.
     * @param startTime start of time frame
     * @param endTime end of time frame
     * @return exams
     */
    public Exam[] getBetween(Date startTime, Date endTime) {
        return examDao.getBetween(startTime.getTime(), endTime.getTime());
    }

    /**
     * Get all exams with a specific ordering scheme.
     * @param order ordering scheme
     * @return exams
     * @throws IllegalArgumentException if Order.DUE_DATE is passed in
     */
    public Exam[] getAll(Order order) throws IllegalArgumentException {
        if (order == Order.DUE_DATE) {
            throw new IllegalArgumentException("Exams can not be ordered by due date.");
        }
        return examDao.getAll(order.columnName);
    }
}
