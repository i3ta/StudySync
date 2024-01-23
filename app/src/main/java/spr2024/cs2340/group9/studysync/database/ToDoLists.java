package spr2024.cs2340.group9.studysync.database;

import android.content.Context;

import androidx.room.Room;

public class ToDoLists {
    private static ToDoListDatabase db;
    private static ToDoListDao toDoListDao;
    private static ToDoListItemDao toDoListItemDao;

    /**
     * Create a new instance of the ToDoLists database.
     * @param applicationContext context of the current application; use getApplicationContext() to get
     */
    public ToDoLists(Context applicationContext) {
        if (db == null) {
            db = Room.databaseBuilder(applicationContext, ToDoListDatabase.class, "ToDoList").build();
            toDoListDao = db.toDoListDao();
            toDoListItemDao = db.toDoListItemDao();
        }
    }

    /**
     * Insert new to do list(s).
     * @param toDoLists to do list(s)
     */
    public void insert(ToDoList... toDoLists) {
        toDoListDao.insert(toDoLists);
        for (ToDoList toDoList: toDoLists) {
            toDoListItemDao.insert(toDoList.toDoListItems);
        }
    }

    /**
     * Insert new to do list item(s).
     * @param toDoListItems to do list item(s)
     */
    public void insert(ToDoListItem... toDoListItems) {
        toDoListItemDao.insert(toDoListItems);
    }

    /**
     * Delete to do list.
     * @param toDoList to do list to delete
     */
    public void delete(ToDoList toDoList) {
        toDoListDao.delete(toDoList);
        toDoListItemDao.deleteList(toDoList.id);
    }

    /**
     * Delete to do list item.
     * @param toDoListItem to do list item to delete
     */
    public void delete(ToDoListItem toDoListItem) {
        toDoListItemDao.delete(toDoListItem);
    }

    /**
     * Delete to do list with id.
     * @param id to do list id
     */
    public void deleteList(int id) {
        toDoListDao.delete(id);
    }

    /**
     * Delete to do list item with id.
     * @param id to do list item id
     */
    public void deleteItem(int id) {
        toDoListItemDao.delete(id);
    }

    /**
     * Get all to do lists.
     * @return to do lists
     */
    public ToDoList[] getAllLists() {
        return toDoListDao.getAll();
    }

    /**
     * Get all to do list items.
     * @return to do list items
     */
    public ToDoListItem[] getAllItems() {
        return toDoListItemDao.getAll();
    }

    /**
     * Get to do list with id.
     * @param id to do list id
     * @return to do list
     */
    public ToDoList getList(int id) {
        ToDoList list = toDoListDao.get(id);
        list.toDoListItems = toDoListItemDao.getList(list.id);
        return list;
    }

    /**
     * Get to do list with only incomplete items.
     * @param id to do list id
     * @return to do list
     */
    public ToDoList getListIncomplete(int id) {
        ToDoList list = toDoListDao.get(id);
        list.toDoListItems = toDoListItemDao.getIncomplete(id);
        return list;
    }
}
