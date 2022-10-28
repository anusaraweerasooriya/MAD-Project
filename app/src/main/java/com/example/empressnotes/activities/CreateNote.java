package com.example.empressnotes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.empressnotes.R;
import com.example.empressnotes.adapters.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNote extends AppCompatActivity {

    EditText noteTitle_input, note_input;
    ImageView noteAdd_button;
    TextView noteDateTime, wordCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        noteTitle_input = findViewById(R.id.inputNoteTitle);
        note_input = findViewById(R.id.inputNote);
        noteAdd_button = findViewById(R.id.btnNoteAdd);
        noteDateTime = findViewById(R.id.lbl_datetime);
        wordCount = findViewById(R.id.lbl_word_count);

        noteDateTime.setText(
                new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.getDefault())
                        .format(new Date())
        );

        noteAdd_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noteTitle_input.getText().toString().trim().isEmpty()) {
                    Toast.makeText(CreateNote.this, "Title can not be empty!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    DatabaseHelper myDB = new DatabaseHelper(CreateNote.this);
                    myDB.addNote(noteTitle_input.getText().toString().trim(),
                            note_input.getText().toString().trim(),
                            noteDateTime.getText().toString().trim());

                    Intent intent = new Intent(CreateNote.this, MyNotes.class);
                    startActivity(intent);
                }
            }
        });
    }
}