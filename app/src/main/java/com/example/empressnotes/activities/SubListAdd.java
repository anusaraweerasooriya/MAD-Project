package com.example.empressnotes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.empressnotes.R;
import com.example.empressnotes.adapters.DatabaseHelper;

public class SubListAdd extends AppCompatActivity {

    private EditText title_input, description_input;
    private Button add_Button;
    String mID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_list_add);
        mID = getIntent().getStringExtra("id");
        title_input = findViewById(R.id.title_input);
        description_input = findViewById(R.id.list_description);
        add_Button = findViewById(R.id.add_button);
        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDB = new DatabaseHelper(SubListAdd.this);
                try{
                    myDB.CreateSUBLIST();
                }catch (Exception e){}
                myDB.addSubList(title_input.getText().toString().trim(),
                        description_input.getText().toString().trim(), mID);

//                Intent intent = new Intent(SubListAdd.this, MyList.class);
//                startActivity(intent);
                onBackPressed();
                onBackPressed();
                Intent intent = new Intent(SubListAdd.this, MySubList.class);
                intent.putExtra("id",mID);
                startActivity(intent);
            }

        });


    }
}