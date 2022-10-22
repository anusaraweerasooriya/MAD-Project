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
import com.example.empressnotes.adapters.NotesAdapter;
import com.example.empressnotes.adapters.ToDoAdapter;

import java.util.ArrayList;

public class MyNotes extends AppCompatActivity {
    RecyclerView notes_recyclerView;
    ImageView add_notes_button;

    DatabaseHelper myDB;
    ArrayList<String> note_id, note_title, note_body;
    NotesAdapter notesAdapter;

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

        myDB = new DatabaseHelper(MyNotes.this);
        note_id = new ArrayList<>();
        note_title = new ArrayList<>();
        note_body = new ArrayList<>();

        storeNotesInArrays();

        notesAdapter = new NotesAdapter(MyNotes.this, note_id, note_title, note_body);
        notes_recyclerView.setAdapter(notesAdapter);
        notes_recyclerView.setLayoutManager(new LinearLayoutManager(MyNotes.this));
    }

    void storeNotesInArrays() {
        Cursor cursor = myDB.readAllNotes();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()) {
                note_id.add(cursor.getString(0));
                note_title.add(cursor.getString(1));
                note_body.add(cursor.getString(2));
            }
        }
    }
}
