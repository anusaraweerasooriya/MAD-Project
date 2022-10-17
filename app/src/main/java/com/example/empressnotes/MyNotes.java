package com.example.empressnotes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class MyNotes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        //set back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set action bar title
        getSupportActionBar().setTitle("Empress Notes");

        //set action bar Color
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF3700B3"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
    }
}
