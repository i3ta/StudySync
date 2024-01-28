package spr2024.cs2340.group9.studysync;

import static org.junit.Assert.assertArrayEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.GregorianCalendar;

import spr2024.cs2340.group9.studysync.database.Exam;
import spr2024.cs2340.group9.studysync.database.Exams;

@RunWith(RobolectricTestRunner.class)
public class ExamDatabaseTest {
    private static final int TIMEOUT = 1000;

    @Before
    public void setup(){
        Exams.init(RuntimeEnvironment.getApplication().getApplicationContext());
    }

    @After
    public void cleanup() {
        Exams.clear();
    }

    @Test(timeout = TIMEOUT)
    public void getAllWhenTableEmptyShouldReturnEmptyArray() {
        Exam[] assignments = Exams.getAll();
        assertArrayEquals(new Exam[]{}, assignments);
    }

    @Test(timeout = TIMEOUT)
    public void insertExamShouldReturnExam() {
        Exam a = new Exam("a", 0, 100, 0);
        Exams.insert(a);

        Exam[] assignments = Exams.getAll();
        assertArrayEquals(new Exam[]{ a }, assignments);
    }

    @Test(timeout = TIMEOUT)
    public void clearShouldReturnEmptyArray() {
        Exam a = new Exam("a", 0, 100, 0);
        Exams.insert(a);

        Exam[] assignments = Exams.getAll();
        assertArrayEquals(new Exam[]{ a }, assignments);

        Exams.clear();
        assignments = Exams.getAll();
        assertArrayEquals(new Exam[]{}, assignments);
    }

    @Test(timeout = TIMEOUT)
    public void insertManyExamsShouldReturnAllExams() {
        Exam[] a = {
                new Exam("a", 0, 100, 0),
                new Exam("b", 0, 100, 0),
                new Exam("c", 1, 100, 0),
                new Exam("d", 200, 400, 0)
        };
        Exams.insert(a);

        Exam[] assignments = Exams.getAll();
        assertArrayEquals(a, assignments);
    }

    @Test(timeout = TIMEOUT)
    public void deleteOneCourseShouldDeleteCourse() {
        Exam[] a = {
                new Exam("a", 0, 100, 0),
                new Exam("b", 0, 100, 0),
                new Exam("c", 1, 100, 0),
                new Exam("d", 200, 400, 0)
        };
        Exams.insert(a);

        Exam[] assignments = Exams.getAll();
        assertArrayEquals(a, assignments);

        Exams.delete(a[2]);
        assignments = Exams.getAll();
        assertArrayEquals(new Exam[]{ a[0], a[1], a[3] }, assignments);
    }

    @Test(timeout = TIMEOUT)
    public void deleteNewCourseShouldNotRemoveCourse() {
        Exam[] a = {
                new Exam("a", 0, 100, 0),
                new Exam("b", 0, 100, 0),
                new Exam("c", 1, 100, 0),
                new Exam("d", 200, 400, 0)
        };
        Exams.insert(a);

        Exam[] assignments = Exams.getAll();
        assertArrayEquals(a, assignments);

        Exams.delete(new Exam("e", 3, 1000, 0));
        assignments = Exams.getAll();
        assertArrayEquals(a, assignments);
    }

    @Test(timeout = TIMEOUT)
    public void deletingExistingCourseWithDifferentIdShouldNotRemoveCourse() {
        Exam[] a = {
                new Exam("a", 0, 100, 0),
                new Exam("b", 0, 100, 0),
                new Exam("c", 1, 100, 0),
                new Exam("d", 200, 400, 0)
        };
        Exams.insert(a);

        Exam[] assignments = Exams.getAll();
        assertArrayEquals(a, assignments);

        Exams.delete(new Exam("a", 0, 100, 0));
        assignments = Exams.getAll();
        assertArrayEquals(a, assignments);
    }

    @Test(timeout = TIMEOUT)
    public void deleteCourseFromEmptyDatabaseShouldNotRemoveCourse() {
        Exams.delete(new Exam("a", 0, 100, 0));
        Exam[] assignments = Exams.getAll();
        assertArrayEquals(new Exam[]{}, assignments);
    }

    @Test(timeout = TIMEOUT)
    public void insertRepeatedExamShouldKeepOneExam() {
        Exam a = new Exam("a", 0, 100, 0);
        Exams.insert(a);

        Exam[] assignments = Exams.getAll();
        assertArrayEquals(new Exam[]{ a }, assignments);

        Exams.insert(a);
        assignments = Exams.getAll();
        assertArrayEquals(new Exam[]{ a }, assignments);
    }

    @Test(timeout = TIMEOUT)
    public void getBetweenShouldReturnAllExamsInTimeFrame() {
        Exam[] a = {
                new Exam("a", 0, 1000, 0),
                new Exam("b", 0, 2000, 0),
                new Exam("c", 1, 1001, 0),
                new Exam("d", 2, 999, 0)
        };
        Exams.insert(a);

//        Exam[] assignments = Exams.getBetween(1000, 2000);
        assertArrayEquals(new Exam[]{ a[0], a[1], a[2] }, assignments);
    }
}
