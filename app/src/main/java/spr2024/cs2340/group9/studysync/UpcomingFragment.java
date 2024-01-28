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

import spr2024.cs2340.group9.studysync.database.Course;
import spr2024.cs2340.group9.studysync.database.Courses;
import spr2024.cs2340.group9.studysync.database.RecurringSlot;
import spr2024.cs2340.group9.studysync.database.TimeSlot;
import spr2024.cs2340.group9.studysync.databinding.UpcomingFragmentBinding;

public class UpcomingFragment extends Fragment {

    private UpcomingFragmentBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        final boolean TESTING = true;

        if (TESTING) {
            Courses.init(getActivity().getApplicationContext());
            Courses.clear();
            Course c = new Course("CS 2340", "Dr. Feijoo", 0, 0);
            c.setCourseTimes(new TimeSlot[]{
                    new TimeSlot(c.id, new RecurringSlot(1, 8, 0), new RecurringSlot(1, 9, 15)),
                    new TimeSlot(c.id, new RecurringSlot(3, 8, 0), new RecurringSlot(3, 9, 15))
            });
            Courses.insert(c);
        }

        binding = UpcomingFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set initial values
        TabLayout dateTabs = view.findViewById(R.id.tabLayout_dateTabs);

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
            monthTextView.setText(firstMonth + " - " + lastMonth);
        }

        // Reset calendar to today
        calendar = Calendar.getInstance();
        // Calendar days of week go from 1 to 7, for Sunday through Saturday
        dateTabs.getTabAt(calendar.get(Calendar.DAY_OF_WEEK) - 1).select();

        setCards(view, new Date());

        // When tab is changed
        dateTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int d = tab.getPosition();
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_WEEK, d);

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

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

        Courses.init(getActivity().getApplicationContext());
        Course[] courses = Courses.getOnDay(cal.get(Calendar.DAY_OF_WEEK) % 7);
        if (courses.length > 0) {
            for (Course c : courses) {
                courseLayout.addView(createCard(c));
            }
        }
    }

    private SchedulableCardView createCard(Course c) {
        SchedulableCardView newCard = new SchedulableCardView(getContext());

        newCard.setCourseTitle(c.name);

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
        newCard.setCourseTime(sb.toString());

        return newCard;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
}