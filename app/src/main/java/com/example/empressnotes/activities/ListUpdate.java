package com.example.empressnotes.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.empressnotes.R;
import com.example.empressnotes.adapters.DatabaseHelper;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ListUpdate extends AppCompatActivity {

    EditText title_input,list_description;
    Button update_button,delete_button;

    String id,title,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_update);
        title_input = findViewById(R.id.title_input2);
        list_description = findViewById(R.id.list_description2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        //first we call this
        getAndSetIntentData();

        //set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }


        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //And only then we call this
                DatabaseHelper listDB =new DatabaseHelper(ListUpdate.this);
                title = title_input.getText().toString().trim();
                description = list_description.getText().toString().trim();

                listDB.updateList(id, title, description);

                Intent intent = new Intent(ListUpdate.this, MyList.class);
                startActivity(intent);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDeleteDialog();

            }
        });



    }
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id")&& getIntent().hasExtra("title") &&
                getIntent().hasExtra("description")){

            //Getting dat from intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            description = getIntent().getStringExtra("description");

            //Setting intent data
            title_input.setText(title);
            list_description.setText(description);


        }else {
            Toast.makeText(this,"No list data",Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDeleteDialog() {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("Delete" + title + "List ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper listDB = new DatabaseHelper(ListUpdate.this);
                listDB.deleteListRow(id);

                Intent intent = new Intent(ListUpdate.this, MyList.class);
                startActivity(intent);

                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }


}