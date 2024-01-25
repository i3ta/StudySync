package spr2024.cs2340.group9.studysync;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.GregorianCalendar;

import spr2024.cs2340.group9.studysync.database.Assignment;
import spr2024.cs2340.group9.studysync.database.Assignments;
import spr2024.cs2340.group9.studysync.database.Order;

@RunWith(RobolectricTestRunner.class)
public class AssignmentDatabaseTest {
    private static final int TIMEOUT = 1000;

    @Before
    public void setup(){
        Assignments.init(RuntimeEnvironment.getApplication().getApplicationContext());
    }

    @After
    public void cleanup() {
        Assignments.clear();
    }

    @Test(timeout = TIMEOUT)
    public void getAllWhenTableEmptyShouldReturnEmptyArray() {
        Assignment[] assignments = Assignments.getAll();
        assertArrayEquals(new Assignment[]{}, assignments);
    }

    @Test(timeout = TIMEOUT)
    public void insertAssignmentShouldReturnAssignment() {
        Assignment a = new Assignment("a", 0, new GregorianCalendar(2024, 1, 1).getTime(), 0);
        Assignments.insert(a);

        Assignment[] assignments = Assignments.getAll();
        assertArrayEquals(new Assignment[]{ a }, assignments);
    }

    @Test(timeout = TIMEOUT)
    public void clearShouldReturnEmptyArray() {
        Assignment a = new Assignment("a", 0, new GregorianCalendar(2024, 1, 24).getTime(), 0);
        Assignments.insert(a);

        Assignment[] assignments = Assignments.getAll();
        assertArrayEquals(new Assignment[]{ a }, assignments);

        Assignments.clear();
        assignments = Assignments.getAll();
        assertArrayEquals(new Assignment[]{}, assignments);
    }

    @Test(timeout = TIMEOUT)
    public void insertManyAssignmentsShouldReturnAllAssignments() {
        Assignment[] a = {
                new Assignment("a", 0, new GregorianCalendar(2024, 1, 1).getTime(), 0),
                new Assignment("b", 0, new GregorianCalendar(2024, 1, 1).getTime(), 0),
                new Assignment("c", 1, new GregorianCalendar(2024, 1, 24).getTime(), 0),
                new Assignment("d", 2, new GregorianCalendar(2024, 2, 9).getTime(), 0)
        };
        Assignments.insert(a);

        Assignment[] assignments = Assignments.getAll();
        assertArrayEquals(a, assignments);
    }

    @Test(timeout = TIMEOUT)
    public void deleteOneCourseShouldDeleteCourse() {
        Assignment[] a = {
                new Assignment("a", 0, new GregorianCalendar(2024, 1, 1).getTime(), 0),
                new Assignment("b", 0, new GregorianCalendar(2024, 1, 1).getTime(), 0),
                new Assignment("c", 1, new GregorianCalendar(2024, 1, 24).getTime(), 0),
                new Assignment("d", 2, new GregorianCalendar(2024, 2, 9).getTime(), 0)
        };
        Assignments.insert(a);

        Assignment[] assignments = Assignments.getAll();
        assertArrayEquals(a, assignments);

        Assignments.delete(a[2]);
        assignments = Assignments.getAll();
        assertArrayEquals(new Assignment[]{ a[0], a[1], a[3] }, assignments);
    }

    @Test(timeout = TIMEOUT)
    public void deleteNewCourseShouldNotRemoveCourse() {
        Assignment[] a = {
                new Assignment("a", 0, new GregorianCalendar(2024, 1, 1).getTime(), 0),
                new Assignment("b", 0, new GregorianCalendar(2024, 1, 1).getTime(), 0),
                new Assignment("c", 1, new GregorianCalendar(2024, 1, 24).getTime(), 0),
                new Assignment("d", 2, new GregorianCalendar(2024, 2, 9).getTime(), 0)
        };
        Assignments.insert(a);

        Assignment[] assignments = Assignments.getAll();
        assertArrayEquals(a, assignments);

        Assignments.delete(new Assignment("e", 3, new GregorianCalendar(2024, 2, 1).getTime(), 0));
        assignments = Assignments.getAll();
        assertArrayEquals(a, assignments);
    }

    @Test(timeout = TIMEOUT)
    public void deletingExistingCourseWithDifferentIdShouldNotRemoveCourse() {
        Assignment[] a = {
                new Assignment("a", 0, new GregorianCalendar(2024, 1, 1).getTime(), 0),
                new Assignment("b", 0, new GregorianCalendar(2024, 1, 1).getTime(), 0),
                new Assignment("c", 1, new GregorianCalendar(2024, 1, 24).getTime(), 0),
                new Assignment("d", 2, new GregorianCalendar(2024, 2, 9).getTime(), 0)
        };
        Assignments.insert(a);

        Assignment[] assignments = Assignments.getAll();
        assertArrayEquals(a, assignments);

        Assignments.delete(new Assignment("a", 0, new GregorianCalendar(2024, 1, 1).getTime(), 0));
        assignments = Assignments.getAll();
        assertArrayEquals(a, assignments);
    }

    @Test(timeout = TIMEOUT)
    public void deleteCourseFromEmptyDatabaseShouldNotRemoveCourse() {
        Assignments.delete(new Assignment("a", 0, new GregorianCalendar(2024, 1, 1).getTime(), 0));
        Assignment[] assignments = Assignments.getAll();
        assertArrayEquals(new Assignment[]{}, assignments);
    }

    @Test(timeout = TIMEOUT)
    public void insertRepeatedAssignmentShouldKeepOneAssignment() {
        Assignment a = new Assignment("a", 0, new GregorianCalendar(2024, 1, 1).getTime(), 0);
        Assignments.insert(a);

        Assignment[] assignments = Assignments.getAll();
        assertArrayEquals(new Assignment[]{ a }, assignments);

        Assignments.insert(a);
        assignments = Assignments.getAll();
        assertArrayEquals(new Assignment[]{ a }, assignments);
    }

    @Test(timeout = TIMEOUT)
    public void getCourseShouldReturnAllAssignmentsForCourse() {
        Assignment[] a = {
                new Assignment("a", 0, new GregorianCalendar(2024, 1, 1).getTime(), 0),
                new Assignment("b", 1, new GregorianCalendar(2024, 1, 1).getTime(), 0),
                new Assignment("c", 0, new GregorianCalendar(2024, 1, 24).getTime(), 0),
                new Assignment("d", 0, new GregorianCalendar(2024, 1, 9).getTime(), 0)
        };
        Assignments.insert(a);

        Assignment[] assignments = Assignments.getCourse(0);
        assertArrayEquals(new Assignment[]{ a[0], a[3], a[2] }, assignments);
    }

    @Test(timeout = TIMEOUT)
    public void getBetweenShouldReturnAllAssignmentsInTimeFrame() {
        Assignment[] a = {
                new Assignment("a", 0, 1000, 0),
                new Assignment("b", 0, 2000, 0),
                new Assignment("c", 1, 1001, 0),
                new Assignment("d", 2, 1500, 0)
        };
        Assignments.insert(a);

        Assignment[] assignments = Assignments.getBetween(1000, 1999);
        assertArrayEquals(new Assignment[]{ a[0], a[2], a[3] }, assignments);
    }
}
