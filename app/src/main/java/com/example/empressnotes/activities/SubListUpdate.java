package com.example.empressnotes.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.empressnotes.R;
import com.example.empressnotes.adapters.DatabaseHelper;

public class SubListUpdate extends AppCompatActivity {

    EditText title_input, list_quantity;
    Button update_button,delete_button;

    String id,title, quantity,mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_list_update);
        title_input = findViewById(R.id.title_input2);
        list_quantity = findViewById(R.id.list_description2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);


        mid=getIntent().getStringExtra("mid");
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
                DatabaseHelper listDB =new DatabaseHelper(SubListUpdate.this);
                title = title_input.getText().toString().trim();
                quantity = list_quantity.getText().toString().trim();

                listDB.updateSubList(id, title, quantity);

                onBackPressed();
                onBackPressed();
                Intent intent = new Intent(SubListUpdate.this, MySubList.class);
                intent.putExtra("id",mid);
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
                getIntent().hasExtra("quantity")){

            //Getting dat from intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            quantity = getIntent().getStringExtra("quantity");

            //Setting intent data
            title_input.setText(title);
            list_quantity.setText(quantity);


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
                DatabaseHelper listDB = new DatabaseHelper(SubListUpdate.this);
                listDB.deleteSubListRow(id);

                onBackPressed();
                onBackPressed();
                Intent intent = new Intent(SubListUpdate.this, MySubList.class);
                intent.putExtra("id",mid);
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