package com.example.empressnotes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.empressnotes.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyList extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        //set back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set action bar title
        getSupportActionBar().setTitle("Empress Notes");

        //set action bar Color
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF3700B3"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);


        recyclerView = findViewById(R.id.notesRecyclerView);
        add_button = findViewById(R.id.imageAddList);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyList.this, ListAdd.class);
                startActivity(intent);
            }
        });

    }

}
