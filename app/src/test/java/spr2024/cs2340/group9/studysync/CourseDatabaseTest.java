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
import spr2024.cs2340.group9.studysync.database.RecurringTime;
import spr2024.cs2340.group9.studysync.database.TimePeriod;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
public class CourseDatabaseTest {
    private static final int TIMEOUT = 1000;
    private Courses courses;

    @Before
    public void setup() {
        courses = new Courses(RuntimeEnvironment.getApplication().getApplicationContext());
    }

    @After
    public void cleanup() {
        courses.clear();
    }

    @Test(timeout = TIMEOUT)
    public void getAllIsEmptyArray() {
        Course[] courseList = courses.getAll();
        assertEquals(0, courseList.length);
    }

    @Test(timeout = TIMEOUT)
    public void insertOneCourseUpdatesDatabase() {
        Course course = new Course("CS 2340", "Dr. Feijoo", 0, 15);
        courses.insert(course);

        Course[] courseList = courses.getAll();
        assertEquals(1, courseList.length);
        assertArrayEquals(new Course[]{course}, courseList);
    }

    @Test(timeout = TIMEOUT)
    public void clearEmptiesTable() {
        Course course = new Course("CS 2340", "Dr. Feijoo", 0, 15);
        courses.insert(course);
        courses.clear();

        Course[] courseList = courses.getAll();
        assertEquals(0, courseList.length);
    }

    @Test(timeout = TIMEOUT)
    public void insertManyCourseUpdatesDatabase() {
        Course[] newCourses = {
                new Course("CS 2340", "Dr. Feijoo", 0, 15),
                new Course("CS 1332", "Prof. Faulkner", 1, 0),
                new Course("CS 2050", "Prof. Faulkner", 2, 0),
                new Course("CHEM 1212K", "Dr. Zhang", 3, 0)
        };
        courses.insert(newCourses);

        Course[] courseList = courses.getAll();
        assertEquals(4, courseList.length);
        assertArrayEquals(newCourses, courseList);
    }

    @Test(timeout = TIMEOUT)
    public void deleteOneCourseUpdatesDatabase() {
        Course[] newCourses = {
                new Course("CS 2340", "Dr. Feijoo", 0, 15),
                new Course("CS 1332", "Prof. Faulkner", 1, 0),
                new Course("CS 2050", "Prof. Faulkner", 2, 0),
                new Course("CHEM 1212K", "Dr. Zhang", 3, 0)
        };
        courses.insert(newCourses);

        courses.delete(newCourses[3]);

        Course[] courseList = courses.getAll();
        assertArrayEquals(Arrays.copyOfRange(newCourses, 0, 3), courseList);
    }

    @Test(timeout = TIMEOUT)
    public void deleteNewCourseDoesNotChangeDatabase() {
        Course[] newCourses = {
                new Course("CS 2340", "Dr. Feijoo", 0, 15),
                new Course("CS 1332", "Prof. Faulkner", 1, 0),
                new Course("CS 2050", "Prof. Faulkner", 2, 0),
                new Course("CHEM 1212K", "Dr. Zhang", 3, 0)
        };
        courses.insert(newCourses);

        courses.delete(new Course("MATH 1564", "Dr. Zou", 1, 0));

        Course[] courseList = courses.getAll();
        assertArrayEquals(newCourses, courseList);
    }

    @Test(timeout = TIMEOUT)
    public void deleteExistingCourseWithDifferentIdDoesNotChangeDatabase() {
        Course[] newCourses = {
                new Course("CS 2340", "Dr. Feijoo", 0, 15),
                new Course("CS 1332", "Prof. Faulkner", 1, 0),
                new Course("CS 2050", "Prof. Faulkner", 2, 0),
                new Course("CHEM 1212K", "Dr. Zhang", 3, 0)
        };
        courses.insert(newCourses);

        courses.delete(new Course("CS 2340", "Dr. Feijoo", 0, 15));

        Course[] courseList = courses.getAll();
        assertArrayEquals(newCourses, courseList);
    }

    @Test(timeout = TIMEOUT)
    public void deleteCourseFromEmptyDatabaseDoesNotChangeDatabase() {
        Course del = new Course("CHEM 1212K", "Dr. Zhang", 3, 0);
        courses.delete(del);

        Course[] courseList = courses.getAll();
        assertArrayEquals(new Course[0], courseList);
    }

    @Test(timeout = TIMEOUT)
    public void insertCourseWithOneTimePeriodUpdatesDatabases() {
        Course newCourse = new Course("CS 2340", "Dr. Feijoo", 0, 15);
        TimePeriod period = new TimePeriod(newCourse.id,
                new RecurringTime(1, 8, 0),
                new RecurringTime(1, 9, 15));
        courses.insert(newCourse);

        Course[] courseList = courses.getAll();
        assertArrayEquals(new Course[]{newCourse}, courseList);
    }
}