package com.example.empressnotes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.empressnotes.R;
import com.example.empressnotes.adapters.DatabaseHelper;

public class ToDoUpdateTask extends AppCompatActivity {

    EditText title_input, description_input, date_input, time_input;
    Button update_btn;

    String id, title, description, date, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_update_task);

        title_input = findViewById(R.id.editTextTaskTitleUpdate);
        description_input = findViewById(R.id.editTextTaskDescriptionUpdate);
        date_input = findViewById(R.id.editTextTaskDateUpdate);
        time_input = findViewById(R.id.editTextTaskTimeUpdate);
        update_btn = findViewById(R.id.buttonUpdateTask);

        //first we call intent data
        getAndSetTaskIntentData();

        // set the actionbar title to note title when in update page
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //then only we call update method
                DatabaseHelper myDB = new DatabaseHelper(ToDoUpdateTask.this);
                title = title_input.getText().toString().trim();
                description = description_input.getText().toString().trim();
                date = date_input.getText().toString().trim();
                time = time_input.getText().toString().trim();
                myDB.updateTaskData(id, title, description, date, time);

                Intent intent = new Intent(ToDoUpdateTask.this, ToDoMain.class);
                startActivity(intent);
            }
        });

    }

    // Set data in intent to be displayed in update fields
    void getAndSetTaskIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("description") &&
                getIntent().hasExtra("date") && getIntent().hasExtra("time")) {

            //Getting data from intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            description = getIntent().getStringExtra("description");
            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");

            //Setting intent data
            title_input.setText(title);
            description_input.setText(description);
            date_input.setText(date);
            time_input.setText(time);

        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    // Delete task option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.todo_delete_task_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_todo_task) {
            confirmDeleteDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Task");
        builder.setMessage("Are you sure you want to delete task " + title + " ?");

        // If user confirm delete action
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper taskDB = new DatabaseHelper(ToDoUpdateTask.this);
                taskDB.deleteTask(id);
                //return back to to-do home page
                Intent intent = new Intent(ToDoUpdateTask.this, ToDoMain.class);
                startActivity(intent);
            }
        });

        // If user cancel delete action
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

}