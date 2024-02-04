package spr2024.cs2340.group9.studysync.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import spr2024.cs2340.group9.studysync.R;
import spr2024.cs2340.group9.studysync.database.Exam;

public class ExamAdapter extends ArrayAdapter<Exam> {

    private LayoutInflater inflater;

    public ExamAdapter(Context context, List<Exam> exams) {
        super(context, 0, exams);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.exam_item, parent, false);
        }

        Exam exam = getItem(position);

        // Set up the TextViews in custom layout
        TextView titleTextView = view.findViewById(R.id.titleTextView);
        TextView locationTextView = view.findViewById(R.id.locationTextView);
        TextView dateTextView = view.findViewById(R.id.dateTextView);
        TextView hiddenIdView = view.findViewById(R.id.hiddenId);

        titleTextView.setText(exam.getName());

        // You may format the date according to your needs
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm");
        dateTextView.setText("Time: " + dateFormat.format(exam.getStartTime()));
        locationTextView.setText("Location: " + exam.getLocation());
        hiddenIdView.setText(String.valueOf(exam.getId()));

        return view;
    }
}

