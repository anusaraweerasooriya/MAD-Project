package com.example.empressnotes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.empressnotes.R;
import com.example.empressnotes.adapters.DatabaseHelper;
import com.example.empressnotes.adapters.DiaryAdapter;

import java.util.ArrayList;


public class MyDiary extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView add_diary_button;
    TextView textDiaryID;
    ImageView empty_diaryImageView;
    TextView noDiaryData;

    DatabaseHelper diary_db;
    ArrayList<String> diary_id, diary_title, diary_date, diary_body, diary_image;
    DiaryAdapter diaryAdaptor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diary);

        recyclerView = findViewById(R.id.diaryRecyclerView);

        add_diary_button = findViewById(R.id.imageAddDiaryMain);
        empty_diaryImageView = findViewById(R.id.imageDiaryEmpty);
        noDiaryData = findViewById(R.id.diaryEmpty);

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
        diary_image = new ArrayList<>();

        storeDiaryDataInArrays();

        diaryAdaptor = new DiaryAdapter(MyDiary.this, this, diary_id, diary_title, diary_date, diary_body, diary_image);
        recyclerView.setAdapter(diaryAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyDiary.this));

    }

    void storeDiaryDataInArrays() {

        textDiaryID = findViewById(R.id.textDiaryID);

        Cursor cursor = diary_db.readDiaryData();
        if (cursor.getCount()==0) {

            empty_diaryImageView.setVisibility(View.VISIBLE);
            noDiaryData.setVisibility(View.VISIBLE);

        } else {
            while (cursor.moveToNext()) {
                diary_id.add(cursor.getString(0));
                diary_title.add(cursor.getString(1));
                diary_date.add(cursor.getString(2));
                diary_body.add(cursor.getString(3));
                diary_image.add(cursor.getString(4));
            }
            empty_diaryImageView.setVisibility(View.GONE);
            noDiaryData.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_delete_diary, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.delete_diary) {
            confirmDeleteDialog();
        }
        return super.onOptionsItemSelected(item);
    }

//    void confirmDeleteDialog() {
//        AlertDialog.Builder builder =new AlertDialog.Builder(this);
//        builder.setTitle("Delete all Diaries ?");
//        builder.setMessage("Are you sure you want to delete all diaries?");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                DatabaseHelper diaryDB = new DatabaseHelper(MyDiary.this);
//                diaryDB.deleteAllDiaries();
//
//                Intent intent = new Intent(MyDiary.this, MyDiary.class);
//                startActivity(intent);
//
//                finish();
//            }
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//        builder.create().show();
//    }

    void confirmDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyDiary.this);
        View view = LayoutInflater.from(this).inflate(
                R.layout.diary_delete_all_layout,
                (ViewGroup) findViewById(R.id.layoutDiaryDeleteAll)
        );
        builder.setView(view);
        AlertDialog deleteAllDialog = builder.create();
        if (deleteAllDialog.getWindow() != null) {
            deleteAllDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        // If user confirm delete action
        view.findViewById(R.id.textDeleteAllDiaryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper diaryDB = new DatabaseHelper(MyDiary.this);
                diaryDB.deleteAllDiaries();
                // Refresh and return back to to-do home page
                Intent intent = new Intent(MyDiary.this, MyDiary.class);
                startActivity(intent);
            }
        });
        // If user cancel delete action
        view.findViewById(R.id.textCancelDeleteAllDiaryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAllDialog.dismiss();
            }
        });
        deleteAllDialog.show();
    }
}
