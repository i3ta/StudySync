package spr2024.cs2340.group9.studysync;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import spr2024.cs2340.group9.studysync.adapters.ToDoListItemsAdapter;

/**
 *This class is for editing and deleting items by swiping.
 */
public class RecyclerViewHelper extends ItemTouchHelper.SimpleCallback {
    private final ToDoListItemsAdapter adapter;

    public RecyclerViewHelper(ToDoListItemsAdapter adapter) {
        super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int pos = viewHolder.getAbsoluteAdapterPosition();
        // Right Swipe Delete
        if(direction == ItemTouchHelper.RIGHT){
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Delete Task");
            builder.setMessage("Are You Sure?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                adapter.removeTask(pos);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> adapter.notifyItemChanged(pos));
            AlertDialog dialog = builder.create();
            dialog.show();
        } else{
            // left Swipe Edit
            adapter.editTask(pos);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftBackgroundColor(ContextCompat.getColor(adapter.getContext(), com.google.android.material.R.color.cardview_dark_background))
                .addSwipeLeftActionIcon(R.drawable.baseline_edit)
                .addSwipeRightBackgroundColor(Color.RED)
                .addSwipeRightActionIcon(R.drawable.baseline_delete)
                .create()
                .decorate();
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
