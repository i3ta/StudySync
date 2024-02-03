package spr2024.cs2340.group9.studysync;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import spr2024.cs2340.group9.studysync.adapters.ToDoListItemsAdapter;
import spr2024.cs2340.group9.studysync.database.ToDoListItem;
import spr2024.cs2340.group9.studysync.database.ToDoLists;

/**
 *This is the main activity for ToDoListItem page
 */
public class ToDoListItemsActivity extends AppCompatActivity implements DialogCloseListener{
    private RecyclerView recyclerView;
    private List<ToDoListItem> toDoListItems;
    private ToDoListItemsAdapter adapter;
    private ToDoLists toDoListsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_items);

        // Retrieve ToDoList name from intent extras
        String todoListName = getIntent().getStringExtra("todoListName");

        TextView textView = findViewById(R.id.textViewTodoListName);
        textView.setText(todoListName);
        toDoListItems = new ArrayList<>();
        adapter = new ToDoListItemsAdapter(ToDoListItemsActivity.this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Initialize DB
        toDoListsDB = new ToDoLists();
        toDoListsDB.init(getApplicationContext());

        // Display the items in DB on recyclerView
        updateView();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button addItemButton = findViewById(R.id.todolistitem_add_button);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int todoListId = getIntent().getIntExtra("todoListid", 1);
                AddToDoListItem.newInstance(todoListId).show(getSupportFragmentManager(), "AddToDoListItem");
                updateView();
//                adapter.notifyDataSetChanged();
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    @Override
    public void DialogClose(DialogInterface dialogInterface) {
        updateView();
        adapter.notifyDataSetChanged();
    }
    public void updateView(){
        int todoListId = getIntent().getIntExtra("todoListid", 1);
        toDoListItems = Arrays.asList(toDoListsDB.getListItems(todoListId));
        Collections.reverse(toDoListItems);
        adapter.setTask(toDoListItems);
    }
}