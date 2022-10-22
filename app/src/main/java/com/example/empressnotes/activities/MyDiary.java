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
import com.example.empressnotes.adapters.DiaryAdapter;

import java.util.ArrayList;


public class MyDiary extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView add_diary_button;

    DatabaseHelper diary_db;
    ArrayList<String> diary_id, diary_title, diary_date, diary_body;
    DiaryAdapter diaryAdaptor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diary);

        recyclerView = findViewById(R.id.diaryRecyclerView);
        add_diary_button = findViewById(R.id.imageAddNotesMain);

        add_diary_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MyDiary.this, CreateADiary.class);
               startActivity(intent);
            }
        });

        diary_db = new DatabaseHelper(MyDiary.this);
        diary_id = new ArrayList<>();
        diary_title = new ArrayList<>();
        diary_body = new ArrayList<>();
        diary_date = new ArrayList<>();

        storeDiaryDataInArrays();

        diaryAdaptor = new DiaryAdapter(MyDiary.this, diary_id, diary_title, diary_date, diary_body);
        recyclerView.setAdapter(diaryAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyDiary.this));

    }

    void storeDiaryDataInArrays() {
        Cursor cursor = diary_db.readDiaryData();
        if (cursor.getCount()==0) {
            Toast.makeText(this, "No Diaries Available ", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                diary_id.add(cursor.getString(0));
                diary_title.add(cursor.getString(1));
                diary_date.add(cursor.getString(2));
                diary_body.add(cursor.getString(3));
            }
        }
    }
}
