package spr2024.cs2340.group9.studysync;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import spr2024.cs2340.group9.studysync.adapters.ToDoListItemsAdapter;
import spr2024.cs2340.group9.studysync.database.ToDoList;
import spr2024.cs2340.group9.studysync.database.ToDoListItem;

/**
 *This is the main activity for ToDoListItem page
 */
public class ToDoListItemsActivity extends AppCompatActivity implements DialogCloseListener{

    private ArrayList<String> toDoListItemsNames;
    private RecyclerView recyclerView;
    private ArrayList<ToDoListItem> toDoListItems;
    private ToDoListItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_items);

        // Retrieve ToDoList name from intent extras
        String todoListName = getIntent().getStringExtra("todoListName");

        // Set the ToDoList name to the TextView
        TextView textView = findViewById(R.id.textViewTodoListName);
        textView.setText(todoListName);
        toDoListItems = new ArrayList<>();
        adapter = new ToDoListItemsAdapter(ToDoListItemsActivity.this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //toDoListItems = get all data from db
        //test
        ToDoListItem item = new ToDoListItem(1,"assignment",false);
        toDoListItems.add(item);

        Collections.reverse(toDoListItems);
        adapter.setTask(toDoListItems);

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Set up the "Add Item" button click listener
        Button addItemButton = findViewById(R.id.todolistitem_add_button);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToDoListItem.newInstance().show(getSupportFragmentManager(), "AddToDoListItem");
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    @Override
    public void DialogClose(DialogInterface dialogInterface) {
        //toDoListItems = get all data from db
        Collections.reverse(toDoListItems);
        adapter.setTask(toDoListItems);
        adapter.notifyDataSetChanged();
    }
}