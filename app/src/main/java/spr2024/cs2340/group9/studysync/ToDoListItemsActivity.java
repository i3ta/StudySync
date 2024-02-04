package spr2024.cs2340.group9.studysync;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
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
 *This is the main fragment for ToDoListItem page
 */
public class ToDoListItemsActivity extends Fragment implements DialogCloseListener{
    private RecyclerView recyclerView;
    private List<ToDoListItem> toDoListItems;
    private ToDoListItemsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_todo_list_items, container, false);

        // Retrieve ToDoList name from arguments
        String todoListName = getArguments().getString("todoListName");

        TextView textView = view.findViewById(R.id.textViewTodoListName);
        textView.setText(todoListName);
        toDoListItems = new ArrayList<>();
        adapter = new ToDoListItemsAdapter(getActivity());

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Initialize DB
        ToDoLists.init(getContext());

        // Display the items in DB on recyclerView
        updateView();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Button addItemButton = view.findViewById(R.id.todolistitem_add_button);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int todoListId = getArguments().getInt("todoListid", 1);
                AddToDoListItem.newInstance(todoListId).show(getChildFragmentManager(), "AddToDoListItem");
                updateView();
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void DialogClose(DialogInterface dialogInterface) {
        updateView();
        adapter.notifyDataSetChanged();
    }
    public void updateView(){
        int todoListId = getArguments().getInt("todoListid", 1);
        toDoListItems = Arrays.asList(ToDoLists.getListItems(todoListId));
        Collections.reverse(toDoListItems);
        adapter.setTask(toDoListItems);
    }
}