package spr2024.cs2340.group9.studysync.database;

import androidx.annotation.NonNull;

import java.util.Locale;

/**
 * Class that represents a single time of the week.
 * Used to represent a specific time of week (not absolute time) for courses.
 */
public class RecurringTime implements Comparable<RecurringTime> {
    private static final String[] DAY_OF_WEEK = {"Sunday", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday"};
    private int dayOfWeek;
    private int hour;
    private int minute;

    /**
     * Create a new Time object with the number of minutes past 00:00 Sunday.
     * @param time number of minutes past 00:00 Sunday
     * @throws IllegalArgumentException if the time is negative or greater than or equal to 1 week
     */
    public RecurringTime(int time) throws IllegalArgumentException {
        set(time);
    }

    /**
     * Create a new Time object with the dayOfWeek, hour, and minute.
     * @param dayOfWeek int representing the day of the week, where 0 is Sunday and 6 is Saturday
     * @param hour int representing the hour of the day, in the interval [0, 24)
     * @param minute int representing the minute of the hour, in the interval [0, 60)
     * @throws IllegalArgumentException if the inputs are invalid
     */
    public RecurringTime(int dayOfWeek, int hour, int minute) throws IllegalArgumentException {
        set(dayOfWeek, hour, minute);
    }

    /**
     * Deep copy a Time object.
     * @param time Time object to copy
     */
    public RecurringTime(RecurringTime time) {
        this.dayOfWeek = time.dayOfWeek;
        this.hour = time.hour;
        this.minute = time.minute;
    }

    /**
     * Get the day of the week as an integer.
     * @return day of the week
     */
    public int getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Get the day of the week as a String.
     * @return day of the week
     */
    public String getDayOfWeekString() {
        return DAY_OF_WEEK[dayOfWeek];
    }

    /**
     * Get the hour of the day.
     * @return hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * Get the hour of the day in 12-hour format.
     * @return hour in 12-hour format
     */
    public int get12Hour() {
        return (hour - 1) % 12 + 1;
    }

    /**
     * Get the minute of the hour
     * @return minute
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Set the day of the week.
     * @param dayOfWeek day of the week, in the range [0, 6]
     * @throws IllegalArgumentException if the input is invalid
     */
    public void setDayOfWeek(int dayOfWeek) throws IllegalArgumentException {
        validateInput(dayOfWeek, hour, minute);
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Set the hour of the day.
     * @param hour hour, in the range [0, 24)
     * @throws IllegalArgumentException if the input is invalid
     */
    public void setHour(int hour) throws IllegalArgumentException {
        validateInput(dayOfWeek, hour, minute);
        this.hour = hour;
    }

    /**
     * Set the minute of the hour.
     * @param minute minute, in the range [0, 60)
     * @throws IllegalArgumentException if the input is invalid
     */
    public void setMinute(int minute) throws IllegalArgumentException {
        validateInput(dayOfWeek, hour, minute);
        this.minute = minute;
    }

    /**
     * Set the time with the given parameters.
     * @param dayOfWeek day of the week, in the range [0, 6]
     * @param hour hour, in the range [0, 24)
     * @param minute minute, in the range [0, 60)
     * @throws IllegalArgumentException if the input is invalid
     */
    public void set(int dayOfWeek, int hour, int minute) throws IllegalArgumentException {
        validateInput(dayOfWeek, hour, minute);
        this.dayOfWeek = dayOfWeek;
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Set the time since Sunday 00:00.
     * @param time number of minutes since Sunday 00:00
     * @throws IllegalArgumentException if the input is invalid
     */
    public void set(int time) throws IllegalArgumentException {
        validateInput(time);
        dayOfWeek = time / (24 * 60);
        hour = (time / 60) % 24;
        minute = time % 60;
    }

    /**
     * Get whether the time is in the morning.
     * @return if time is AM
     */
    public boolean isAM() {
        return hour < 12;
    }

    /**
     * Get the total number of minutes since 00:00 Sunday
     * @return minutes
     */
    public int getMinutesSinceSunday() {
        return dayOfWeek * 24 * 60 + hour * 60 + minute;
    }

    /**
     * Get the time in the format "DAY HH:MM".
     * @return String of the time
     */
    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s %02d:%02d", DAY_OF_WEEK[dayOfWeek], hour,
                minute);
    }

    /**
     * Overridden compareTo function to compare specific times.
     * @param time the object to be compared
     * @return minutes between the times
     */
    @Override
    public int compareTo(RecurringTime time) {
        return getMinutesSinceSunday() - time.getMinutesSinceSunday();
    }

    /**
     * Private helper function to validate input.
     * @param dayOfWeek day of the week
     * @param hour hour
     * @param minute minute
     * @throws IllegalArgumentException if the input is invalid
     */
    private void validateInput(int dayOfWeek, int hour, int minute) throws IllegalArgumentException {
        if (dayOfWeek < 0) {
            throw new IllegalArgumentException(String.format("The dayOfWeek %d can not be less " +
                    "than 0.", dayOfWeek));
        } else if (dayOfWeek >= 7) {
            throw new IllegalArgumentException(String.format("The dayOfWeek %d can not be " +
                    "greater than 6", dayOfWeek));
        } else if (hour < 0) {
            throw new IllegalArgumentException(String.format("The hour %d can not be less than 0.",
                    hour));
        } else if (hour >= 24) {
            throw new IllegalArgumentException(String.format("The hour %d can not be greater " +
                    "than 24.", hour));
        } else if (minute < 0) {
            throw new IllegalArgumentException(String.format("The minute %d can not be less " +
                    "than 0.", minute));
        } else if (minute >= 60) {
            throw new IllegalArgumentException(String.format("The minute %d must be less than 60.",
                    minute));
        }
    }

    /**
     * Private helper function to validate input.
     * @param time minutes since Sunday 00:00
     * @throws IllegalArgumentException if the input is invalid
     */
    private void validateInput(int time) throws IllegalArgumentException {
        if (time < 0) {
            throw new IllegalArgumentException(String.format("The time %d can not be less than 0.",
                    time));
        } else if (time >= 7 * 24 * 60) {
            throw new IllegalArgumentException(String.format("The time %d must be less than %d.",
                    time, 7 * 24 * 60));
        }
    }
}
