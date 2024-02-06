package spr2024.cs2340.group9.studysync;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import spr2024.cs2340.group9.studysync.database.Course;
import spr2024.cs2340.group9.studysync.database.Courses;
import spr2024.cs2340.group9.studysync.database.RecurringSlot;
import spr2024.cs2340.group9.studysync.database.TimeSlot;

import java.util.Calendar;
import java.util.Objects;

public class CourseFragment extends Fragment {

    private Button addButton;
    private int currentDay = 1;
    private LinearLayout courseLinearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_fragment, container, false);

        addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> showNewCourseDialog());

        TabLayout tabLayout = view.findViewById(R.id.course_tab_layout);

        Objects.requireNonNull(tabLayout.getTabAt(0)).select();

        courseLinearLayout = view.findViewById(R.id.course_linear_layout);
        updateCourseList();

        Courses.init(requireContext());

        // Set the listener for the bottom navigation view
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentDay = tab.getPosition() + 1;
                updateCourseList();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    private void showNewCourseDialog() {
        final Calendar calendar = Calendar.getInstance();
        dialogHelper(new Course(), null);
    }

    private void showEditCourseDialog(Course c, TimeSlot t) {
        dialogHelper(c, t);
    }

    /**
     * Private helper function to help set up the new/edit course dialog
     * @param c Course
     */
    private void dialogHelper(Course c, TimeSlot t) {
        View courseDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.course_dialog_layout, null);

        // Create the dialog
        final AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(courseDialogView)
                .create();

        TimePicker timePickerStart = courseDialogView.findViewById(R.id.timePickerStart);
        TimePicker timePickerEnd = courseDialogView.findViewById(R.id.timePickerEnd);
        EditText titleEditText = courseDialogView.findViewById(R.id.titleEditText);
        EditText instructorNameEditText = courseDialogView.findViewById(R.id.instructorNameEditText);
        Button okButton = courseDialogView.findViewById(R.id.save_button);
        Button cancelButton = courseDialogView.findViewById(R.id.cancel_button);

        timePickerStart.setIs24HourView(true);
        timePickerEnd.setIs24HourView(true);

        if (c.name != null) {
            titleEditText.setText(c.name);
        }
        if (c.instructorName != null) {
            instructorNameEditText.setText(c.instructorName);
        }
        if (t != null) {
            timePickerStart.setHour(t.getStartTime().getHour());
            timePickerStart.setMinute(t.getStartTime().getMinute());
            timePickerEnd.setHour(t.getEndTime().getHour());
            timePickerEnd.setMinute(t.getEndTime().getMinute());
        }

        okButton.setOnClickListener(v -> {

            // Get the selected time from TimePicker
            int hourOfDayStart = timePickerStart.getHour();
            int minuteStart = timePickerStart.getMinute();

            int hourOfDayEnd = timePickerEnd.getHour();
            int minuteEnd = timePickerEnd.getMinute();

            // Get the text from EditText
            String userInputTitle = titleEditText.getText().toString();
            String userInputInstructor = instructorNameEditText.getText().toString();

            //(String name, String instructorName, int color, int notifyBefore)
            c.name = userInputTitle;
            c.instructorName = userInputInstructor;
            TimeSlot timeSlot = new TimeSlot(c.id,
                    new RecurringSlot(currentDay, hourOfDayStart, minuteStart),
                    new RecurringSlot(currentDay, hourOfDayEnd, minuteEnd));
            c.setCourseTimes(new TimeSlot[]{timeSlot});
            Courses.insert(c);

            updateCourseList();

            dialog.dismiss();
        });
        cancelButton.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void updateCourseList() {
        courseLinearLayout.removeAllViews();
        Course[] courses = Courses.getOnDay(currentDay);
        for (Course course: courses) {
            courseLinearLayout.addView(createCard(course, currentDay));
        }
    }

    private CourseCardView createCard(final Course c, int currentDay) {
        CourseCardView newCard = new CourseCardView(getContext());

        newCard.setTitle(c.name);
        newCard.setInstructorName(c.instructorName);

        TimeSlot time = null;
        for (TimeSlot t : c.getCourseTimes()) {
            if (t.getStartTime().getDayOfWeek() == currentDay) {
                time = t;
                break;
            }
        }
        assert time != null;
        newCard.setTimeStart(time.getStartTime().toString());
        newCard.setTimeEnd(time.getEndTime().toString());
        final TimeSlot t = new TimeSlot(time);

        newCard.setOnLongClickListener(v -> {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme);
            alertBuilder.setTitle(String.format("Edit \"%s\"", c.name))
                    .setNegativeButton("Delete", (dialog, which) -> {
                        Courses.delete(c);
                        updateCourseList();
                    })
                    .setNeutralButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .setPositiveButton("Edit", (dialog, which) -> {
                        showEditCourseDialog(c, t);
                    });
            AlertDialog dialog = alertBuilder.create();
            dialog.show();
            return true;
        });

        return newCard;
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }

    @Override
    public void onPause() {
        super.onPause();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}
