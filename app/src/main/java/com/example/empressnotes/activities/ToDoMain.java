package com.example.empressnotes.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import com.example.empressnotes.R;

public class ToDoMain extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_TASK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_main);

        //add new task btn
        ImageView imageAddNoteMain = findViewById(R.id.imageAddTaskMain);
        imageAddNoteMain.setOnClickListener(v -> startActivityForResult(
                new Intent(getApplicationContext(), ToDoCreateTask.class),
                REQUEST_CODE_ADD_TASK
        ));

    }
}

