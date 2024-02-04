package spr2024.cs2340.group9.studysync;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import spr2024.cs2340.group9.studysync.adapters.ExamAdapter;
import spr2024.cs2340.group9.studysync.database.Exam;
import spr2024.cs2340.group9.studysync.database.Exams;

/**
 * Exam Fragment.
 */
public class ExamFragment extends Fragment {
    private ExamAdapter examAdapter;

    /**
     * Creates Exam view
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return View created
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exams_fragment, container, false);

        Button addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> showDateTimeInputDialog());

        // Initialization
        ListView examListView = view.findViewById(R.id.listView);
        examAdapter = new ExamAdapter(requireContext(), new ArrayList<>());
        examListView.setAdapter(examAdapter);
        Exams.init(requireContext());

        // Load exams from the database
        updateExamListView();

        // Long hold response
        examListView.setOnItemLongClickListener((parent, view1, position, id) -> {
            TextView myInvisibleView = view1.findViewById(R.id.hiddenId);
            String valueString = myInvisibleView.getText().toString();
            int examId = Integer.parseInt(valueString);
            Exam selectedExam = Exams.get(examId);

            // Build an alert with code
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme);
            builder.setTitle("Delete To Do List");
            builder.setMessage("Are You Sure?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                Exams.delete(selectedExam.getId());
                updateExamListView();
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {});
            androidx.appcompat.app.AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        });

        return view;
    }

    /**
     * Initializes dateTimeInputDialog.
     */
    private void showDateTimeInputDialog() {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_layout, null);

        // Find views in the custom layout
        DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        TimePicker timePicker = dialogView.findViewById(R.id.timePicker);
        EditText titleEditText = dialogView.findViewById(R.id.titleEditText);
        EditText locationEditText = dialogView.findViewById(R.id.locationEditText);
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
            String userInput = titleEditText.getText().toString();
            String userInput1 = locationEditText.getText().toString();

            // Construct Calendar object from selected date and time
            Calendar selectedDateTime = Calendar.getInstance();
            selectedDateTime.set(year, month, dayOfMonth, hourOfDay, minute);

            Exam newExam = new Exam(userInput, userInput1, selectedDateTime.getTimeInMillis(), 0);
            Exams.insert(newExam);

            // Update the ListView
            updateExamListView();

            dialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    /**
     * Updates exam list view with updated database.
     */
    private void updateExamListView() {
        // Load exams from the database using your Exams helper class
        Exam[] exams = Exams.getAll();

        // Update the adapter with the loaded exams
        examAdapter.clear();
        examAdapter.addAll(Arrays.asList(exams));
    }

    /**
     * Hides action bar
     */
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) requireActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((AppCompatActivity) requireActivity()).getSupportActionBar().show();
    }
}
