package com.example.empressnotes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

public class UpdateNote extends AppCompatActivity {
    EditText noteTitle_input, note_input;
    ImageView noteEdit_button;
    TextView noteDateTime;

    String id, title, note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        noteTitle_input = findViewById(R.id.inputNoteTitleEdit);
        note_input = findViewById(R.id.inputNoteEdit);
        noteEdit_button = findViewById(R.id.btnNoteEdit);
        noteDateTime = findViewById(R.id.lbl_editdatetime);

        noteDateTime.setText(
                new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.getDefault())
                        .format(new Date())
        );

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle(title);
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_note){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateNote.this);
        View view = LayoutInflater.from(this).inflate(
                R.layout.note_delete_note_layout,
                (ViewGroup) findViewById(R.id.layoutNoteDelete)
        );
        builder.setView(view);
        AlertDialog deleteNoteDialog = builder.create();
        if (deleteNoteDialog.getWindow() != null) {
            deleteNoteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        // If user confirm delete action
        view.findViewById(R.id.textDeleteNoteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper diaryDB = new DatabaseHelper(UpdateNote.this);
                diaryDB.deleteNote(id);
                // Refresh and return back to to-do home page
                Intent intent = new Intent(UpdateNote.this, MyNotes.class);
                startActivity(intent);
            }
        });
        // If user cancel delete action
        view.findViewById(R.id.textCancelDeleteNoteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNoteDialog.dismiss();
            }
        });
        deleteNoteDialog.show();
    }


}