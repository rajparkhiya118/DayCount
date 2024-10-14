package com.apidemo.getapi;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button btnSelectStartDate, btnSelectEndDate, btnCalculateDays;
    private TextView tvResult;
    private Spinner spinnerDays;
    private Calendar startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSelectStartDate = findViewById(R.id.btnSelectStartDate);
        btnSelectEndDate = findViewById(R.id.btnSelectEndDate);
        btnCalculateDays = findViewById(R.id.btnCalculateDays);
        tvResult = findViewById(R.id.tvResult);
        spinnerDays = findViewById(R.id.spinnerDays);

        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();

        btnSelectStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(startDate, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startDate.set(year, month, dayOfMonth);
                        btnSelectStartDate.setText(formatDate(startDate));
                    }
                });
            }
        });

        btnSelectEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(endDate, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endDate.set(year, month, dayOfMonth);
                        btnSelectEndDate.setText(formatDate(endDate));
                    }
                });
            }
        });

        btnCalculateDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedDayOfWeek = spinnerDays.getSelectedItemPosition() + 1; // Sunday = 1, Monday = 2, etc.
                int occurrences = calculateOccurrencesOfDay(selectedDayOfWeek, startDate, endDate);
                tvResult.setText("Total occurrences of selected day: " + occurrences);
            }
        });
    }

    private void showDatePickerDialog(Calendar calendar, DatePickerDialog.OnDateSetListener listener) {
        new DatePickerDialog(
                MainActivity.this,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private String formatDate(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH) + "-" +
                (calendar.get(Calendar.MONTH) + 1) + "-" +
                calendar.get(Calendar.YEAR);
    }

    private int calculateOccurrencesOfDay(int dayOfWeek, Calendar start, Calendar end) {
        int occurrences = 0;
        Calendar temp = (Calendar) start.clone();
        while (!temp.after(end)) {
            if (temp.get(Calendar.DAY_OF_WEEK) == dayOfWeek) {
                occurrences++;
            }
            temp.add(Calendar.DAY_OF_YEAR, 1);
        }

        return occurrences;
    }
}
