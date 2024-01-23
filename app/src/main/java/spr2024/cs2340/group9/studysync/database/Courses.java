package spr2024.cs2340.group9.studysync.database;

import android.content.Context;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class to interface between the DAO and the rest of the code
 */
public class Courses {
    private static CourseDatabase courseDB;
    private static CourseTimeDatabase courseTimeDB;
    private static CourseDao courseDao;
    private static CourseTimeDao courseTimeDao;

    /**
     * Create a new Courses object.
     * @param applicationContext context of the current application; use getApplicationContext() to get
     */
    public Courses(Context applicationContext) {
        if (courseDB == null) {
            courseDB = Room.databaseBuilder(applicationContext, CourseDatabase.class, "Course")
                    .build();
        }
        if (courseTimeDB == null) {
            courseTimeDB = Room.databaseBuilder(applicationContext, CourseTimeDatabase.class, "CourseTime")
                    .build();
        }
        if (courseDao == null) {
            courseDao = courseDB.courseDao();
        }
        if (courseTimeDao == null) {
            courseTimeDao = courseTimeDB.courseTimeDao();
        }
    }

    /**
     * Get all courses.
     * @return courses
     */
    public Course[] getAll() {
        Course[] courses = courseDao.getAll();
        for (Course course : courses) {
            course.courseTimes = toTimePeriod(courseTimeDao.get(course.id));
        }
        return courses;
    }

    /**
     * Insert courses.
     * @param courses courses
     */
    public void insert(Course... courses) {
        courseDao.insert(courses);
        ArrayList<CourseTime> courseTimes = new ArrayList<>();
        for (Course course: courses) {
            courseTimes.addAll(Arrays.asList(toCourseTime(course.id, course.courseTimes)));
        }
        CourseTime[] arr = new CourseTime[courseTimes.size()];
        courseTimeDao.insert(courseTimes.toArray(arr));
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
        course.courseTimes = toTimePeriod(courseTimeDao.get(courseId));
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
            course.courseTimes = toTimePeriod(courseTimeDao.get(course.id));
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
    public Course[] getBetween(RecurringTime startTime, RecurringTime endTime) throws IllegalArgumentException {
        if (startTime.compareTo(endTime) > 0) {
            throw new IllegalArgumentException(String.format("The start time (%s) cannot be before " +
                    "the end time (%s).", startTime, endTime));
        }
        int[] courseIds = courseTimeDao.getCourseIdBetween(startTime.getMinutesSinceSunday(),
                endTime.getMinutesSinceSunday());
        Course[] courses = courseDao.get(courseIds);
        for (Course course: courses) {
            course.courseTimes = toTimePeriod(courseTimeDao.get(course.id));
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
        return getBetween(new RecurringTime(dayOfWeek, 0, 0),
                new RecurringTime(dayOfWeek, 23, 59));
    }

    /**
     * Private helper class to convert time periods to course times
     * @param courseId course id for the time periods
     * @param timePeriods time periods
     * @return course times
     */
    private CourseTime[] toCourseTime(int courseId, TimePeriod[] timePeriods) {
        CourseTime[] times = new CourseTime[timePeriods.length];
        for (int i = 0; i < timePeriods.length; i++) {
            times[i] = timePeriods[i].getCourseTime(courseId);
        }
        return times;
    }

    /**
     * Private helper class to convert course times to time periods
     * @param courseTimes course times
     * @return time periods
     */
    private TimePeriod[] toTimePeriod(CourseTime[] courseTimes) {
        TimePeriod[] periods = new TimePeriod[courseTimes.length];
        for (int i = 0; i < courseTimes.length; i++) {
            periods[i] = new TimePeriod(courseTimes[i]);
        }
        return periods;
    }
}
