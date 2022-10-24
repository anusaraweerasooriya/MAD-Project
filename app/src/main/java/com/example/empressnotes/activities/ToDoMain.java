package com.example.empressnotes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.empressnotes.R;
import com.example.empressnotes.adapters.DatabaseHelper;
import com.example.empressnotes.adapters.ToDoAdapter;

import java.util.ArrayList;

public class ToDoMain extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView add_button, empty_img;
    TextView empty_txt;

    DatabaseHelper myDB;
    ArrayList<String> task_id, task_title, task_description, task_date, task_time, task_url, task_status;
    ToDoAdapter toDoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_main);

        recyclerView = findViewById(R.id.tasksRecyclerView);
        add_button = findViewById(R.id.imageAddTaskMain);
        empty_img = findViewById(R.id.imageTaskEmpty);
        empty_txt = findViewById(R.id.textTaskEmpty);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToDoMain.this, ToDoCreateTask.class);
                startActivity(intent);
            }
        });

        myDB = new DatabaseHelper(ToDoMain.this);
        task_id = new ArrayList<>();
        task_title = new ArrayList<>();
        task_description = new ArrayList<>();
        task_date = new ArrayList<>();
        task_time = new ArrayList<>();
        task_url = new ArrayList<>();
        task_status = new ArrayList<>();

        storeTaskDataInArrays();

        toDoAdapter = new ToDoAdapter(ToDoMain.this, task_id, task_title, task_description, task_date, task_time, task_url, task_status);
        recyclerView.setAdapter(toDoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ToDoMain.this));

    }

    void storeTaskDataInArrays() {
        Cursor cursor = myDB.readTaskData();
        if(cursor.getCount() == 0) {
            empty_img.setVisibility(View.VISIBLE);
            empty_txt.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()) {
                task_id.add(cursor.getString(0));
                task_title.add(cursor.getString(1));
                task_description.add(cursor.getString(2));
                task_date.add(cursor.getString(3));
                task_time.add(cursor.getString(4));
                task_url.add(cursor.getString(5));
                task_status.add(cursor.getString(6));
            }
            empty_img.setVisibility(View.GONE);
            empty_txt.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.todo_delete_all_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all_todo) {
            confirmDeleteAllDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDeleteAllDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All Tasks");
        builder.setMessage("Are you sure you want to delete all ToDo tasks ?");

        // If user confirm delete action
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper taskDB = new DatabaseHelper(ToDoMain.this);
                taskDB.deleteAllTasks();
                //refresh and return back to to-do home page
                Intent intent = new Intent(ToDoMain.this, ToDoMain.class);
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

