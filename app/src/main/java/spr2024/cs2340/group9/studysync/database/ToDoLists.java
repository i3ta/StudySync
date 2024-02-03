package spr2024.cs2340.group9.studysync.database;

import android.content.Context;

import androidx.room.Room;

public class ToDoLists {
    private static ToDoListDatabase db;
    private static ToDoListDao toDoListDao;
    private static ToDoListItemDao toDoListItemDao;

    /**
     * Force static methods.
     */
//    private ToDoLists() {}

    /**
     * Create a new instance of the ToDoLists database.
     * @param applicationContext context of the current application; use getApplicationContext() to get
     */
    public static void init(Context applicationContext) {
        if (db == null) {
            db = Room.databaseBuilder(applicationContext, ToDoListDatabase.class, "ToDoList")
                    .allowMainThreadQueries()
                    .build();
            toDoListDao = db.toDoListDao();
            toDoListItemDao = db.toDoListItemDao();
        }
    }

    /**
     * Insert new to do list(s).
     * @param toDoLists to do list(s)
     */
    public static void insert(ToDoList... toDoLists) {
        toDoListDao.insert(toDoLists);
        for (ToDoList toDoList: toDoLists) {
            if (toDoList.toDoListItems == null) {
                continue;
            }
            toDoListItemDao.insert(toDoList.toDoListItems);
        }
    }

    /**
     * Insert new to do list item(s).
     * @param toDoListItems to do list item(s)
     */
    public static void insert(ToDoListItem... toDoListItems) {
        toDoListItemDao.insert(toDoListItems);
    }

    /**
     * Delete to do list.
     * @param toDoList to do list to delete
     */
    public static void delete(ToDoList toDoList) {
        toDoListDao.delete(toDoList);
        toDoListItemDao.deleteList(toDoList.id);
    }

    /**
     * Delete to do list item.
     * @param toDoListItem to do list item to delete
     */
    public static void delete(ToDoListItem toDoListItem) {
        toDoListItemDao.delete(toDoListItem);
    }

    /**
     * Delete to do list with id.
     * @param id to do list id
     */
    public static void deleteList(int id) {
        toDoListDao.delete(id);
        toDoListItemDao.deleteList(id);
    }

    /**
     * Delete to do list item with id.
     * @param id to do list item id
     */
    public static void deleteItem(int id) {
        toDoListItemDao.delete(id);
    }

    /**
     * Get all to do lists.
     * @return to do lists
     */
    public static ToDoList[] getAllLists() {
        ToDoList[] lists = toDoListDao.getAll();
        for (ToDoList list: lists) {
            ToDoListItem[] items = toDoListItemDao.getList(list.id);
            if (items.length != 0) {
                list.toDoListItems = items;
            } else {
                list.toDoListItems = null;
            }
        }
        return lists;
    }

    /**
     * Get all to do list items.
     * @return to do list items
     */
    public static ToDoListItem[] getAllItems() {
        return toDoListItemDao.getAll();
    }

    /**
     * Get to do list with id.
     * @param id to do list id
     * @return to do list
     */
    public static ToDoList getList(int id) {
        ToDoList list = toDoListDao.get(id);
        ToDoListItem[] items = toDoListItemDao.getList(list.id);
        if (items.length != 0) {
            list.toDoListItems = items;
        } else {
            list.toDoListItems = null;
        }
        return list;
    }

    public static ToDoListItem[] getListItems(int id) {
        ToDoList list = toDoListDao.get(id);
        ToDoListItem[] items = toDoListItemDao.getList(list.id);
        return items;
    }

    /**
     * Get to do list with only incomplete items.
     * @param id to do list id
     * @return to do list
     */
    public static ToDoList getListIncomplete(int id) {
        ToDoList list = toDoListDao.get(id);
        ToDoListItem[] items = toDoListItemDao.getIncomplete(list.id);
        if (items.length != 0) {
            list.toDoListItems = items;
        } else {
            list.toDoListItems = null;
        }
        return list;
    }

    public static void updateItemName(int id, String newName){
        toDoListItemDao.updateNameById(id,newName);
    }

    public static void updateItemComplete(int id, Boolean newComplete){
        toDoListItemDao.updateCompleteById(id, newComplete);
    }

    public static void clear() {
        toDoListDao.clear();
        toDoListItemDao.clear();
    }
}
