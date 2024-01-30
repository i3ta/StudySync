package spr2024.cs2340.group9.studysync;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import spr2024.cs2340.group9.studysync.database.ToDoList;

/**
 *This class is for the ToDoList page, by clicking on ToDoList on this page you can enter ToDoListItem Page
 */
public class TodolistFragment extends Fragment {

    private ArrayList<ToDoList> toDoLists; // Assume you have a data source for your ListView
    private ListView listViewToDoLists;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todolists, container, false);

        toDoLists = new ArrayList<>(); // Initialize your data source

        // Assume you have a simple ArrayAdapter to display ToDoList names
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                getToDoListNames()
        );

        listViewToDoLists = view.findViewById(R.id.todolists_listView);
        listViewToDoLists.setAdapter(adapter);

        Button addButton = view.findViewById(R.id.tododlist_addbutton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the dialog
                showAddTodoDialog();
            }
        });

        listViewToDoLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle click on ToDoList row
                ToDoList selectedToDoList = toDoLists.get(position);

                // Navigate to ToDoListItemsActivity
                Intent intent = new Intent(requireContext(), ToDoListItemsActivity.class);
                intent.putExtra("todoListName", selectedToDoList.getName());
                startActivity(intent);
            }
        });

        return view;
    }

    private void showAddTodoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_todolist, null);
        builder.setView(dialogView);

        EditText editTextTodoName = dialogView.findViewById(R.id.todolist_editText);
        Button buttonOk = dialogView.findViewById(R.id.todolist_buttonOk);
        Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);

        AlertDialog dialog = builder.create();

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user input
                String todoName = editTextTodoName.getText().toString();

                // Add the new ToDoList
                ToDoList toDoList = new ToDoList(todoName);
                toDoLists.add(toDoList);

                // Update the ArrayAdapter with the new data
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) listViewToDoLists.getAdapter();
                adapter.clear();
                adapter.addAll(getToDoListNames());
                adapter.notifyDataSetChanged();

                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private List<String> getToDoListNames() {
        List<String> names = new ArrayList<>();
        for (ToDoList toDoList : toDoLists) {
            names.add(toDoList.getName());
        }
        return names;
    }
}
