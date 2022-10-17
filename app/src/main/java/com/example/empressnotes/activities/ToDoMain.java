package com.example.empressnotes.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import com.example.empressnotes.R;

public class ToDoMain extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_TASK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_main);

        //set back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set action bar title
        getSupportActionBar().setTitle("Empress Notes");

        //set action bar Color
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#3FA796"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);


        //add new task btn
        ImageView imageAddNoteMain = findViewById(R.id.imageAddTaskMain);
        imageAddNoteMain.setOnClickListener(v -> startActivityForResult(
                new Intent(getApplicationContext(), CreateTaskActivity.class),
                REQUEST_CODE_ADD_TASK
        ));

    }
}

