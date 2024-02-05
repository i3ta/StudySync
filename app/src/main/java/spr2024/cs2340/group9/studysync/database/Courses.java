package spr2024.cs2340.group9.studysync.database;

import android.content.Context;

import androidx.room.Room;

import java.util.Arrays;

/**
 * Class to interface between the DAO and the rest of the code
 */
public class Courses {
    private static CourseDatabase db;
    private static CourseDao courseDao;
    private static CourseTimeDao courseTimeDao;

    /**
     * Private constructor to enforce static usage.
     */
    private Courses() {}

    /**
     * Create a new Courses object.
     * @param applicationContext context of the current application; use getApplicationContext() to get
     */
    public static void init(Context applicationContext) {
        if (db == null) {
            db = Room.databaseBuilder(applicationContext, CourseDatabase.class, "Course")
                    .allowMainThreadQueries()
                    .build();
            courseDao = db.courseDao();
            courseTimeDao = db.courseTimeDao();
            Course.currentId = courseDao.getId() + 1;
            CourseTime.currentId = courseTimeDao.getId() + 1;
        }
    }

    /**
     * Get all courses.
     * @return courses
     */
    public static Course[] getAll() {
        if (db == null) {
            throw new IllegalStateException("Database function can not be run because the database has not been initialized.");
        }
        Course[] courses = courseDao.getAll();
        for (Course course : courses) {
            course.setCourseTimes(toTimePeriod(courseTimeDao.get(course.id)));
        }
        return courses;
    }

    /**
     * Insert courses.
     * @param courses courses
     */
    public static void insert(Course... courses) {
        if (db == null) {
            throw new IllegalStateException("Database function can not be run because the database has not been initialized.");
        }
        for (Course course: courses) {
            courseTimeDao.deleteCourse(course.id);
            if (course.getCourseTimes() == null || course.getCourseTimes().length == 0) {
                continue;
            }
            courseTimeDao.insert(toCourseTime(course.getCourseTimes()));
        }
        courseDao.insert(courses);
    }

    /**
     * Delete a course
     * @param course course
     */
    public static void delete(Course course) {
        if (db == null) {
            throw new IllegalStateException("Database function can not be run because the database has not been initialized.");
        }
        courseDao.delete(course);
        courseTimeDao.deleteCourse(course.id);
    }

    /**
     * Get a course with the course id.
     * @param courseId course id
     * @return course
     */
    public static Course get(int courseId) {
        if (db == null) {
            throw new IllegalStateException("Database function can not be run because the database has not been initialized.");
        }
        Course course = courseDao.get(courseId);
        course.setCourseTimes(toTimePeriod(courseTimeDao.get(courseId)));
        return course;
    }

    /**
     * Get courses with a course id.
     * @param courseIds course ids to get courses
     * @return courses
     */
    public static Course[] get(int[] courseIds) {
        if (db == null) {
            throw new IllegalStateException("Database function can not be run because the database has not been initialized.");
        }
        Course[] courses = courseDao.get(courseIds);
        for (Course course: courses) {
            course.setCourseTimes(toTimePeriod(courseTimeDao.get(course.id)));
        }
        return courses;
    }

    /**
     * Get courses within a specific time frame.
     * @param startTime beginning of the time frame, inclusive
     * @param endTime end of the time frame, inclusive
     * @return courses in the time frame
     * @throws IllegalArgumentException if the startTime is after the endTime
     */
    public static Course[] getBetween(int startTime, int endTime) throws IllegalArgumentException {
        if (db == null) {
            throw new IllegalStateException("Database function can not be run because the database has not been initialized.");
        }
        if (startTime > endTime) {
            throw new IllegalArgumentException(String.format("The start time (%s) cannot be before " +
                    "the end time (%s).", startTime, endTime));
        }
        int[] courseIds = courseTimeDao.getCourseIdBetween(startTime, endTime);
        Course[] courses = new Course[courseIds.length];
        for (int i = 0; i < courseIds.length; i++) {
            courses[i] = courseDao.get(courseIds[i]);
        }
        for (Course course: courses) {
            course.setCourseTimes(toTimePeriod(courseTimeDao.get(course.id)));
        }
        return courses;
    }

    /**
     * Get courses within a specific time frame.
     * @param startTime beginning of the time frame, inclusive
     * @param endTime end of the time frame, inclusive
     * @return courses in the time frame
     * @throws IllegalArgumentException if the startTime is after the endTime
     */
    public static Course[] getBetween(RecurringSlot startTime, RecurringSlot endTime) throws IllegalArgumentException {
        return getBetween(startTime.getMinutesSinceSunday(), endTime.getMinutesSinceSunday());
    }

    /**
     * Get courses on a specific day of the week.
     * @param dayOfWeek day of the week
     * @return courses on the specified day of the week
     * @throws IllegalArgumentException if the day of the week is invalid
     */
    public static Course[] getOnDay(int dayOfWeek) throws IllegalArgumentException {
        if (db == null) {
            throw new IllegalStateException("Database function can not be run because the database has not been initialized.");
        }
        if (dayOfWeek < 0 || dayOfWeek > 6) {
            throw new IllegalArgumentException(String.format("Day of week %d is invalid. Day of " +
                    "week must be in the range [0, 6], where 0 is Sunday and 6 is Saturday.",
                    dayOfWeek));
        }
        return getBetween(new RecurringSlot(dayOfWeek, 0, 0),
                new RecurringSlot(dayOfWeek, 23, 59));
    }

    /**
     * Private helper class to convert time periods to course times
     * @param timeSlots time periods
     * @return course times
     */
    private static CourseTime[] toCourseTime(TimeSlot[] timeSlots) {
        if (db == null) {
            throw new IllegalStateException("Database function can not be run because the database has not been initialized.");
        }
        if (timeSlots == null || timeSlots.length == 0) {
            return null;
        }
        CourseTime[] times = new CourseTime[timeSlots.length];
        for (int i = 0; i < timeSlots.length; i++) {
            times[i] = timeSlots[i].toCourseTime();
        }
        return times;
    }

    /**
     * Private helper class to convert course times to time periods
     * @param courseTimes course times
     * @return time periods
     */
    private static TimeSlot[] toTimePeriod(CourseTime[] courseTimes) {
        if (db == null) {
            throw new IllegalStateException("Database function can not be run because the database has not been initialized.");
        }
        if (courseTimes == null || courseTimes.length == 0) {
            return null;
        }
        TimeSlot[] periods = new TimeSlot[courseTimes.length];
        for (int i = 0; i < courseTimes.length; i++) {
            periods[i] = new TimeSlot(courseTimes[i]);
        }
        return periods;
    }

    /**
     * Clears table.
     */
    public static void clear() {
        if (db == null) {
            throw new IllegalStateException("Database function can not be run because the database has not been initialized.");
        }
        courseDao.clear();
    }
}
