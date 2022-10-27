package com.example.empressnotes.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.empressnotes.R;
import com.example.empressnotes.adapters.DatabaseHelper;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateADiary extends AppCompatActivity {

    private EditText inputDiaryTitle, inputDiaryBody;
    private TextView diaryDateTime, wordCount;
    private ImageView imageDiary, buttonDiarySave;

    private String selectedDiaryImagePath;
    private Uri selectedImageUri;

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_DIARY_IMAGE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_adiary);

        inputDiaryTitle = findViewById(R.id.inputDiaryTitle);
        inputDiaryBody = findViewById(R.id.inputDiaryBody);
        diaryDateTime = findViewById(R.id.diaryDateTime);
        imageDiary = findViewById(R.id.imageDiary);
        wordCount = findViewById(R.id.wordCount);
        buttonDiarySave = findViewById(R.id.diaryCreateSubmitButton);

        // Calling word count method
        getWordCount();

        diaryDateTime.setText(
                new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.getDefault())
                        .format(new Date())
        );

        selectedDiaryImagePath = "";

        buttonDiarySave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (inputDiaryTitle.getText().toString().trim().isEmpty()) {
                    Toast.makeText(CreateADiary.this, "Note title cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (inputDiaryBody.getText().toString().trim().isEmpty()) {
                    Toast.makeText(CreateADiary.this, "Note cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    DatabaseHelper myDB = new DatabaseHelper(CreateADiary.this);
                    myDB.addDiary(inputDiaryTitle.getText().toString().trim(),
                            diaryDateTime.getText().toString(),
                            inputDiaryBody.getText().toString().trim(),
                            selectedDiaryImagePath
                    );



                    Intent intent = new Intent(CreateADiary.this, MyDiary.class);
                    startActivity(intent);

                }
            }

        });

//        ImageView addImageDiary = findViewById(R.id.diaryAddImage);
//        addImageDiary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (ContextCompat.checkSelfPermission(
//                        getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
//                ) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(
//                            CreateADiary.this,
//                            new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
//                            REQUEST_CODE_STORAGE_PERMISSION
//                    );
//                } else {
//                    selectDiaryImage();
//                }
//            }
//        });

    }

    private void uploadDiaryImage(){
        buttonDiarySave = findViewById(R.id.diaryCreateSubmitButton);
        buttonDiarySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            CreateADiary.this,
                            new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION
                    );
                } else {
                    selectDiaryImage();
                }
            }
        });
    }

    private void selectDiaryImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_SELECT_DIARY_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectDiaryImage();
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
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
}