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

import java.util.ArrayList;

public class ToDoMain extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView add_button;

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

    }

}

