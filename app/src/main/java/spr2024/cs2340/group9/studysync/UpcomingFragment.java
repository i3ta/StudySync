package spr2024.cs2340.group9.studysync;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import spr2024.cs2340.group9.studysync.database.Assignment;
import spr2024.cs2340.group9.studysync.database.Assignments;
import spr2024.cs2340.group9.studysync.database.Course;
import spr2024.cs2340.group9.studysync.database.Courses;
import spr2024.cs2340.group9.studysync.database.Exam;
import spr2024.cs2340.group9.studysync.database.Exams;
import spr2024.cs2340.group9.studysync.database.RecurringSlot;
import spr2024.cs2340.group9.studysync.database.TimeSlot;
import spr2024.cs2340.group9.studysync.databinding.UpcomingFragmentBinding;

/**
 * Fragment for upcoming page.
 */
public class UpcomingFragment extends Fragment {

    private UpcomingFragmentBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = UpcomingFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set initial values
        TabLayout dateTabs = view.findViewById(R.id.tabLayout_dateTabs);
        dateTabs.removeAllTabs();

        Calendar calendar = Calendar.getInstance();
        int daysToSubtract = (calendar.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY + 7) % 7;
        calendar.add(Calendar.DATE, -daysToSubtract);

        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
        String firstMonth = monthFormat.format(calendar.getTime());

        for (int i = 0; i < 7; i++) {
            String date = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            dateTabs.addTab(dateTabs.newTab().setText(date));
            calendar.add(Calendar.DATE, 1);
        }

        String lastMonth = monthFormat.format(calendar.getTime());

        TextView monthTextView = view.findViewById(R.id.textView);
        if (firstMonth.equals(lastMonth)) {
            monthTextView.setText(firstMonth);
        } else {
            monthTextView.setText(String.format("%s - %s", firstMonth, lastMonth));
        }

        // Reset calendar to today
        calendar = Calendar.getInstance();
        // Calendar days of week go from 1 to 7, for Sunday through Saturday
        Objects.requireNonNull(dateTabs.getTabAt(calendar.get(Calendar.DAY_OF_WEEK) - 1)).select();

        setCards(view, new Date());

        // When tab is changed
        dateTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int d = tab.getPosition();
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_WEEK, d + 1);

                Date date = cal.getTime();
                setCards(view, date);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void setCards(@NonNull View v, Date d) {
        LinearLayout courseLayout = v.findViewById(R.id.linearLayout_courses);
        courseLayout.removeAllViews();
        LinearLayout assignmentLayout = v.findViewById(R.id.linearLayout_assignments);
        assignmentLayout.removeAllViews();
        LinearLayout examLayout = v.findViewById(R.id.linearLayout_exams);
        examLayout.removeAllViews();

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

        Courses.init(requireContext());
        Course[] courses = Courses.getOnDay(cal.get(Calendar.DAY_OF_WEEK) - 1);
        if (courses.length > 0) {
            for (Course c : courses) {
                if (c == null) {
                    continue;
                }
                courseLayout.addView(createCard(c));
            }
        } else {
            TextView textView = new TextView(requireContext());
            textView.setText(R.string.Upcoming_NoCourseLabel);
            textView.setPadding(48, 8,8,8);
            courseLayout.addView(textView);
        }

        Assignments.init(requireContext());
        Assignment[] assignments = Assignments.getBetween(getStartOfDay(d), getEndOfDay(d));
        if (assignments.length > 0) {
            for (Assignment a: assignments) {
                assignmentLayout.addView(createCard(a));
            }
        } else {
            TextView textView = new TextView(requireContext());
            textView.setText(R.string.Upcoming_NoAssignmentsDueLabel);
            textView.setPadding(48, 8,8,8);
            assignmentLayout.addView(textView);
        }

        Exams.init(requireContext());
        Exam[] exams = Exams.getBetween(getStartOfDay(d), getEndOfDay(d));
        if (exams.length > 0) {
            for (Exam e: exams) {
                examLayout.addView(createCard(e));
            }
        } else {
            TextView textView = new TextView(requireContext());
            textView.setText(R.string.Upcoming_NoExamsLabel);
            textView.setPadding(48, 8,8,8);
            examLayout.addView(textView);
        }
    }

    private SchedulableCardView createCard(Course c) {
        SchedulableCardView newCard = new SchedulableCardView(getContext());

        newCard.setTitle(c.name);

        StringBuilder sb = new StringBuilder();
        for (TimeSlot t : c.getCourseTimes()) {
            RecurringSlot st = t.getStartTime();
            RecurringSlot et = t.getEndTime();
            boolean sameDay = st.getDayOfWeek() == et.getDayOfWeek();
            if (sb.length() != 0) {
                sb.append("\n");
            }
            sb.append(String.format(Locale.getDefault(), "%s %02d:%02d - %s%02d:%02d",
                    st.getDayOfWeekString(), st.getHour(), st.getMinute(),
                    (sameDay ? "" : et.getDayOfWeekString()), et.getHour(), et.getMinute()));
        }
        newCard.setTime(sb.toString());

        return newCard;
    }

    private SchedulableCardView createCard(Assignment a) {
        SchedulableCardView newCard = new SchedulableCardView(getContext());

        newCard.setTitle(a.name);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        newCard.setTime(String.format("Due %s", format.format(a.getDueDate())));

        return newCard;
    }

    private SchedulableCardView createCard(Exam e) {
        SchedulableCardView newCard = new SchedulableCardView(getContext());

        newCard.setTitle(e.name);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        newCard.setTime(String.format("Starts %s",
                format.format(e.getStartTime())));
        if (e.location != null && !e.location.isEmpty()) {
            newCard.setLocation(e.location);
        }

        return newCard;
    }

    private Date getStartOfDay(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);

        // Set the calendar to start of date
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date getEndOfDay(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);

        // Set the calendar to end of date
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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