package spr2024.cs2340.group9.studysync;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Arrays;

import spr2024.cs2340.group9.studysync.database.RecurringSlot;
import spr2024.cs2340.group9.studysync.database.ToDoList;
import spr2024.cs2340.group9.studysync.database.ToDoLists;
import spr2024.cs2340.group9.studysync.database.ToDoListItem;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
public class ToDoDatabaseTest {
    private static final int TIMEOUT = 1000;

    @Before
    public void setup() {
        ToDoLists.init(RuntimeEnvironment.getApplication().getApplicationContext());
    }

    @After
    public void cleanup() {
        ToDoLists.clear();
    }

    @Test(timeout = TIMEOUT)
    public void getAllToDoListsShouldReturnEmptyArray() {
        ToDoList[] toDoLists = ToDoLists.getAllLists();
        assertEquals(0, toDoLists.length);
    }

    @Test(timeout = TIMEOUT)
    public void insertOneToDoListsShouldReturnToDoList() {
        ToDoList toDoList = new ToDoList("A");
        ToDoLists.insert(toDoList);

        ToDoList[] toDoLists = ToDoLists.getAllLists();
        assertEquals(1, toDoLists.length);
        assertArrayEquals(new ToDoList[]{toDoList}, toDoLists);
    }

    @Test(timeout = TIMEOUT)
    public void clearEmptiesTable() {
        ToDoList toDoList = new ToDoList("A");
        ToDoLists.insert(toDoList);
        ToDoLists.clear();

        ToDoList[] toDoLists = ToDoLists.getAllLists();
        assertEquals(0, toDoLists.length);
    }

    @Test(timeout = TIMEOUT)
    public void insertManyToDoListsShouldReturnAllToDoLists() {
        ToDoList[] newToDoLists = {
                new ToDoList("A"),
                new ToDoList("B"),
                new ToDoList("C"),
                new ToDoList("A")
        };
        ToDoLists.insert(newToDoLists);

        ToDoList[] toDoLists = ToDoLists.getAllLists();
        assertEquals(4, toDoLists.length);
        assertArrayEquals(newToDoLists, toDoLists);
    }

    @Test(timeout = TIMEOUT)
    public void deleteOneToDoListsShouldDeleteToDoList() {
        ToDoList[] newToDoLists = {
                new ToDoList("A"),
                new ToDoList("B"),
                new ToDoList("C"),
                new ToDoList("A")
        };
        ToDoLists.insert(newToDoLists);

        ToDoLists.delete(newToDoLists[3]);

        ToDoList[] toDoLists = ToDoLists.getAllLists();
        assertArrayEquals(Arrays.copyOfRange(newToDoLists, 0, 3), toDoLists);
    }

    @Test(timeout = TIMEOUT)
    public void deleteNewToDoListsShouldNotRemoveToDoList() {
        ToDoList[] newToDoLists = {
                new ToDoList("A"),
                new ToDoList("B"),
                new ToDoList("C"),
                new ToDoList("A")
        };
        ToDoLists.insert(newToDoLists);

        ToDoLists.delete(new ToDoList("E"));

        ToDoList[] toDoLists = ToDoLists.getAllLists();
        assertArrayEquals(newToDoLists, toDoLists);
    }

    @Test(timeout = TIMEOUT)
    public void deleteExistingToDoListWithDifferentIdShouldNotRemoveToDoList() {
        ToDoList[] newToDoLists = {
                new ToDoList("A"),
                new ToDoList("B"),
                new ToDoList("C"),
                new ToDoList("A")
        };
        ToDoLists.insert(newToDoLists);

        ToDoLists.delete(new ToDoList("A"));

        ToDoList[] toDoLists = ToDoLists.getAllLists();
        assertArrayEquals(newToDoLists, toDoLists);
    }

    @Test(timeout = TIMEOUT)
    public void deleteToDoListFromEmptyDatabaseShouldNotRemoveToDoList() {
        ToDoList del = new ToDoList("Z");
        ToDoLists.delete(del);

        ToDoList[] toDoLists = ToDoLists.getAllLists();
        assertArrayEquals(new ToDoList[0], toDoLists);
    }

    @Test(timeout = TIMEOUT)
    public void insertRepeatedToDoListsShouldKeepOneToDoList() {
        ToDoList newToDoList = new ToDoList("A");
        ToDoLists.insert(newToDoList);
        ToDoLists.insert(newToDoList);

        ToDoList[] toDoLists = ToDoLists.getAllLists();
        assertEquals(1, toDoLists.length);
        assertArrayEquals(new ToDoList[]{newToDoList}, toDoLists);
    }

    @Test(timeout = TIMEOUT)
    public void insertRepeatedToDoListIdShouldKeepOneToDoList() {
        ToDoList toDoList1 = new ToDoList("A");
        ToDoList toDoList2 = new ToDoList(toDoList1);
        ToDoLists.insert(toDoList1);
        ToDoLists.insert(toDoList2);

        ToDoList[] toDoLists = ToDoLists.getAllLists();
        assertEquals(1, toDoLists.length);
        assertArrayEquals(new ToDoList[]{toDoList2}, toDoLists);
    }

    @Test(timeout = TIMEOUT)
    public void insertToDoListItemShouldReturnWithToDoListItem() {
        ToDoListItem item = new ToDoListItem(0, "A", false);
        ToDoLists.insert(item);

        ToDoListItem[] list = ToDoLists.getAllItems();
        assertArrayEquals(new ToDoListItem[]{item}, list);
    }

    @Test(timeout = TIMEOUT)
    public void insertRepeatedToDoListWithToDoListItemShouldRetainOneToDoListItem() {
        ToDoListItem item = new ToDoListItem(0, "A", false);
        ToDoLists.insert(item);
        ToDoLists.insert(item);

        ToDoListItem[] list = ToDoLists.getAllItems();
        assertArrayEquals(new ToDoListItem[]{item}, list);
    }

    @Test(timeout = TIMEOUT)
    public void insertMultipleToDoListsWithSameTimePeriodShouldRetainBothToDoListItems() {
        ToDoListItem item1 = new ToDoListItem(0, "A", false);
        ToDoListItem item2 = new ToDoListItem(0, "B", true);
        ToDoLists.insert(item1, item2);

        ToDoListItem[] list = ToDoLists.getAllItems();
        assertArrayEquals(new ToDoListItem[]{item1, item2}, list);
    }
}