package com.example.empressnotes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.empressnotes.R;
import com.example.empressnotes.adapters.DatabaseHelper;

public class UpdateNote extends AppCompatActivity {
    EditText noteTitle_input, note_input;
    Button noteEdit_button;

    String id, title, note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        noteTitle_input = findViewById(R.id.inputNoteTitleEdit);
        note_input = findViewById(R.id.inputNoteEdit);
        noteEdit_button = findViewById(R.id.btnNoteEdit);

        getAndSetIntentData();

        noteEdit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDB = new DatabaseHelper(UpdateNote.this);
                title = noteTitle_input.getText().toString().trim();
                note = note_input.getText().toString().trim();
                myDB.editNote(id, title, note);

                Intent intent = new Intent(UpdateNote.this, MyNotes.class);
                startActivity(intent);
            }
        });
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("note")){
            //getting data from intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            note = getIntent().getStringExtra("note");

            //setting intent data
            noteTitle_input.setText(title);
            note_input.setText(note);
        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show();
        }
    }
}