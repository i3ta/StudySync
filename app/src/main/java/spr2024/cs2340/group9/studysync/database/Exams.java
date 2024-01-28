package spr2024.cs2340.group9.studysync.database;

import android.content.Context;

import androidx.room.Room;

import java.util.Date;

public class Exams {
    private static ExamDatabase db;
    private static ExamDao examDao;

    /**
     * Force static methods.
     */
    public Exams() {}

    /**
     * Create new instance of the Exam database.
     * @param applicationContext context of the current application; use getApplicationContext() to get
     */
    public static void init(Context applicationContext) {
        if (db == null) {
            db = Room.databaseBuilder(applicationContext, ExamDatabase.class, "Exam")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
            examDao = db.examDao();
        }
    }

    /**
     * Insert exams.
     * @param exams exams to insert
     */
    public static void insert(Exam... exams) {
        examDao.insert(exams);
    }

    /**
     * Delete an exam.
     * @param exam exam to delete
     */
    public static void delete(Exam exam) {
        examDao.delete(exam);
    }

    /**
     * Delete an exam with the id.
     * @param id exam id to delete
     */
    public static void delete(int id) {
        examDao.delete(id);
    }

    /**
     * Get all exams.
     * @return exams
     */
    public static Exam[] getAll() {
        return examDao.getAll();
    }

    /**
     * Get all exams between startTime and endTime.
     * @param startTime start of time frame
     * @param endTime end of time frame
     * @return exams
     */
//    public static Exam[] getBetween(long startTime, long endTime) {
//        return examDao.getBetween(startTime, endTime);
//    }

    /**
     * Get all exams between startTime and endTime.
     * @param startTime start of time frame
     * @param endTime end of time frame
     * @return exams
     */
    public static Exam[] getBetween(Date startTime, Date endTime) {
        return examDao.getBetween(startTime.getTime(), endTime.getTime());
    }

    /**
     * Clear Exam table.
     */
    public static void clear() {
        examDao.clear();
    }
}
