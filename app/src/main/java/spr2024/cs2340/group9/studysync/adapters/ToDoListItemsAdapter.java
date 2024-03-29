package spr2024.cs2340.group9.studysync.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import spr2024.cs2340.group9.studysync.AddToDoListItem;
import spr2024.cs2340.group9.studysync.R;
import spr2024.cs2340.group9.studysync.database.ToDoListItem;
import spr2024.cs2340.group9.studysync.database.ToDoLists;

public class ToDoListItemsAdapter extends RecyclerView.Adapter<ToDoListItemsAdapter.MyViewHolder> {
    private List<ToDoListItem> toDoListItemList;
    private final FragmentActivity activity;
    //db helper

    public ToDoListItemsAdapter(FragmentActivity activity) {
        this.activity = activity;
        ToDoLists.init(activity.getApplicationContext());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ToDoListItem item = toDoListItemList.get(position);
        holder.checkBox.setText(item.getName());
        holder.checkBox.setChecked(item.isComplete());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // update complete
            ToDoLists.updateItemComplete(item.getId(), isChecked);
        });
    }

    public Context getContext(){
        return activity;
    }

    public void setTask(List<ToDoListItem> toDoListItemList){
        this.toDoListItemList = toDoListItemList;
        notifyDataSetChanged();
    }

    public void removeTask(int pos){
        ToDoListItem item = toDoListItemList.get(pos);
        // delete todolistItem in db
        ToDoLists.delete(item);
        // Update the list in adapter
        List<ToDoListItem> mutableList = new ArrayList<>(toDoListItemList);
        mutableList.remove(pos);
        setTask(mutableList);
    }

    public void editTask(int pos){
        ToDoListItem item = toDoListItemList.get(pos);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getName());

        // Pass data from activity to fragment
        AddToDoListItem addToDoListItem = new AddToDoListItem();
        addToDoListItem.setArguments(bundle);
        addToDoListItem.show(activity.getSupportFragmentManager(), addToDoListItem.getTag());
    }

    @Override
    public int getItemCount() {
        return toDoListItemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
