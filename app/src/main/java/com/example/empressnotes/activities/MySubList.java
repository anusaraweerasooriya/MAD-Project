package com.example.empressnotes.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.empressnotes.R;
import com.example.empressnotes.adapters.DatabaseHelper;
import com.example.empressnotes.adapters.SubListAdapter;

import java.util.ArrayList;

public class MySubList extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView add_button;
    ImageView list_empty;
    TextView no_data, total_count;
    String mID="";
    DatabaseHelper list_db;
    ArrayList<String> list_id, list_title, list_quantity;
    SubListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        mID=getIntent().getStringExtra("id");
        recyclerView = findViewById(R.id.notesRecyclerView);
        add_button = findViewById(R.id.imageAddList);
        list_empty=findViewById(R.id.list_empty);
        no_data=findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MySubList.this, SubListAdd.class);
                intent.putExtra("id",mID);
                startActivity(intent);
            }
        });

        list_db = new DatabaseHelper(MySubList.this);
        list_id = new ArrayList<>();
        list_title = new ArrayList<>();
        list_quantity = new ArrayList<>();

        storeListDataInArrays();

        listAdapter = new SubListAdapter(MySubList.this, list_id, list_title, list_quantity,mID);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MySubList.this));

    }

    void storeListDataInArrays() {
        Cursor cursor = list_db.readSubListData(mID);
        if (cursor.getCount()==0) {
            list_empty.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                list_id.add(cursor.getString(0));
                list_title.add(cursor.getString(2));
                list_quantity.add(cursor.getString(4));

            }

            list_empty.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_all){
            confirmDeleteDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    void confirmDeleteDialog() {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("Delete All");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper listDB = new DatabaseHelper(MySubList.this);
                DatabaseHelper list_db = new DatabaseHelper(MySubList.this);
                list_db.deleteAllLists();
                //refresh Activity
                Intent intent = new Intent(MySubList.this, MySubList.class);
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
