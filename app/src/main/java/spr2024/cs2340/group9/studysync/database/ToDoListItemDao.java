package spr2024.cs2340.group9.studysync.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
interface ToDoListItemDao {
    /**
     * Insert to do list item(s) into database
     * @param toDoListItems items to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ToDoListItem... toDoListItems);

    /**
     * Delete to do list item from database.
     * @param toDoListItem item to delete
     */
    @Delete
    void delete(ToDoListItem toDoListItem);

    /**
     * Delete all to do list items with a specific to do list id.
     * @param toDoListId to do list id
     */
    @Query("DELETE FROM ToDoListItem WHERE toDoListId = :toDoListId")
    void deleteList(int toDoListId);

    /**
     * Delete to do list item from database with id.
     * @param id to do list item id to delete
     */
    @Query("DELETE FROM ToDoListItem WHERE id = :id")
    void delete(int id);

    /**
     * Get all to do list items from database.
     * @return to do list items
     */
    @Query("SELECT * FROM ToDoListItem ORDER BY complete ASC")
    ToDoListItem[] getAll();

    /**
     * Get to do list item with id.
     * @param id id of to do list item
     * @return to do list item
     */
    @Query("SELECT * FROM ToDoListItem WHERE id = :id")
    ToDoListItem get(int id);

    /**
     * Get items from database in a specific to-do list.
     * @param toDoListId to do list id
     * @return to do list items
     */
    @Query("SELECT * FROM ToDoListItem WHERE toDoListId = :toDoListId ORDER BY complete ASC")
    ToDoListItem[] getList(int toDoListId);

    /**
     * Get all incomplete items from a specific to-do list.
     * @param toDoListId to do list id
     * @return to do list items
     */
    @Query("SELECT * FROM todolistitem WHERE toDoListId = :toDoListId AND complete = 0")
    ToDoListItem[] getIncomplete(int toDoListId);

    /**
     * Clear ToDoListItem table.
     */
    @Query("DELETE FROM ToDoListItem")
    void clear();

    /**
     * Get maximum id from table.
     * @return id
     */
    @Query("SELECT MAX(id) FROM ToDoListItem")
    int getId();
}
