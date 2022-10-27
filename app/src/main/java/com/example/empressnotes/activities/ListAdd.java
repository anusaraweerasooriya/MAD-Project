package com.example.empressnotes.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.empressnotes.R;
import com.example.empressnotes.adapters.DatabaseHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ListAdd extends AppCompatActivity {

    private EditText title_input,description_input;
    private Button add_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_add);

        title_input = findViewById(R.id.title_input);
        description_input = findViewById(R.id.list_description);
        add_Button = findViewById(R.id.add_button);
        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDB = new DatabaseHelper(ListAdd.this);
                myDB.addList(title_input.getText().toString().trim(),
                        description_input.getText().toString().trim());

                Intent intent = new Intent(ListAdd.this, MyList.class);
                startActivity(intent);
            }

        });


    }
}