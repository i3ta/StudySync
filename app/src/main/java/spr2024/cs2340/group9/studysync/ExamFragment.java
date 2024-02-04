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
import android.widget.TimePicker;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import spr2024.cs2340.group9.studysync.adapters.ExamAdapter;
import spr2024.cs2340.group9.studysync.database.Exam;
import spr2024.cs2340.group9.studysync.database.Exams;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class ExamFragment extends Fragment {

    private Button addButton;
    private ListView examListView;
    private Exams examsHelper;
    private ExamAdapter examAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exams_fragment, container, false);

        addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> showDateTimeInputDialog());

        // Initialization
        examListView = view.findViewById(R.id.listView);
        examAdapter = new ExamAdapter(requireContext(), new ArrayList<Exam>());
        examListView.setAdapter(examAdapter);
        examsHelper = new Exams();
        examsHelper.init(requireContext());

//        examsHelper.clear();

        // Load exams from the database
        updateExamListView();

        return view;
    }

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
            examsHelper.insert(newExam);

            // Update the ListView
            updateExamListView();

            dialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    private void updateExamListView() {
        // Load exams from the database using your Exams helper class
        Exam[] exams = examsHelper.getAll();

        // Update the adapter with the loaded exams
        examAdapter.clear();
        examAdapter.addAll(Arrays.asList(exams));
    }
}
