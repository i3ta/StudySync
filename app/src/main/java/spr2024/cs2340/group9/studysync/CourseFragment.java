package spr2024.cs2340.group9.studysync;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import spr2024.cs2340.group9.studysync.adapters.ExamAdapter;
import spr2024.cs2340.group9.studysync.database.Course;
import spr2024.cs2340.group9.studysync.database.Courses;
import spr2024.cs2340.group9.studysync.database.Exam;
import spr2024.cs2340.group9.studysync.database.Exams;
import spr2024.cs2340.group9.studysync.database.RecurringSlot;
import spr2024.cs2340.group9.studysync.database.TimeSlot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CourseFragment extends Fragment {

    private Button addButton;
    private ListView courseListView;
    private Course courseHelper;
    private int currentDay = 1;
    private LinearLayout courses;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_fragment, container, false);

        addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> showDateTimeInputDialog());

        TabLayout tabLayout = view.findViewById(R.id.course_tab_layout);

        tabLayout.selectTab(tabLayout.getTabAt(0));

        courses = view.findViewById(R.id.course_linear_layout);

        Courses.init(requireContext());

                // Set the listener for the bottom navigation view
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentDay = tab.getPosition() + 1;
                System.out.println(currentDay);
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

    private void showDateTimeInputDialog() {
        View courseDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.course_dialog_layout, null);

        TimePicker timePickerStart = courseDialogView.findViewById(R.id.timePickerStart);
        TimePicker timePickerEnd = courseDialogView.findViewById(R.id.timePickerEnd);
        EditText titleEditText = courseDialogView.findViewById(R.id.titleEditText);
        EditText instructorNameText = courseDialogView.findViewById(R.id.instructorNameEditText);
        Button okButton = courseDialogView.findViewById(R.id.save_button);
        Button cancelButton = courseDialogView.findViewById(R.id.cancel_button);

        final Calendar calendar = Calendar.getInstance();

        timePickerStart.setIs24HourView(true);
        timePickerStart.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePickerStart.setMinute(calendar.get(Calendar.MINUTE));

        timePickerEnd.setIs24HourView(true);
        timePickerEnd.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePickerEnd.setMinute(calendar.get(Calendar.MINUTE));

        // Create the dialog
        final AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(courseDialogView)
                .create();

        okButton.setOnClickListener(v -> {

            // Get the selected time from TimePicker
            int hourOfDayStart = timePickerStart.getHour();
            int minuteStart = timePickerStart.getMinute();

            int hourOfDayEnd = timePickerEnd.getHour();
            int minuteEnd = timePickerEnd.getMinute();

            // Get the text from EditText
            String userInputTitle = titleEditText.getText().toString();
            String userInputInstructor = instructorNameText.getText().toString();

            //(String name, String instructorName, int color, int notifyBefore)
            Course newCourse = new Course(userInputTitle, userInputInstructor,0, 0);
            TimeSlot timeSlot = new TimeSlot(newCourse.id, new RecurringSlot(currentDay, hourOfDayStart, minuteStart), new RecurringSlot(currentDay, hourOfDayEnd, minuteEnd));
            newCourse.setCourseTimes(new TimeSlot[]{timeSlot});
            Courses.insert(newCourse);

            updateCourseList();

            dialog.dismiss();
        });
        cancelButton.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private void updateCourseList() {
        courses.removeAllViews();
        Course[] c = Courses.getOnDay(currentDay);
        for (Course d: c) {
            System.out.println(d.toString());
            courses.addView(createCard(d, currentDay));
        }
    }

    private SchedulableCardViewCourse createCard(Course c, int currentDay) {
        SchedulableCardViewCourse newCard = new SchedulableCardViewCourse(getContext());

        newCard.setTitle(c.name);

        StringBuilder sbStart = new StringBuilder();
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

        return newCard;
    }
}
