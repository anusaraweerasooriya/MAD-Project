package com.example.empressnotes.adapters;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "EmpressNotes";
    private static final int DATABASE_VERSION = 1;

    // DatabaseHelper constructor
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }



    // Tables
    private static final String TABLE_NAME1 = "my_diary";
    private static final String TABLE_NAME2 = "my_todo";


    // my_diary table columns
    private static final String COLUMN_DIARY_ID = "id";
    private static final String COLUMN_DIARY_TITLE = "title";
    private static final String COLUMN_DIARY_DATETIME = "dateTime";
    private static final String COLUMN_DIARY_BODY = "diaryBody";
    private static final String COLUMN_DIARY_IMAGE_PATH = "imagePath";

    // my_todo table columns

    // my_notes table columns

    // my_lists table columns



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // My Diary-------------------------------------
        String my_diary_query = " CREATE TABLE " + TABLE_NAME1 +
                "(" + COLUMN_DIARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DIARY_TITLE + " TEXT, " +
                COLUMN_DIARY_DATETIME + " TEXT, " +
                COLUMN_DIARY_BODY + " TEXT, " +
                COLUMN_DIARY_IMAGE_PATH + " TEXT);";

        sqLiteDatabase.execSQL(my_diary_query);

        // My To-Do--------------------------------------

        // My Notes--------------------------------------

        // My Lists--------------------------------------

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        // My Diary
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(sqLiteDatabase);

        // My To-Do

        // My Notes

        // My Lists

    }
}
