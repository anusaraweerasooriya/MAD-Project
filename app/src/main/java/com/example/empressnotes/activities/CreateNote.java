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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.empressnotes.R;
import com.example.empressnotes.adapters.DatabaseHelper;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNote extends AppCompatActivity {

    EditText noteTitle_input, note_input;
    ImageView noteAdd_button;
    TextView noteDateTime, wordCount;

    private String selectedNoteColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        noteTitle_input = findViewById(R.id.inputNoteTitle);
        note_input = findViewById(R.id.inputNote);
        noteAdd_button = findViewById(R.id.btnNoteAdd);
        noteDateTime = findViewById(R.id.lbl_datetime);
        wordCount = findViewById(R.id.lbl_word_count);

        getWordCount();

        noteDateTime.setText(
                new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.getDefault())
                        .format(new Date())
        );

        initMiscellaneous();

        selectedNoteColor = "#FFFFFF";

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
                            noteDateTime.getText().toString().trim(),
                            selectedNoteColor);


                    Intent intent = new Intent(CreateNote.this, MyNotes.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initMiscellaneous(){
        final LinearLayout layoutMiscellaneous = findViewById(R.id.layoutMiscellaneous);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(layoutMiscellaneous);
        layoutMiscellaneous.findViewById(R.id.textMiscellaneous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        final ImageView imageColor1 = layoutMiscellaneous.findViewById(R.id.imageColor1);
        final ImageView imageColor2 = layoutMiscellaneous.findViewById(R.id.imageColor2);
        final ImageView imageColor3 = layoutMiscellaneous.findViewById(R.id.imageColor3);
        final ImageView imageColor4 = layoutMiscellaneous.findViewById(R.id.imageColor4);

        layoutMiscellaneous.findViewById(R.id.viewColor1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNoteColor = "#FFFFFF";
                imageColor1.setImageResource(R.drawable.ic_done);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
            }
        });

        layoutMiscellaneous.findViewById(R.id.viewColor2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNoteColor = "#FDF4A6";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(R.drawable.ic_done);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
            }
        });

        layoutMiscellaneous.findViewById(R.id.viewColor3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNoteColor = "#98A7F8";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(R.drawable.ic_done);
                imageColor4.setImageResource(0);
            }
        });

        layoutMiscellaneous.findViewById(R.id.viewColor4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNoteColor = "#FDA4A4";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(R.drawable.ic_done);
            }
        });
    }

    public void getWordCount() {

        wordCount = findViewById(R.id.lbl_word_count);
        note_input = findViewById(R.id.inputNote);

        note_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = note_input.getText().toString();
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