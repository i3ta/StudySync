package spr2024.cs2340.group9.studysync.database;

import android.content.Context;

import androidx.room.Room;

/**
 * Class to interface between the DAO and the rest of the code
 */
public class Courses {
    private static CourseDatabase db;
    private static CourseDao courseDao;
    private static CourseTimeDao courseTimeDao;

    /**
     * Create a new Courses object.
     * @param applicationContext context of the current application; use getApplicationContext() to get
     */
    public Courses(Context applicationContext) {
        if (db == null) {
            db = Room.databaseBuilder(applicationContext, CourseDatabase.class, "Course")
                    .allowMainThreadQueries()
                    .build();
            courseDao = db.courseDao();
            courseTimeDao = db.courseTimeDao();
        }
    }

    /**
     * Get all courses.
     * @return courses
     */
    public Course[] getAll() {
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
    public void insert(Course... courses) {
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
    public void delete(Course course) {
        courseDao.delete(course);
        courseTimeDao.deleteCourse(course.id);
    }

    /**
     * Get a course with the course id.
     * @param courseId course id
     * @return course
     */
    public Course get(int courseId) {
        Course course = courseDao.get(courseId);
        course.setCourseTimes(toTimePeriod(courseTimeDao.get(courseId)));
        return course;
    }

    /**
     * Get courses with a course id.
     * @param courseIds course ids to get courses
     * @return courses
     */
    public Course[] get(int[] courseIds) {
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
    public Course[] getBetween(RecurringSlot startTime, RecurringSlot endTime) throws IllegalArgumentException {
        if (startTime.compareTo(endTime) > 0) {
            throw new IllegalArgumentException(String.format("The start time (%s) cannot be before " +
                    "the end time (%s).", startTime, endTime));
        }
        int[] courseIds = courseTimeDao.getCourseIdBetween(startTime.getMinutesSinceSunday(),
                endTime.getMinutesSinceSunday());
        Course[] courses = courseDao.get(courseIds);
        for (Course course: courses) {
            course.setCourseTimes(toTimePeriod(courseTimeDao.get(course.id)));
        }
        return courses;
    }

    /**
     * Get courses on a specific day of the week.
     * @param dayOfWeek day of the week
     * @return courses on the specified day of the week
     * @throws IllegalArgumentException if the day of the week is invalid
     */
    public Course[] getOnDay(int dayOfWeek) throws IllegalArgumentException {
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
    private CourseTime[] toCourseTime(TimeSlot[] timeSlots) {
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
    private TimeSlot[] toTimePeriod(CourseTime[] courseTimes) {
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
    public void clear() {
        courseDao.clear();
    }
}
