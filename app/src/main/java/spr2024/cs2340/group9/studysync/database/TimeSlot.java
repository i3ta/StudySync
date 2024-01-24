package spr2024.cs2340.group9.studysync.database;

import androidx.annotation.NonNull;

import java.util.Locale;

public class TimeSlot {
    private int id;
    private int courseId;
    private RecurringSlot startTime;
    private RecurringSlot endTime;

    /**
     * Initialize new TimePeriod object with the given startTime and endTime.
     * @param startTime int representing how many minutes past 00:00 Sunday
     * @param endTime int representing how many minutes past 00:00 Sunday
     */
    public TimeSlot(int courseId, int startTime, int endTime) {
        id = CourseTime.getNewId();
        this.courseId = courseId;
        this.startTime = new RecurringSlot(startTime);
        this.endTime = new RecurringSlot(endTime);
    }

    /**
     * Create with Time objects.
     * @param startTime start time
     * @param endTime end time
     */
    public TimeSlot(int courseId, RecurringSlot startTime, RecurringSlot endTime) {
        this(courseId, startTime.getMinutesSinceSunday(), endTime.getMinutesSinceSunday());
    }

    /**
     * Initialize a new TimePeriod object with the given CourseTime object.
     * @param courseTime CourseTime object
     */
    public TimeSlot(CourseTime courseTime) {
        id = courseTime.id;
        courseId = courseTime.courseId;
        this.startTime = new RecurringSlot(courseTime.startTime);
        this.endTime = new RecurringSlot(courseTime.endTime);
    }

    /**
     * Copy a TimePeriod object.
     * @param timeSlot TimePeriod object to copy
     */
    public TimeSlot(TimeSlot timeSlot) {
        this.id = timeSlot.id;
        this.courseId = timeSlot.courseId;
        this.startTime = new RecurringSlot(timeSlot.startTime);
        this.endTime = new RecurringSlot(timeSlot.endTime);
    }

    /**
     * Get a new CourseTime object from this time period.
     * @return CourseTime object
     */
    public CourseTime toCourseTime() {
        return new CourseTime(id, courseId, startTime.getMinutesSinceSunday(),
                endTime.getMinutesSinceSunday());
    }

    /**
     * Get the course id for this time period.
     * @return course id
     */
    int getCourseId() {
        return courseId;
    }

    /**
     * Set the startTime and endTime.
     * @param startTime start time in minutes since Sunday 00:00
     * @param endTime end time in minutes since Sunday 00:00
     */
    public void set(int startTime, int endTime) {
        this.startTime = new RecurringSlot(startTime);
        this.endTime = new RecurringSlot(endTime);
    }

    /**
     * Set the start and end times with a CourseTime object.
     * @param courseTime object to get start and end time from
     */
    public void set(CourseTime courseTime) {
        id = courseTime.id;
        courseId = courseTime.courseId;
        startTime = new RecurringSlot(courseTime.startTime);
        endTime = new RecurringSlot(courseTime.endTime);
    }

    /**
     * Set the start time.
     * @param startTime start time, in minutes since Sunday 00:00
     * @throws IllegalArgumentException if the input time is invalid
     */
    public void setStartTime(int startTime) throws IllegalArgumentException {
        this.startTime = new RecurringSlot(startTime);
    }

    /**
     * Set the start time with a Time object.
     * @param startTime start time
     */
    public void setStartTime(RecurringSlot startTime) {
        this.startTime = new RecurringSlot(startTime);
    }

    /**
     * Set the end time.
     * @param endTime end time, in minutes since Sunday 00:00
     * @throws IllegalArgumentException if the input time is invalid
     */
    public void setEndTime(int endTime) throws IllegalArgumentException {
        this.endTime = new RecurringSlot(endTime);
    }

    /**
     * Set the end time with a Time object.
     * @param endTime end time
     */
    public void setEndTime(RecurringSlot endTime) {
        this.endTime = new RecurringSlot(endTime);
    }

    /**
     * Get the start time.
     * @return start time Time object
     */
    public RecurringSlot getStartTime() {
        return startTime;
    }

    /**
     * Get the end time.
     * @return end time Time object
     */
    public RecurringSlot getEndTime() {
        return endTime;
    }

    /**
     * Get a CourseTime object from this TimePeriod.
     * @param courseId courseId to attach to CourseTime
     * @return CourseTime object with the given courseId
     */
    public CourseTime getCourseTime(int courseId) {
        return new CourseTime(id, courseId, startTime.getMinutesSinceSunday(),
                endTime.getMinutesSinceSunday());
    }

    /**
     * Get a String with the information about this time period.
     * @return String about this period
     */
    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(),
                "(TimePeriod %d: Course: %d; From: %s; To: %s)",
                id, courseId, startTime, endTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimeSlot t = (TimeSlot) o;
        return id == t.id && courseId == t.courseId && startTime.equals(t.startTime)
                && endTime.equals(t.endTime);
    }
}
