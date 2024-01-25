package spr2024.cs2340.group9.studysync;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.*;

import spr2024.cs2340.group9.studysync.database.Assignment;
import spr2024.cs2340.group9.studysync.database.Assignments;

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
}
