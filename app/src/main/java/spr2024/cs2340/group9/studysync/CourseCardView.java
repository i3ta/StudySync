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
public class CourseCardView extends CardView {

    private TextView title;
    private TextView timeStart;
    private TextView timeEnd;
    private TextView instructorName;

    public CourseCardView(Context context) {
        super(context);
        init(context);
    }

    public CourseCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CourseCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.card_course_view, this, true);

        title = findViewById(R.id.title);
        timeStart = findViewById(R.id.timeStart);
        timeEnd = findViewById(R.id.timeEnd);
        instructorName = findViewById(R.id.instructorName);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setTimeStart(String timeStart) {
        if (timeStart == null || timeStart.isEmpty()) {
            return;
        }
        this.timeStart.setText(timeStart);
        ViewGroup.LayoutParams params = this.timeStart.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.timeStart.setLayoutParams(params);
    }

    public void setTimeEnd(String timeEnd) {
        if (timeEnd == null || timeEnd.isEmpty()) {
            return;
        }
        this.timeEnd.setText(String.format("- %s", timeEnd));
        ViewGroup.LayoutParams params = this.timeEnd.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.timeEnd.setLayoutParams(params);
    }

    public void setInstructorName(String instructorName) {
        if (instructorName == null || instructorName.isEmpty()) {
            return;
        }
        this.instructorName.setText(instructorName);
        ViewGroup.LayoutParams params = this.instructorName.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.instructorName.setLayoutParams(params);
    }
}