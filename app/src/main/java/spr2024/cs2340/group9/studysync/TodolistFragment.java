package spr2024.cs2340.group9.studysync;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
        ToDoLists.init(getContext());

        updateToDoListView();

        Button addButton = view.findViewById(R.id.tododlist_addbutton);
        addButton.setOnClickListener(v -> {
            System.out.println("Add to do list item");
            // Show the dialog
            showAddTodoDialog();
        });

        // Normal click; open fragment showing items
        listViewToDoLists.setOnItemClickListener((parent, view1, position, id) -> {
            // Handle click on ToDoList row
            ToDoList selectedToDoList = toDoLists.get(position);

            // Create a Bundle to hold the arguments
            Bundle args = new Bundle();
            args.putString("todoListName", selectedToDoList.getName());
            args.putInt("todoListid", selectedToDoList.getId());

            // Use NavController to navigate
            NavController navController = Navigation.findNavController(view1);
            navController.navigate(R.id.action_toDoListFragment_to_textViewTodoListName, args);
        });

        listViewToDoLists.setOnItemLongClickListener((parent, view12, position, id) -> {
            ToDoList selectedToDoList = toDoLists.get(position);
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme);
            builder.setTitle("Select Action");

            builder.setItems(new String[]{"Edit", "Delete"}, (dialog, which) -> {
                if (which == 0) {
                    showAddTodoDialog(selectedToDoList);
                    updateToDoListView();
                    // Edit option selected
                } else if (which == 1) {
                    // Delete the to-do list
                    ToDoLists.delete(selectedToDoList);
                    updateToDoListView();
                }
            });

            builder.setNegativeButton("Cancel", null);
            builder.show();

            return true;
        });


        return view;
    }

    private void showAddTodoDialog(ToDoList selectedToDoList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_todolist, null);
        builder.setView(dialogView);

        EditText editTextTodoName = dialogView.findViewById(R.id.todolist_editText);
        Button buttonOk = dialogView.findViewById(R.id.todolist_buttonOk);
        Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);

        AlertDialog dialog = builder.create();

        buttonOk.setOnClickListener(v -> {
            // Get the user input
            String todoName = editTextTodoName.getText().toString();

            // Add the new ToDoList to DB
            selectedToDoList.setName(todoName);
            ToDoLists.insert(selectedToDoList);
            updateToDoListView();
            dialog.dismiss();
        });

        buttonCancel.setOnClickListener(v -> {
            // Dismiss the dialog
            dialog.dismiss();
        });

        dialog.show();
    }

    private void showAddTodoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_todolist, null);
        builder.setView(dialogView);

        EditText editTextTodoName = dialogView.findViewById(R.id.todolist_editText);
        Button buttonOk = dialogView.findViewById(R.id.todolist_buttonOk);
        Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);

        AlertDialog dialog = builder.create();

        buttonOk.setOnClickListener(v -> {
            // Get the user input
            String todoName = editTextTodoName.getText().toString();

            // Add the new ToDoList to DB
            ToDoList toDoList = new ToDoList(todoName);
            ToDoLists.insert(toDoList);
            updateToDoListView();
            dialog.dismiss();
        });

        buttonCancel.setOnClickListener(v -> {
            // Dismiss the dialog
            dialog.dismiss();
        });

        dialog.show();
    }

    private void updateToDoListView() {
        // Load exams from the database using your Exams helper class
        toDoLists  = Arrays.asList(ToDoLists.getAllLists());
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) listViewToDoLists.getAdapter();

        // Update the adapter with the loaded exams
        System.out.println("Get to do list update");
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
        ((AppCompatActivity) requireActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((AppCompatActivity) requireActivity()).getSupportActionBar().show();
    }
}
