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
import android.widget.Switch;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Objects;

import spr2024.cs2340.group9.studysync.database.Assignment;
import spr2024.cs2340.group9.studysync.database.Assignments;
import spr2024.cs2340.group9.studysync.database.TimeSlot;

/**
 * Exam Fragment.
 */
public class AssignmentFragment extends Fragment {

    private Button addButton;
    private LinearLayout assignmentListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.assignment_fragment, container, false);

        addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> showDateTimeInputDialog());

        // Initialization
        assignmentListView = view.findViewById(R.id.linearLayout);

        // Load exams from the database
        updateAssignmentList();

        return view;
    }

    /**
     * Initializes dateTimeInputDialog.
     */
    private void showDateTimeInputDialog() {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.assignment_dialog_view, null);

        DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        TimePicker timePicker = dialogView.findViewById(R.id.timePicker);
        EditText titleEditText = dialogView.findViewById(R.id.titleEditText);
        EditText notifyBeforeText = dialogView.findViewById(R.id.notifyBeforeEditText);
        Switch notifySwitch = dialogView.findViewById(R.id.courseNotifySwitch);
        Button okButton = dialogView.findViewById(R.id.save_button);
        Button cancelButton = dialogView.findViewById(R.id.cancel_button);

        // Set up date picker
        final Calendar calendar = Calendar.getInstance();
        datePicker.init(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null
        );

        // Set up time picker
        timePicker.setIs24HourView(true);
        timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(calendar.get(Calendar.MINUTE));

        // Create the dialog
        final AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create();

        okButton.setOnClickListener(v -> {
            // Get the selected date from DatePicker
            int year = datePicker.getYear();
            int month = datePicker.getMonth();
            int dayOfMonth = datePicker.getDayOfMonth();

            // Get the selected time from TimePicker
            int hourOfDay = timePicker.getHour();
            int minute = timePicker.getMinute();

            // Get the text from EditText
            String titleInput = titleEditText.getText().toString();

            // Construct Calendar object from selected date and time
            Calendar selectedDateTime = Calendar.getInstance();
            selectedDateTime.set(year, month, dayOfMonth, hourOfDay, minute);
            //notify one hour before
            String notifyBefore = notifyBeforeText.getText().toString();
            Assignment newAssignment = new Assignment(titleInput,0,
                    selectedDateTime.getTimeInMillis(), notifySwitch.isChecked(),
                    notifyBefore.isBlank() ? 0 : Integer.parseInt(notifyBefore));
            Assignments.insert(newAssignment);

            // Update the ListView
            updateAssignmentList();

            dialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    /**
     * Updates exam list view with updated database.
     */
    private void updateAssignmentList() {
        assignmentListView.removeAllViews();
        Calendar now = Calendar.getInstance();
        Calendar future = Calendar.getInstance();
        future.add(Calendar.MONTH, 1);
        Assignment[] assignments = Assignments.getBetween(now.getTime(), future.getTime());
        for (Assignment assignment : assignments) {
            View assignmentView = createCard(assignment);
            assignmentListView.addView(assignmentView);
        }
    }

    private AssignmentSchedulableCardView createCard(Assignment a) {
        AssignmentSchedulableCardView newCard = new AssignmentSchedulableCardView(requireContext());

        TimeSlot time = null;

        newCard.setTitle(a.name);
        newCard.setNotifyBefore(String.valueOf(a.notifyBefore));
        newCard.setDueDate(a.getDueDate().toString());

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
