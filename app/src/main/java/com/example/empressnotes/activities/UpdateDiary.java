package com.example.empressnotes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.empressnotes.R;
import com.example.empressnotes.adapters.DatabaseHelper;

public class UpdateDiary extends AppCompatActivity {

    private EditText inputDiaryTitle, inputDiaryBody;
    private TextView diaryDateTime;
    private ImageView updateButton;
    String id, title, body, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_diary);

        inputDiaryTitle = findViewById(R.id.inputDiaryTitle2);
        inputDiaryBody = findViewById(R.id.inputDiaryBody2);
        diaryDateTime = findViewById(R.id.diaryDateTime2);
        updateButton = findViewById(R.id.diaryCreateSubmitButton2);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper diaryDB =new DatabaseHelper(UpdateDiary.this);
                title = inputDiaryTitle.getText().toString().trim();
                body = inputDiaryBody.getText().toString().trim();
                date = diaryDateTime.getText().toString();

                diaryDB.updateDiary(id, title, body, date);

                Intent intent = new Intent(UpdateDiary.this, MyDiary.class);
                startActivity(intent);
            }
        });

        getAndSetInsertDiaryData();


    }

    void getAndSetInsertDiaryData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("date") && getIntent().hasExtra("body")) {

            // Getting data from the intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            body = getIntent().getStringExtra("body");
            date = getIntent().getStringExtra("date");

            // Setting intent data
            inputDiaryTitle.setText(title);
            inputDiaryBody.setText(body);
            diaryDateTime.setText(date);

        } else {
            Toast.makeText(this, "No diary data", Toast.LENGTH_SHORT).show();
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
//        builder.setTitle("Delete" + title + "Diary ?");
//        builder.setMessage("Are you sure you want to delete " + title + " ?");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                DatabaseHelper diaryDB = new DatabaseHelper(UpdateDiary.this);
//                diaryDB.deleteDiaryRow(id);
//
//                Intent intent = new Intent(UpdateDiary.this, MyDiary.class);
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
 //   }

    void confirmDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDiary.this);
        View view = LayoutInflater.from(this).inflate(
                R.layout.diary_delete_diary_layout,
                (ViewGroup) findViewById(R.id.layoutDiaryDelete)
        );
        builder.setView(view);
        AlertDialog deleteDiaryDialog = builder.create();
        if (deleteDiaryDialog.getWindow() != null) {
            deleteDiaryDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        // If user confirm delete action
        view.findViewById(R.id.textDeleteDiaryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper diaryDB = new DatabaseHelper(UpdateDiary.this);
                diaryDB.deleteDiaryRow(id);
                // Refresh and return back to to-do home page
                Intent intent = new Intent(UpdateDiary.this, MyDiary.class);
                startActivity(intent);
            }
        });
        // If user cancel delete action
        view.findViewById(R.id.textCancelDeleteDiaryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDiaryDialog.dismiss();
            }
        });
        deleteDiaryDialog.show();
    }
}