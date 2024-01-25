package spr2024.cs2340.group9.studysync.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ToDoListDao {
    /**
     * Insert to do lists into the database.
     * @param toDoLists to do lists to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ToDoList... toDoLists);

    /**
     * Delete a to do list from the database.
     * @param toDoList to do list to delete
     */
    @Delete
    void delete(ToDoList toDoList);

    /**
     * Delete a to do list from the database with the to do list id.
     * @param id to do list id to delete
     */
    @Query("DELETE FROM ToDoList WHERE id = :id")
    void delete(int id);

    /**
     * Get all to do lists from database.
     * @return to do lists
     */
    @Query("SELECT * FROM ToDoList")
    ToDoList[] getAll();

    /**
     * Get the to do list with the given id.
     * @param id to do list id
     * @return to do list
     */
    @Query("SELECT * FROM ToDoList WHERE id = :id")
    ToDoList get(int id);

    /**
     * Clear ToDoList table.
     */
    @Query("DELETE FROM ToDoList")
    void clear();

    /**
     * Get maximum id from table.
     * @return id
     */
    @Query("SELECT MAX(id) FROM ToDoList")
    int getId();
}
