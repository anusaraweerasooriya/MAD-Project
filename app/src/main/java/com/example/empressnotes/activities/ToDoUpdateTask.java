package com.example.empressnotes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.empressnotes.R;
import com.example.empressnotes.adapters.DatabaseHelper;

import java.util.Calendar;

public class ToDoUpdateTask extends AppCompatActivity {

    EditText title_input, description_input, date_input, time_input;
    TextView url_input;
    ImageView url_btn, update_btn;

    // Fetch URL display layout
    TextView text_url;
    android.app.AlertDialog dialog_add_url;

    String id, title, description, date, time, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_update_task);

        title_input = findViewById(R.id.editTextTaskTitleUpdate);
        description_input = findViewById(R.id.editTextTaskDescriptionUpdate);
        date_input = findViewById(R.id.editTextTaskDateUpdate);
        time_input = findViewById(R.id.editTextTaskTimeUpdate);
        url_input = findViewById(R.id.editTextTaskUrlUpdate);
        update_btn = findViewById(R.id.buttonUpdateTask);

        url_btn = findViewById(R.id.imageTaskUrlEdit);
        text_url = findViewById(R.id.editTextTaskUrlUpdate);

        // call intent data
        getAndSetTaskIntentData();

        // set the actionbar title to note title when in update page
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ToDoUpdateTask.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = day + " / " + month + " / " + year;
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
                        ToDoUpdateTask.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                        String time = hour + ":" + minutes;
                        time_input.setText(time);
                    }
                }, hour, minutes, false);
                timePickerDialog.show();
            }
        });

        // OnClick function of Update URL button
        url_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTaskUrlDialog();
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //then only we call update method
                DatabaseHelper myDB = new DatabaseHelper(ToDoUpdateTask.this);
                title = title_input.getText().toString().trim();
                description = description_input.getText().toString().trim();
                date = date_input.getText().toString().trim();
                time = time_input.getText().toString().trim();
                url = url_input.getText().toString().trim();
                myDB.updateTaskData(id, title, description, date, time, url);

                Intent intent = new Intent(ToDoUpdateTask.this, ToDoMain.class);
                startActivity(intent);
            }
        });

    }

    // Set data in intent to be displayed in update fields
    void getAndSetTaskIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("description") &&
                getIntent().hasExtra("date") && getIntent().hasExtra("time") && getIntent().hasExtra("url")) {

            //Getting data from intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            description = getIntent().getStringExtra("description");
            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");
            url = getIntent().getStringExtra("url");

            //Setting intent data
            title_input.setText(title);
            description_input.setText(description);
            date_input.setText(date);
            time_input.setText(time);
            url_input.setText(url);

        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    // UPDATE URL OPTION
    private void showAddTaskUrlDialog() {
        if(dialog_add_url == null) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ToDoUpdateTask.this);
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
                        Toast.makeText(ToDoUpdateTask.this, "Enter URL", Toast.LENGTH_SHORT).show();
                    }else if(!Patterns.WEB_URL.matcher(inputUrl.getText().toString()).matches()) {
                        // Display error if user enter invalid URL
                        Toast.makeText(ToDoUpdateTask.this, "Please Enter Valid URL", Toast.LENGTH_SHORT).show();
                    }else {
                        // Display the submitted valid url in the text view
                        text_url.setText(inputUrl.getText().toString());
                        /*layout_url.setVisibility(View.VISIBLE);*/
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


    // Delete TASK OPTION
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.todo_delete_task_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_todo_task) {
            confirmDeleteDialog(id);
        }
        return super.onOptionsItemSelected(item);
    }

    public void confirmDeleteDialog(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ToDoUpdateTask.this);
        View view = LayoutInflater.from(this).inflate(
                R.layout.todo_delete_task_layout,
                (ViewGroup) findViewById(R.id.layoutTaskDelete)
        );
        builder.setView(view);
        AlertDialog deleteTaskDialog = builder.create();
        if (deleteTaskDialog.getWindow() != null) {
            deleteTaskDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        // If user confirm delete action
        view.findViewById(R.id.textDeleteTaskBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper taskDB = new DatabaseHelper(ToDoUpdateTask.this);
                taskDB.deleteTask(id);
                // Return back to to-do home page
                Intent intent = new Intent(ToDoUpdateTask.this, ToDoMain.class);
                startActivity(intent);
            }
        });

        // If user cancel delete action
        view.findViewById(R.id.textCancelDeleteTaskBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTaskDialog.dismiss();
            }
        });
        deleteTaskDialog.show();
    }




}