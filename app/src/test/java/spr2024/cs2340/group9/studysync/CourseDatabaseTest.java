package spr2024.cs2340.group9.studysync;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.*;

import java.util.Arrays;

import spr2024.cs2340.group9.studysync.database.Course;
import spr2024.cs2340.group9.studysync.database.Courses;
import spr2024.cs2340.group9.studysync.database.RecurringSlot;
import spr2024.cs2340.group9.studysync.database.TimeSlot;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
public class CourseDatabaseTest {
    private static final int TIMEOUT = 1000;

    @Before
    public void setup() {
        Courses.init(RuntimeEnvironment.getApplication().getApplicationContext());
    }

    @After
    public void cleanup() {
        Courses.clear();
    }

    @Test(timeout = TIMEOUT)
    public void getAllCoursesShouldReturnEmptyArray() {
        Course[] courseList = Courses.getAll();
        assertEquals(0, courseList.length);
    }

    @Test(timeout = TIMEOUT)
    public void insertOneCourseshouldReturnCourse() {
        Course course = new Course("CS 2340", "Dr. Feijoo", 0, 15);
        Courses.insert(course);

        Course[] courseList = Courses.getAll();
        assertEquals(1, courseList.length);
        assertArrayEquals(new Course[]{course}, courseList);
    }

    @Test(timeout = TIMEOUT)
    public void clearEmptiesTable() {
        Course course = new Course("CS 2340", "Dr. Feijoo", 0, 15);
        Courses.insert(course);
        Courses.clear();

        Course[] courseList = Courses.getAll();
        assertEquals(0, courseList.length);
    }

    @Test(timeout = TIMEOUT)
    public void insertManyCourseshouldReturnAllCourses() {
        Course[] newCourses = {
                new Course("CS 2340", "Dr. Feijoo", 0, 15),
                new Course("CS 1332", "Prof. Faulkner", 1, 0),
                new Course("CS 2050", "Prof. Faulkner", 2, 0),
                new Course("CHEM 1212K", "Dr. Zhang", 3, 0)
        };
        Courses.insert(newCourses);

        Course[] courseList = Courses.getAll();
        assertEquals(4, courseList.length);
        assertArrayEquals(newCourses, courseList);
    }

    @Test(timeout = TIMEOUT)
    public void deleteOneCourseshouldDeleteCourse() {
        Course[] newCourses = {
                new Course("CS 2340", "Dr. Feijoo", 0, 15),
                new Course("CS 1332", "Prof. Faulkner", 1, 0),
                new Course("CS 2050", "Prof. Faulkner", 2, 0),
                new Course("CHEM 1212K", "Dr. Zhang", 3, 0)
        };
        Courses.insert(newCourses);

        Courses.delete(newCourses[3]);

        Course[] courseList = Courses.getAll();
        assertArrayEquals(Arrays.copyOfRange(newCourses, 0, 3), courseList);
    }

    @Test(timeout = TIMEOUT)
    public void deleteNewCourseshouldNotRemoveCourse() {
        Course[] newCourses = {
                new Course("CS 2340", "Dr. Feijoo", 0, 15),
                new Course("CS 1332", "Prof. Faulkner", 1, 0),
                new Course("CS 2050", "Prof. Faulkner", 2, 0),
                new Course("CHEM 1212K", "Dr. Zhang", 3, 0)
        };
        Courses.insert(newCourses);

        Courses.delete(new Course("MATH 1564", "Dr. Zou", 1, 0));

        Course[] courseList = Courses.getAll();
        assertArrayEquals(newCourses, courseList);
    }

    @Test(timeout = TIMEOUT)
    public void deleteExistingCourseWithDifferentIdShouldNotRemoveCourse() {
        Course[] newCourses = {
                new Course("CS 2340", "Dr. Feijoo", 0, 15),
                new Course("CS 1332", "Prof. Faulkner", 1, 0),
                new Course("CS 2050", "Prof. Faulkner", 2, 0),
                new Course("CHEM 1212K", "Dr. Zhang", 3, 0)
        };
        Courses.insert(newCourses);

        Courses.delete(new Course("CS 2340", "Dr. Feijoo", 0, 15));

        Course[] courseList = Courses.getAll();
        assertArrayEquals(newCourses, courseList);
    }

    @Test(timeout = TIMEOUT)
    public void deleteCourseFromEmptyDatabaseShouldNotRemoveCourse() {
        Course del = new Course("CHEM 1212K", "Dr. Zhang", 3, 0);
        Courses.delete(del);

        Course[] courseList = Courses.getAll();
        assertArrayEquals(new Course[0], courseList);
    }

