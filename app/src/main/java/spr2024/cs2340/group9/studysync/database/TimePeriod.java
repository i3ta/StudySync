package spr2024.cs2340.group9.studysync.database;

import androidx.annotation.NonNull;

public class TimePeriod {
    private Time startTime;
    private Time endTime;

    /**
     * Initialize new TimePeriod object with the given startTime and endTime.
     * @param startTime int representing how many minutes past 00:00 Sunday
     * @param endTime int representing how many minutes past 00:00 Sunday
     */
    public TimePeriod(int startTime, int endTime) {
        this.startTime = new Time(startTime);
        this.endTime = new Time(endTime);
    }

    /**
     * Create with Time objects.
     * @param startTime start time
     * @param endTime end time
     */
    public TimePeriod(Time startTime, Time endTime) {
        this.startTime = new Time(startTime);
        this.endTime = new Time(endTime);
    }

    /**
     * Initialize a new TimePeriod object with the given CourseTime object.
     * @param courseTime CourseTime object
     */
    public TimePeriod(CourseTime courseTime) {
        this.startTime = new Time(courseTime.startTime);
        this.endTime = new Time(courseTime.endTime);
    }

    /**
     * Copy a TimePeriod object.
     * @param timePeriod TimePeriod object to copy
     */
    public TimePeriod(TimePeriod timePeriod) {
        this.startTime = new Time(timePeriod.startTime);
        this.endTime = new Time(timePeriod.endTime);
    }

    /**
     * Set the startTime and endTime.
     * @param startTime start time in minutes since Sunday 00:00
     * @param endTime end time in minutes since Sunday 00:00
     */
    public void set(int startTime, int endTime) {
        this.startTime = new Time(startTime);
        this.endTime = new Time(endTime);
    }

    /**
     * Set the start and end times with a CourseTime object.
     * @param courseTime object to get start and end time from
     */
    public void set(CourseTime courseTime) {
        this.startTime = new Time(courseTime.startTime);
        this.endTime = new Time(courseTime.endTime);
    }

    /**
     * Set the start time.
     * @param startTime start time, in minutes since Sunday 00:00
     * @throws IllegalArgumentException if the input time is invalid
     */
    public void setStartTime(int startTime) throws IllegalArgumentException {
        this.startTime = new Time(startTime);
    }

    /**
     * Set the start time with a Time object.
     * @param startTime start time
     */
    public void setStartTime(Time startTime) {
        this.startTime = new Time(startTime);
    }

    /**
     * Set the end time.
     * @param endTime end time, in minutes since Sunday 00:00
     * @throws IllegalArgumentException if the input time is invalid
     */
    public void setEndTime(int endTime) throws IllegalArgumentException {
        this.endTime = new Time(endTime);
    }

    /**
     * Set the end time with a Time object.
     * @param endTime end time
     */
    public void setEndTime(Time endTime) {
        this.endTime = new Time(endTime);
    }

    /**
     * Get the start time.
     * @return start time Time object
     */
    public Time getStartTime() {
        return startTime;
    }

    /**
     * Get the end time.
     * @return end time Time object
     */
    public Time getEndTime() {
        return endTime;
    }

    /**
     * Get a CourseTime object from this TimePeriod.
     * @param courseId courseId to attach to CourseTime
     * @return CourseTime object with the given courseId
     */
    public CourseTime getCourseTime(int courseId) {
        CourseTime period = new CourseTime();
        period.courseId = courseId;
        period.startTime = startTime.getMinutesSinceSunday();
        period.endTime = endTime.getMinutesSinceSunday();
        return period;
    }

    /**
     * Get a String with the information about this time period.
     * @return String about this period
     */
    @NonNull
    @Override
    public String toString() {
        return String.format("Time period between %s and %s.", startTime, endTime);
    }
}
