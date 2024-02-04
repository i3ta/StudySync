package spr2024.cs2340.group9.studysync;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import spr2024.cs2340.group9.studysync.database.ToDoList;
import spr2024.cs2340.group9.studysync.database.ToDoLists;

/**
 *This class is for the ToDoList page, by clicking on ToDoList
 * on this page you can enter ToDoListItem Page, by long clicking
 * you can delete a to do list
 */
public class TodolistFragment extends Fragment {

    private List<ToDoList> toDoLists; // Assume you have a data source for your ListView
    private ListView listViewToDoLists;
    private ToDoLists toDoListDB;

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
        toDoListDB = new ToDoLists();
        toDoListDB.init(getContext());

        updateToDoListView();

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

                // Create a Bundle to hold the arguments
                Bundle args = new Bundle();
                args.putString("todoListName", selectedToDoList.getName());
                args.putInt("todoListid", selectedToDoList.getId());

                // Use NavController to navigate
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_toDoListFragment_to_textViewTodoListName, args);
            }
        });
        listViewToDoLists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ToDoList selectedToDoList = toDoLists.get(position);
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme);
                builder.setTitle("Delete To Do List");
                builder.setMessage("Are You Sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toDoListDB.deleteList(selectedToDoList.getId());
                        updateToDoListView();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                androidx.appcompat.app.AlertDialog dialog = builder.create();
                dialog.show();
                return true;
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

                // Add the new ToDoList to DB
                ToDoList toDoList = new ToDoList(todoName);
                toDoListDB.insert(toDoList);
                updateToDoListView();
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

    private void updateToDoListView() {
        // Load exams from the database using your Exams helper class
        toDoLists  = Arrays.asList(toDoListDB.getAllLists());
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) listViewToDoLists.getAdapter();

        // Update the adapter with the loaded exams
        adapter.clear();
        adapter.addAll(getToDoListNames());
        adapter.notifyDataSetChanged();
    }

    private List<String> getToDoListNames() {
        List<String> names = new ArrayList<>();
        for (ToDoList toDoList : toDoLists) {
            names.add(toDoList.getName());
        }
        return names;
    }


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
}
