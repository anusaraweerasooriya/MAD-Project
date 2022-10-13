package com.example.empressnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        //set back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set action bar title
        getSupportActionBar().setTitle("My To-Do");

        //set action bar Color
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF3700B3"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

    }
}