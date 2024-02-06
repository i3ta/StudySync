package spr2024.cs2340.group9.studysync;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

/**
 * View for CardViews for schedulable items (courses, assignments, and exams).
 */
public class AssignmentSchedulableCardView extends CardView {

    TextView title;
    TextView dueDate;

    TextView notifyBefore;

    public AssignmentSchedulableCardView(Context context) {
        super(context);
        init(context);
    }

    public AssignmentSchedulableCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AssignmentSchedulableCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.assignment_card, this, true);

        title = findViewById(R.id.title);
        dueDate = findViewById(R.id.dueDate);
        notifyBefore = findViewById(R.id.notifyBefore);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setDueDate(String dueDate) {
        this.dueDate.setText(dueDate);
        ViewGroup.LayoutParams params = this.dueDate.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.dueDate.setLayoutParams(params);
    }

    public void setNotifyBefore(boolean notify, String notifyBefore) {
        this.notifyBefore.setText(String.format("Minutes before notification: %s", notifyBefore));
        ViewGroup.LayoutParams params = this.notifyBefore.getLayoutParams();
        params.height = notify ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;
        this.notifyBefore.setLayoutParams(params);
    }
}