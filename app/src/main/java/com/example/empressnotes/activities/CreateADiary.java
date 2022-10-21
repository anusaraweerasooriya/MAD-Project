package com.example.empressnotes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.empressnotes.R;
import com.example.empressnotes.adapters.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateADiary extends AppCompatActivity {

    private EditText inputDiaryTitle, inputDiaryBody;
    private TextView diaryDateTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_adiary);

        inputDiaryTitle = findViewById(R.id.inputDiaryTitle);
        inputDiaryBody = findViewById(R.id.inputDiaryBody);
        diaryDateTime = findViewById(R.id.diaryDateTime);
        LottieAnimationView buttonDiarySave = findViewById(R.id.diaryCreateSubmitButton);

        diaryDateTime.setText(
                new SimpleDateFormat("EEEE dd MMMM yyyy HH:mm a", Locale.getDefault())
                        .format(new Date())
        );

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

                    buttonDiarySave.setSpeed(1);
                    buttonDiarySave.playAnimation();

                    DatabaseHelper myDB = new DatabaseHelper(CreateADiary.this);
                    myDB.addDiary(inputDiaryTitle.getText().toString().trim(),
                            diaryDateTime.getText().toString(),
                            inputDiaryBody.getText().toString().trim()
                    );



                    Intent intent = new Intent(CreateADiary.this, MyDiary.class);
                    startActivity(intent);

                }
            }

        });

    }
}