    @Test(timeout = TIMEOUT)
    public void insertRepeatedCourseshouldKeepOneCourse() {
        Course newCourse = new Course("CS 2340", "Dr. Feijoo", 0, 15);
        Courses.insert(newCourse);
        Courses.insert(newCourse);

        Course[] courseList = Courses.getAll();
        assertEquals(1, courseList.length);
        assertArrayEquals(new Course[]{newCourse}, courseList);
    }

    @Test(timeout = TIMEOUT)
    public void insertRepeatedCourseIdShouldKeepOneCourse() {
        Course course1 = new Course("CS 2340", "Dr. Feijoo", 0, 15);
        Course course2 = new Course(course1);
        course2.name = "CHEM 1212K";
        course2.instructorName = "Dr. Zhang";
        Courses.insert(course1);
        Courses.insert(course2);

        Course[] courseList = Courses.getAll();
        assertEquals(1, courseList.length);
        assertArrayEquals(new Course[]{course2}, courseList);
    }

    @Test(timeout = TIMEOUT)
    public void insertCourseWithOneTimeSlotShouldReturnWithTimeSlot() {
        Course newCourse = new Course("CS 2340", "Dr. Feijoo", 0, 15);
        TimeSlot period = new TimeSlot(newCourse.id,
                new RecurringSlot(1, 8, 0),
                new RecurringSlot(1, 9, 15));
        newCourse.setCourseTimes(new TimeSlot[]{period});
        Courses.insert(newCourse);

        Course[] courseList = Courses.getAll();
        assertArrayEquals(new Course[]{newCourse}, courseList);
    }

    @Test(timeout = TIMEOUT)
    public void updateTimeSlotShouldReturnNewTimeSlot() {
        Course course = new Course("CS 1332", "Prof. Faulkner", 0, 0);
        TimeSlot period1 = new TimeSlot(course.id,
                new RecurringSlot(1, 14, 0),
                new RecurringSlot(1, 14, 50));
        course.setCourseTimes(new TimeSlot[]{period1});
        Courses.insert(course);

        TimeSlot period2 = new TimeSlot(course.id,
                new RecurringSlot(3, 14, 0),
                new RecurringSlot(3, 14, 50));
        course.setCourseTimes(new TimeSlot[]{period2});
        Courses.insert(course);

        Course[] courseList = Courses.getAll();
        assertArrayEquals(new Course[]{course}, courseList);
    }

    @Test(timeout = TIMEOUT)
    public void insertCourseRemoveTimeSlotRemovesTimeSlot() {
        Course course = new Course("CS 1332", "Prof. Faulkner", 0, 0);
        TimeSlot period1 = new TimeSlot(course.id,
                new RecurringSlot(1, 14, 0),
                new RecurringSlot(1, 14, 50));
        course.setCourseTimes(new TimeSlot[]{period1});
        Courses.insert(course);

        course.setCourseTimes(null);
        Courses.insert(course);

        Course[] courseList = Courses.getAll();
        assertArrayEquals(new Course[]{course}, courseList);
    }

    @Test(timeout = TIMEOUT)
    public void insertRepeatedCourseWithTimeSlotShouldRetainOneTimeSlot() {
        Course course = new Course("CS 1332", "Prof. Faulkner", 0, 0);
        TimeSlot period1 = new TimeSlot(course.id,
                new RecurringSlot(1, 14, 0),
                new RecurringSlot(1, 14, 50));
        course.setCourseTimes(new TimeSlot[]{period1});
        Courses.insert(course);
        Courses.insert(course);

        Course[] courseList = Courses.getAll();
        assertArrayEquals(new Course[]{course}, courseList);
    }

    @Test(timeout = TIMEOUT)
    public void insertMultipleCoursesWithSameTimePeriodShouldRetainBothTimeSlots() {
        Course course1 = new Course("CS 1332", "Prof. Faulkner", 0, 0);
        TimeSlot period1 = new TimeSlot(course1.id,
                new RecurringSlot(1, 14, 0),
                new RecurringSlot(1, 14, 0));
        course1.setCourseTimes(new TimeSlot[]{period1});
        Course course2 = new Course("CS 2340", "Dr. Feijoo", 0, 0);
        TimeSlot period2 = new TimeSlot(course2.id,
                new RecurringSlot(1, 14, 0),
                new RecurringSlot(1, 14, 0));
        course2.setCourseTimes(new TimeSlot[]{period2});
        Courses.insert(course1, course2);

        Course[] courseList = Courses.getAll();
        assertArrayEquals(new Course[]{course1, course2}, courseList);
    }
}