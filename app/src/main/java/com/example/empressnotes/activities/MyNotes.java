package com.example.empressnotes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.empressnotes.R;

public class MyNotes extends AppCompatActivity {
    RecyclerView notes_recyclerView;
    ImageView add_notes_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        notes_recyclerView = findViewById(R.id.notesRecyclerView);
        add_notes_button = findViewById(R.id.imageAddNotesMain);

        add_notes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyNotes.this, CreateNote.class);
                startActivity(intent);
            }
        });

    }
}
