package spr2024.cs2340.group9.studysync.database;

import android.content.Context;

import androidx.room.Room;

import java.util.Date;

public class Exams {
    private static ExamDatabase examDB;
    private static ExamDao examDao;

    public Exams(Context applicationContext) {
        if (examDB == null) {
            examDB = Room.databaseBuilder(applicationContext, ExamDatabase.class, "Exam").build();
        }
        if (examDao == null) {
            examDao = examDB.examDao();
        }
    }

    public void insert(Exam... exams) {
        examDao.insert(exams);
    }

    public void delete(Exam exam) {
        examDao.delete(exam);
    }

    public void delete(int id) {
        examDao.delete(id);
    }

    public Exam[] getAll() {
        return examDao.getAll();
    }

    public Exam[] getBetween(Date startTime, Date endTime) {
        return examDao.getBetween(startTime.getTime(), endTime.getTime());
    }

    public Exam[] getAll(Order order) throws IllegalArgumentException {
        if (order == Order.DUE_DATE) {
            throw new IllegalArgumentException("Exams can not be ordered by due date.");
        }
        return examDao.getAll(order.columnName);
    }
}
