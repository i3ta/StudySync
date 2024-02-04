package spr2024.cs2340.group9.studysync;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import spr2024.cs2340.group9.studysync.database.ToDoListItem;
import spr2024.cs2340.group9.studysync.database.ToDoLists;

/**
 *This class is for adding ToDoListItems fragments inside ToDoList.
 */
public class AddToDoListItem extends BottomSheetDialogFragment {
    private EditText editText;
    private Button okButton;
    private Button cancelButton;
    private int ToDoListId;

    /**
     * Initializes item by setting toDoListId so it knows which id to search for in DB.
     * @param toDoListId to-do list id
     */
    public void setToDoListId(int toDoListId) {
        ToDoListId = toDoListId;
    }

    /**
     * Creates new toDoListItem from id from static context.
     * @param ToDoListId id to create new from
     * @return ToDoListItem
     */
    public static AddToDoListItem newInstance(int ToDoListId) {
        AddToDoListItem item = new AddToDoListItem();
        item.setToDoListId(ToDoListId);
        return item;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_todolist, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = view.findViewById(R.id.todolist_editText);
        okButton = view.findViewById(R.id.todolist_buttonOk);
        cancelButton = view.findViewById(R.id.buttonCancel);

        // db initialization
        ToDoLists.init(getContext());

        boolean isUpdate = false;

        Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            editText.setText(task);
            assert task != null;
            if(task.length() > 0){
                okButton.setEnabled(false);
            }
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    okButton.setEnabled(false);
                    okButton.setBackgroundColor(Color.GRAY);
                }
                else{
                    okButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        boolean finalIsUpdate = isUpdate;

        okButton.setOnClickListener(v -> {
            String text = editText.getText().toString();
            if(finalIsUpdate){
                int id = bundle.getInt("id");
                //TODO:update in db
                ToDoLists.updateItemName(id, text);
            }
            else{
                ToDoListItem item = new ToDoListItem(ToDoListId,text,false);
                //insert to db
                ToDoLists.insert(item);
            }
            dismiss();
        });

        cancelButton.setOnClickListener(v -> dismiss());
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener){
            ((DialogCloseListener)activity).DialogClose(dialog);
        }
    }
}
