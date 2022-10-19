package com.example.empressnotes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.empressnotes.R;

import java.util.Calendar;

public class ToDoCreateTask extends AppCompatActivity {

    EditText title_input, description_input, date_input, time_input;
    Button weblink_btn, save_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_create_task);

        title_input = findViewById(R.id.editTextTitle);
        description_input = findViewById(R.id.editTextDescription);
        date_input = findViewById(R.id.editTextDate);
        time_input = findViewById(R.id.editTextTime);
        weblink_btn = findViewById(R.id.buttonURL);
        save_btn = findViewById(R.id.buttonSave);


        date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ToDoCreateTask.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = day + "/" + month + "/" + year;
                        date_input.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis()-1000);
                datePickerDialog.show();
            }
        });

        time_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                final int minutes = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        ToDoCreateTask.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                        String time = hour + ":" + minutes;
                        time_input.setText(time);
                    }
                }, hour, minutes, true);
                timePickerDialog.show();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title_input.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ToDoCreateTask.this, "Task title can not be empty!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (description_input.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ToDoCreateTask.this, "Task description can not be empty!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (date_input.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ToDoCreateTask.this, "Task date can not be empty", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    DatabaseHelper myDB = new DatabaseHelper(ToDoCreateTask.this);
                    myDB.createToDoTask(title_input.getText().toString().trim(),
                            description_input.getText().toString().trim(),
                            date_input.getText().toString().trim(),
                            time_input.getText().toString().trim(),
                            weblink_btn.getText().toString().trim()
                    );
                }
            }
        });

    }


}
