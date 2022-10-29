package com.example.empressnotes.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.io.InputStream;

public class UpdateDiary extends AppCompatActivity {

    private EditText inputDiaryTitle, inputDiaryBody;
    private TextView diaryDateTime, wordCount;
    private ImageView imageDiary, updateButton, buttonImageSave;
    String id, title, body, date, image;

    private String selectedDiaryImagePath;
    private Uri selectedImageUri, imageFilePath;
    private Bitmap imageToStore;
    private TextView diaryDate, diaryDay, diaryMonth;

    private static final int REQUEST_CODE_SELECT_DIARY_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_diary);

        inputDiaryTitle = findViewById(R.id.inputDiaryTitle2);
        inputDiaryBody = findViewById(R.id.inputDiaryBody2);
        diaryDateTime = findViewById(R.id.diaryDateTime2);
        updateButton = findViewById(R.id.diaryCreateSubmitButton2);
        buttonImageSave = findViewById(R.id.diaryAddImage2);
        imageDiary = findViewById(R.id.imageDiary2);
        diaryDate = findViewById(R.id.diaryDate2);
        diaryDay = findViewById(R.id.diaryDay2);
        diaryMonth = findViewById(R.id.diaryMonth2);

        uploadDiaryImage();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper diaryDB =new DatabaseHelper(UpdateDiary.this);
                title = inputDiaryTitle.getText().toString().trim();
                body = inputDiaryBody.getText().toString().trim();
                date = diaryDateTime.getText().toString();
                image = selectedDiaryImagePath;

                diaryDB.updateDiary(id, title, body, date, image);

                Intent intent = new Intent(UpdateDiary.this, MyDiary.class);
                startActivity(intent);
            }
        });

        getAndSetInsertDiaryData();


    }

    public void uploadDiaryImage(){
        buttonImageSave = findViewById(R.id.diaryAddImage2);
        buttonImageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_SELECT_DIARY_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_DIARY_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageDiary.setImageBitmap(bitmap);
                        imageDiary.setVisibility(View.VISIBLE);

                        selectedDiaryImagePath = getPathFromUri(selectedImageUri);

                    } catch (Exception e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private String getPathFromUri(Uri contentUri) {
        String filePath;
        Cursor cursor = getContentResolver().query(contentUri, null,null,null,null);

        if (cursor == null) {
            filePath = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }


    //Calculating word count method implementation
    public void getWordCount() {

        wordCount = findViewById(R.id.wordCount);
        inputDiaryBody = findViewById(R.id.inputDiaryBody);

        inputDiaryBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = inputDiaryBody.getText().toString();
                text = text.replace("\n", " ");
                String[] textArray = text.split(" ");
                wordCount.setText(""+textArray.length);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    void getAndSetInsertDiaryData() {
        diaryDate = findViewById(R.id.diaryDate2);
        diaryDay = findViewById(R.id.diaryDay2);
        diaryMonth = findViewById(R.id.diaryMonth2);

        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("date") && getIntent().hasExtra("body") && getIntent().hasExtra("image")) {

            // Getting data from the intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            body = getIntent().getStringExtra("body");
            date = getIntent().getStringExtra("date");
            image = getIntent().getStringExtra("image");

            // Setting intent data
            inputDiaryTitle.setText(title);
            inputDiaryBody.setText(body);
            diaryDateTime.setText(date);

            try {
                String date = String.valueOf(diaryDateTime.getText());

                String[] s = date.split(" ");
                String day = s[0];
                String dayI = s[1];
                String month = s[2];

                diaryDate.setText(dayI);
                diaryDay.setText(day);
                diaryMonth.setText(month);


            } catch (Exception e) {
                e.printStackTrace();
            }


            if (image != null ){
                imageDiary = findViewById(R.id.imageDiary2);
                imageDiary.setImageBitmap(BitmapFactory.decodeFile(image));
                imageDiary.setVisibility(View.VISIBLE);
                selectedDiaryImagePath = image;
            } else {
                imageDiary.setVisibility(View.GONE);
            }

            diaryDateTime.setVisibility(View.GONE);

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