package com.example.empressnotes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.empressnotes.R;
import com.example.empressnotes.adapters.DatabaseHelper;

public class ToDoUpdateTask extends AppCompatActivity {

    EditText title_input, description_input, date_input, time_input;
    ImageView update_btn;

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

    /*void confirmDeleteDialog() {
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
    }*/

    void confirmDeleteDialog() {
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
                DatabaseHelper myDelete = new DatabaseHelper(ToDoUpdateTask.this);
                myDelete.deleteTask(id);
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