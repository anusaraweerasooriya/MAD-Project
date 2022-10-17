package com.example.empressnotes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import com.example.empressnotes.R;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout layoutDiaryMain, layoutToDoMain, layoutNotesMain, layoutListsMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hide action bar
        getSupportActionBar().hide();


        //onclick My Diary tab
        layoutDiaryMain = findViewById(R.id.layoutDiaryMain);
        layoutDiaryMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(MainActivity.this, MyDiary.class)
                );
            }
        });


        //onclick My To-Do tab
        layoutToDoMain = findViewById(R.id.layoutToDoMain);
        layoutToDoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(MainActivity.this, ToDoMain.class)
                );
            }
        });
        
        //onclick My Notes tab
        layoutNotesMain = findViewById(R.id.layoutNotesMain);
        layoutNotesMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(MainActivity.this, MyNotes.class)
                );
            }
        });

        //onclick My Lists tab
        layoutListsMain = findViewById(R.id.layoutListsMain);
        layoutListsMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(MainActivity.this, MyList.class)
                );
            }
        });
    }

}

