package com.example.empressnotes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.empressnotes.R;
import com.example.empressnotes.adapters.DatabaseHelper;
import com.example.empressnotes.adapters.ToDoAdapter;

import java.util.ArrayList;

public class ToDoMain extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView add_button;

    DatabaseHelper myDB;
    ArrayList<String> task_id, task_title, task_description, task_date, task_time, task_weblink, task_status;
    ToDoAdapter toDoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_main);

        recyclerView = findViewById(R.id.tasksRecyclerView);
        add_button = findViewById(R.id.imageAddTaskMain);

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
        task_weblink = new ArrayList<>();
        task_status = new ArrayList<>();

        storeTaskDataInArrays();

        toDoAdapter = new ToDoAdapter(ToDoMain.this, task_id, task_title, task_description, task_date, task_time, task_weblink, task_status);
        recyclerView.setAdapter(toDoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ToDoMain.this));

    }

    void storeTaskDataInArrays() {
        Cursor cursor = myDB.readTaskData();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()) {
                task_id.add(cursor.getString(0));
                task_title.add(cursor.getString(1));
                task_description.add(cursor.getString(2));
                task_date.add(cursor.getString(3));
                task_time.add(cursor.getString(4));
                task_weblink.add(cursor.getString(5));
                task_status.add(cursor.getString(6));
            }
        }
    }


}

