package spr2024.cs2340.group9.studysync;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class SecondFragment extends Fragment {

    private Button addButton;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeInputDialog();
            }
        });

        return view;
    }

    private void showDateTimeInputDialog() {
        // Inflate the custom layout
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_layout, null);

        // Find views in the custom layout
        DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        TimePicker timePicker = dialogView.findViewById(R.id.timePicker);
        EditText titleEditText = dialogView.findViewById(R.id.titleEditText);
        Button okButton = dialogView.findViewById(R.id.okButton);

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
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));

        // Create the dialog
        final AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create();

        // Set up button click listener
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected date from DatePicker
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int dayOfMonth = datePicker.getDayOfMonth();

                // Get the selected time from TimePicker
                int hourOfDay = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                // Get the text from EditText
                String userInput = titleEditText.getText().toString();

                // Handle the selected date, time, and user input here
                // You may want to save this information or perform some other action

                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        // Show the dialog
        dialog.show();
    }
}
