package com.example.empressnotes.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.empressnotes.R;
import com.example.empressnotes.adapters.DatabaseHelper;
import com.example.empressnotes.adapters.NotesAdapter;
import com.example.empressnotes.adapters.ToDoAdapter;

import java.util.ArrayList;

public class MyNotes extends AppCompatActivity {
    RecyclerView notes_recyclerView;
    ImageView add_notes_button, empty_imageview;
    TextView no_data;

    DatabaseHelper myDB;
    ArrayList<String> note_id, note_title, note_body, note_datetime, note_color;
    NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        notes_recyclerView = findViewById(R.id.notesRecyclerView);
        add_notes_button = findViewById(R.id.imageAddNotesMain);
        empty_imageview = findViewById(R.id.imageEmptyNoteData);
        no_data = findViewById(R.id.lblNoData);

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
        note_datetime = new ArrayList<>();
        note_color = new ArrayList<>();

        storeNotesInArrays();

        notesAdapter = new NotesAdapter(MyNotes.this, MyNotes.this, note_id, note_title, note_body, note_datetime, note_color);
        notes_recyclerView.setAdapter(notesAdapter);
        notes_recyclerView.setLayoutManager(new LinearLayoutManager(MyNotes.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeNotesInArrays() {
        Cursor cursor = myDB.readAllNotes();
        if(cursor.getCount() == 0) {
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()) {
                note_id.add(cursor.getString(0));
                note_title.add(cursor.getString(1));
                note_body.add(cursor.getString(2));
                note_datetime.add(cursor.getString(3));
                note_color.add(cursor.getString(4));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notes_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all notes");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper myDB = new DatabaseHelper(MyNotes.this);
                myDB.deleteAllNotes();

                Intent intent = new Intent(MyNotes.this, MyNotes.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
