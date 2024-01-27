package spr2024.cs2340.group9.studysync;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;
import androidx.cardview.widget.CardView;

public class SchedulableCardView extends CardView {

    private TextView courseTitle;
    private TextView courseTime;

    public SchedulableCardView(Context context) {
        super(context);
        init(context);
    }

    public SchedulableCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SchedulableCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.card_schedulable_view, this, true);

        courseTitle = findViewById(R.id.courseTitle);
        courseTime = findViewById(R.id.courseTime);
    }

    public void setCourseTitle(String title) {
        courseTitle.setText(title);
    }

    public void setCourseTime(String time) {
        courseTime.setText(time);
    }
}