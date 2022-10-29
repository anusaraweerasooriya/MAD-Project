package com.example.empressnotes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.empressnotes.R;
import com.example.empressnotes.adapters.DatabaseHelper;

import java.util.Calendar;

public class ToDoCreateTask extends AppCompatActivity {

    EditText title_input, description_input, date_input, time_input;
    Button save_btn, url_btn;

    // Fetch URL display layout
    LinearLayout layout_url;
    TextView text_url;
    AlertDialog dialog_add_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_create_task);

        title_input = findViewById(R.id.editTextTaskTitle);
        description_input = findViewById(R.id.editTextTaskDescription);
        date_input = findViewById(R.id.editTextTaskDate);
        time_input = findViewById(R.id.editTextTaskTime);
        save_btn = findViewById(R.id.buttonSaveTask);
        url_btn = findViewById(R.id.buttonTaskURL);

        layout_url = findViewById(R.id.layoutTaskUrl);
        text_url = findViewById(R.id.textTaskUrl);

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
                }, hour, minutes, false);
                timePickerDialog.show();
            }
        });

        // OnClick function of Add URL button
        url_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTaskUrlDialog();
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
                            text_url.getText().toString().trim()
                    );

                    Intent intent = new Intent(ToDoCreateTask.this, ToDoMain.class);
                    startActivity(intent);
                }
            }
        });

    }



    // Show popup dialog box to ADD URL
    private void showAddTaskUrlDialog() {
        if(dialog_add_url == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ToDoCreateTask.this);
            View view = LayoutInflater.from(this).inflate(
                    R.layout.todo_add_url_layout,
                    (ViewGroup) findViewById(R.id.layoutAddTaskUrl)
            );
            builder.setView(view);

            dialog_add_url = builder.create();
            if(dialog_add_url.getWindow() != null) {
                dialog_add_url.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }

            final EditText inputUrl = view.findViewById(R.id.editTextTaskUrl);
            inputUrl.requestFocus();

            // Confirm add URL
            view.findViewById(R.id.textAddTaskUrlBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(inputUrl.getText().toString().trim().isEmpty()) {
                        // Notify user that URL is empty
                        Toast.makeText(ToDoCreateTask.this, "Enter URL", Toast.LENGTH_SHORT).show();
                    }else if(!Patterns.WEB_URL.matcher(inputUrl.getText().toString()).matches()) {
                        // Display error if user enter invalid URL
                        Toast.makeText(ToDoCreateTask.this, "Please Enter Valid URL", Toast.LENGTH_SHORT).show();
                    }else {
                        // Display the submitted valid url in the text view
                        text_url.setText(inputUrl.getText().toString());
                        layout_url.setVisibility(View.VISIBLE);
                        dialog_add_url.dismiss();
                    }
                }
            });

            // Cancel add URL
            view.findViewById(R.id.textCancelTaskUrlBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_add_url.dismiss();
                }
            });
        }
        dialog_add_url.show();
    }


}
