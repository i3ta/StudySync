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
public class SchedulableCardView extends CardView {

    private TextView title;
    private TextView time;
    private TextView location;

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

        title = findViewById(R.id.title);
        time = findViewById(R.id.time);
        location = findViewById(R.id.location);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setTime(String time) {
        this.time.setText(time);
        ViewGroup.LayoutParams params = this.time.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.time.setLayoutParams(params);
    }

    public void setLocation(String location) {
        this.location.setText(location);
        ViewGroup.LayoutParams params = this.location.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.location.setLayoutParams(params);
    }
}