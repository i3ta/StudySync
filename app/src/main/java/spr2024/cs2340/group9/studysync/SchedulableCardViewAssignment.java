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
public class SchedulableCardViewAssignment extends CardView {

    TextView title;
    TextView dueDate;

    public SchedulableCardViewAssignment(Context context) {
        super(context);
        init(context);
    }

    public SchedulableCardViewAssignment(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SchedulableCardViewAssignment(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.assignment_schedulable, this, true);

        title = findViewById(R.id.title);
        dueDate = findViewById(R.id.timeStart);
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
}