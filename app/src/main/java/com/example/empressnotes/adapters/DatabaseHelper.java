package com.example.empressnotes.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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
    private static final String TABLE_NAME3 = "my_notes";
    private static final String TABLE_NAME4 = "my_list";

    // my_diary table columns
    private static final String COLUMN_DIARY_ID = "id";
    private static final String COLUMN_DIARY_TITLE = "title";
    private static final String COLUMN_DIARY_DATETIME = "dateTime";
    private static final String COLUMN_DIARY_BODY = "diaryBody";
    private static final String COLUMN_DIARY_IMAGE_PATH = "imagePath";

    // my_todo table columns
    private static final String COLUMN_TASK_ID = "id";
    private static final String COLUMN_TASK_TITLE = "title";
    private static final String COLUMN_TASK_DESCRIPTION = "description";
    private static final String COLUMN_TASK_DATE = "date";
    private static final String COLUMN_TASK_TIME = "time";
    private static final String COLUMN_TASK_WEBLINK = "weblink";
    private static final String COLUMN_TASK_STATUS = "status";

    // my_notes table columns
    private static final String COLUMN_NOTES_ID = "id";
    private static final String COLUMN_NOTES_TITLE = "noteTitle";
    private static final String COLUMN_NOTES_BODY = "note";
    private static final String COLUMN_NOTES_DATE = "date";
    private static final String COLUMN_NOTES_TIME = "time";
    private static final String COLUMN_NOTES_IMAGEPATH = "imagePath";
    private static final String COLUMN_NOTES_COLOR = "color";

    // my_lists table columns
    private static final String COLUMN_List_ID = "id";
    private static final String COLUMN_List_Name = "name";
    private static final String COLUMN_List_Quantity = "quantity";



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
        String todo_query = " CREATE TABLE " + TABLE_NAME2 +
                "(" + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TASK_TITLE + " TEXT, " +
                COLUMN_TASK_DESCRIPTION + " TEXT, " +
                COLUMN_TASK_DATE + " TEXT, " +
                COLUMN_TASK_TIME + " TEXT, " +
                COLUMN_TASK_WEBLINK + " TEXT, " +
                COLUMN_TASK_STATUS + " TEXT DEFAULT 'UPCOMING');";

        sqLiteDatabase.execSQL(todo_query);
      

        // My Notes--------------------------------------
        String notes_query = " CREATE TABLE " + TABLE_NAME3 +
                "(" + COLUMN_NOTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOTES_TITLE + " TEXT, " +
                COLUMN_NOTES_BODY + " TEXT);";

        sqLiteDatabase.execSQL(notes_query);


        // My Lists--------------------------------------
        String my_list_query = " CREATE TABLE " + TABLE_NAME4 +
                "(" + COLUMN_List_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_List_Name + " TEXT, " +
                COLUMN_List_Quantity + " INTEGER);" ;

        sqLiteDatabase.execSQL(my_list_query);

    }

  
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        // My Diary
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(sqLiteDatabase);

        // My To-Do
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(sqLiteDatabase);

        // My Notes
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME3);
        onCreate(sqLiteDatabase);

        // My Lists
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME4);
        onCreate(sqLiteDatabase);

    }

    //=========================================================================== MY DIARY ====================================================================
    //Add a new diary----------------------------------------------
    public void addDiary(String diaryTitle, String diaryDateTime, String diaryBody){

        SQLiteDatabase dbRef = this.getWritableDatabase();
        ContentValues cv= new ContentValues();

        cv.put(COLUMN_DIARY_TITLE, diaryTitle);
        cv.put(COLUMN_DIARY_BODY, diaryBody);
        cv.put(COLUMN_DIARY_DATETIME, diaryDateTime);

        long result = dbRef.insert(TABLE_NAME1, null, cv);
        if (result==-1){
            Toast.makeText(context, "Failed To insert Diary", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Diary Added Successfully", Toast.LENGTH_SHORT).show();
        }

    }

    // Read all the diaries----------------------------------------
    public Cursor readDiaryData() {
        String diary_query = "SELECT * FROM " + TABLE_NAME1;
        SQLiteDatabase dbRef = this.getReadableDatabase();

        Cursor cursor = null;
        if (dbRef != null) {
            cursor = dbRef.rawQuery(diary_query, null);
        }
        return cursor;
    }



    //=============================================MY TO-DO======================================================

    //CREATE
    public void createToDoTask(String title, String description, String date, String time, String weblink) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TASK_TITLE, title);
        cv.put(COLUMN_TASK_DESCRIPTION, description);
        cv.put(COLUMN_TASK_DATE, date);
        cv.put(COLUMN_TASK_TIME, time);
        cv.put(COLUMN_TASK_WEBLINK, weblink);

        long result = db.insert(TABLE_NAME2, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Failed to create the task", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Task is created successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    //READ
    public Cursor readTaskData() {
        String query = "SELECT * FROM " + TABLE_NAME2;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    
    
     //=============================================MY NOTES======================================================

    //Add Note
     public void addNote(String title, String note) {
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues cv = new ContentValues();

         cv.put(COLUMN_NOTES_TITLE, title);
         cv.put(COLUMN_NOTES_BODY, note);


         long result = db.insert(TABLE_NAME3, null, cv);
         if(result == -1) {
             Toast.makeText(context, "Failed to add note", Toast.LENGTH_SHORT).show();
         }else {
             Toast.makeText(context, "Note added successfully!", Toast.LENGTH_SHORT).show();
         }
     }

    public Cursor readAllNotes() {
        String query = "SELECT * FROM " + TABLE_NAME3;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void editNote(String row_id, String title, String note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NOTES_TITLE , title);
        cv.put(COLUMN_NOTES_BODY, note);

        long result = db.update(TABLE_NAME3, cv, "id=?", new String[] {row_id});
        if(result == -1) {
            Toast.makeText(context, "Failed to edit note", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Note edited successfully!", Toast.LENGTH_SHORT).show();
        }
    }

     
      //=============================================MY LISTS======================================================
      
      
      
      


}
