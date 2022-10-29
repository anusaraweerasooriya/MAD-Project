package com.example.empressnotes.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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
    private static final String TABLE_NAME5 = "my_sub_list";

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
    private static final String COLUMN_TASK_URL = "url";
    private static final String COLUMN_TASK_STATUS = "status";

    // my_notes table columns
    private static final String COLUMN_NOTES_ID = "id";
    private static final String COLUMN_NOTES_TITLE = "noteTitle";
    private static final String COLUMN_NOTES_BODY = "note";
    private static final String COLUMN_NOTES_DATETIME = "datetime";
    private static final String COLUMN_NOTES_COLOR = "color";

    // my_lists table columns
    private static final String COLUMN_List_ID = "id";
    private static final String COLUMN_List_Title = "title";
    private static final String COLUMN_List_Description = "description";
    private static final String COLUMN_List_Quantity = "quantity";

    // my_sub_list table columns
    private static final String COLUMN_my_sub_list_ID = "id";
    private static final String COLUMN_my_sub_list_mainID = "m_id";
    private static final String COLUMN_my_sub_list_Title = "title";
    private static final String COLUMN_my_sub_list_Description = "description";
    private static final String COLUMN_my_sub_list_Quantity = "quantity";


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
                COLUMN_TASK_URL + " TEXT, " +
                COLUMN_TASK_STATUS + " TEXT DEFAULT 'UPCOMING');";

        sqLiteDatabase.execSQL(todo_query);


        // My Notes--------------------------------------
        String notes_query = " CREATE TABLE " + TABLE_NAME3 +
                "(" + COLUMN_NOTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOTES_TITLE + " TEXT, " +
                COLUMN_NOTES_BODY + " TEXT, " +
                COLUMN_NOTES_DATETIME + " TEXT, " +
                COLUMN_NOTES_COLOR + " TEXT);";

        sqLiteDatabase.execSQL(notes_query);


        // My Lists--------------------------------------
        String my_list_query = " CREATE TABLE " + TABLE_NAME4 +
                "(" + COLUMN_List_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_List_Title + " TEXT, " +
                COLUMN_List_Description + " TEXT, " +
                COLUMN_List_Quantity + " INTEGER);";

        sqLiteDatabase.execSQL(my_list_query);

        //Sub List-----------------------------------------------
        String my_sub_list_query = " CREATE TABLE " + TABLE_NAME5 +
                "(" + COLUMN_my_sub_list_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_my_sub_list_mainID + " TEXT, " +
                COLUMN_my_sub_list_Title + " TEXT, " +
                COLUMN_my_sub_list_Description + " TEXT, " +
                COLUMN_my_sub_list_Quantity + " INTEGER);";

        sqLiteDatabase.execSQL(my_sub_list_query);

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

        // My Sub Lists
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME5);
        onCreate(sqLiteDatabase);

    }

    //=========================================================================== MY DIARY ====================================================================
    //Add a new diary----------------------------------------------
    public void addDiary(String diaryTitle, String diaryDateTime, String diaryBody, String imagePath){

        SQLiteDatabase dbRef = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DIARY_TITLE, diaryTitle);
        cv.put(COLUMN_DIARY_BODY, diaryBody);
        cv.put(COLUMN_DIARY_DATETIME, diaryDateTime);
        cv.put(COLUMN_DIARY_IMAGE_PATH, imagePath);

        long result = dbRef.insert(TABLE_NAME1, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed To insert Diary", Toast.LENGTH_SHORT).show();
        } else {
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

    //Update diary----------------------------------------------
    public void updateDiary(String row_id, String title, String body, String date, String image) {

        SQLiteDatabase dbRef = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DIARY_TITLE, title);
        cv.put(COLUMN_DIARY_BODY, body);
        cv.put(COLUMN_DIARY_DATETIME, date);
        cv.put(COLUMN_DIARY_IMAGE_PATH, image);

        long result = dbRef.update(TABLE_NAME1, cv, "id=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Failed to update diary !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Diary updated successfully!", Toast.LENGTH_SHORT).show();

        }

    }

    //Delete a row of diary--------------------------------------
    public void deleteDiaryRow(String row_id) {

        SQLiteDatabase dbRef = this.getWritableDatabase();
        long result = dbRef.delete(TABLE_NAME1, "id=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Failed to delete diary!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted the diary!", Toast.LENGTH_SHORT).show();
        }

    }

    //Delete all diaries--------------------------------------
    public void deleteAllDiaries() {

        SQLiteDatabase dbRef = this.getWritableDatabase();
        dbRef.execSQL("DELETE FROM " + TABLE_NAME1);

    }






    //=============================================MY TO-DO======================================================

    // CREATE A TO-DO TASK
    public void createToDoTask(String title, String description, String date, String time, String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TASK_TITLE, title);
        cv.put(COLUMN_TASK_DESCRIPTION, description);
        cv.put(COLUMN_TASK_DATE, date);
        cv.put(COLUMN_TASK_TIME, time);
        cv.put(COLUMN_TASK_URL, url);

        long result = db.insert(TABLE_NAME2, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed to create the task", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Task is created successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    // READ ALL TO-DO TASKS
    public Cursor readTaskData() {
        String query = "SELECT * FROM " + TABLE_NAME2;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    
    // UPDATE A TO-DO TASK
    public void updateTaskData(String task_id, String title, String description, String date, String time, String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TASK_TITLE, title);
        cv.put(COLUMN_TASK_DESCRIPTION, description);
        cv.put(COLUMN_TASK_DATE, date);
        cv.put(COLUMN_TASK_TIME, time);
        cv.put(COLUMN_TASK_URL, url);

        long result = db.update(TABLE_NAME2, cv, "id=?", new String[] {task_id});
        if(result == -1) {
            Toast.makeText(context, "Failed to update task", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Task updated successfully", Toast.LENGTH_SHORT).show();
        }

    }

    // UPDATE A TASK AS COMPLETED
    public void taskCompleted(String task_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TASK_STATUS, "COMPLETED");

        long result = db.update(TABLE_NAME2, cv, "id=?", new String[] {task_id});
        if(result == -1) {
            Toast.makeText(context, "Failed to complete task", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Task completion successfully", Toast.LENGTH_SHORT).show();
        }

    }
    
    // DELETE ONE TO-DO TASK
    public void deleteTask(String task_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME2, "id=?", new String[] {task_id});
        if(result == -1) {
            Toast.makeText(context, "Failed to delete task", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Task deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }


    // DELETE ALL TO-DO TASKS
    public void deleteAllTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME2);
    }



    //=============================================MY NOTES======================================================

    //Add Note
     public void addNote(String title, String note, String datetime, String color) {
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues cv = new ContentValues();

         cv.put(COLUMN_NOTES_TITLE, title);
         cv.put(COLUMN_NOTES_BODY, note);
         cv.put(COLUMN_NOTES_DATETIME, datetime);
         cv.put(COLUMN_NOTES_COLOR, color);

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

    public void deleteNote(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME3, "id=?", new String[] {row_id});
        if(result == -1) {
            Toast.makeText(context, "Failed to delete note", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Note deleted successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllNotes(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME3);
    }


    //=============================================MY LISTS======================================================

    // Add a new list----------------------------------------------
    public void addList(String listTitle, String listDescription) {

        SQLiteDatabase dbRef = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_List_Title, listTitle);
        cv.put(COLUMN_List_Description, listDescription);

        long result = dbRef.insert(TABLE_NAME4, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed To insert List", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "List Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }


    public void addSubList(String listTitle, String quantity,String mID) {

        SQLiteDatabase dbRef = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_my_sub_list_Title, listTitle);
        cv.put(COLUMN_my_sub_list_Quantity, quantity);
        cv.put(COLUMN_my_sub_list_mainID, mID);

        long result = dbRef.insert(TABLE_NAME5, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed To insert List", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "List Added Successfully", Toast.LENGTH_SHORT).show();
        }

    }

    public void CreateSUBLIST(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String my_sub_list_query = " CREATE TABLE " + TABLE_NAME5 +
                "(" + COLUMN_my_sub_list_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_my_sub_list_mainID + " TEXT, " +
                COLUMN_my_sub_list_Title + " TEXT, " +
                COLUMN_my_sub_list_Description + " TEXT, " +
                COLUMN_my_sub_list_Quantity + " INTEGER);";

        sqLiteDatabase.execSQL(my_sub_list_query);

    }
            
    // Read all the lists----------------------------------------
    public Cursor readListData() {

        String list_query = "SELECT * FROM " + TABLE_NAME4;
        SQLiteDatabase dbRef = this.getReadableDatabase();

        Cursor cursor = null;
        if (dbRef != null) {
            cursor = dbRef.rawQuery(list_query, null);
        }
        return cursor;
    }

    public Cursor readSubListData(String id) {
        String list_query = "SELECT * FROM " + TABLE_NAME5+" WHERE m_id= "+id ;
        SQLiteDatabase dbRef = this.getReadableDatabase();

        Cursor cursor = null;
        if (dbRef != null) {
            cursor = dbRef.rawQuery(list_query, null);
        }
        return cursor;
    }


    //Update list----------------------------------------------
    public void updateList(String row_id, String title, String description) {

        SQLiteDatabase dbRef = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_List_Title, title);
        cv.put(COLUMN_List_Description, description);

        long result = dbRef.update(TABLE_NAME4, cv, "id=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Failed to update list !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "List updated successfully!", Toast.LENGTH_SHORT).show();

        }
    }

    public void updateSubList(String row_id, String title, String description) {

        SQLiteDatabase dbRef = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_List_Title, title);
        cv.put(COLUMN_List_Description, description);

        long result = dbRef.update(TABLE_NAME5, cv, "id=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Failed to update list !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "List updated successfully!", Toast.LENGTH_SHORT).show();

        }
    }

    //Delete a row of list--------------------------------------
    public void deleteListRow(String row_id) {

        SQLiteDatabase dbRef = this.getWritableDatabase();
        long result = dbRef.delete(TABLE_NAME4, "id=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Failed to delete list!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted the list!", Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteSubListRow(String row_id) {

        SQLiteDatabase dbRef = this.getWritableDatabase();
        long result = dbRef.delete(TABLE_NAME5, "id=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Failed to delete list!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted the list!", Toast.LENGTH_SHORT).show();
        }

    }

    public void updateList(String id) {
    }


    //Delete all lists--------------------------------------
    public void deleteAllLists() {
        SQLiteDatabase dbRef = this.getWritableDatabase();
        dbRef.execSQL("DELETE FROM " + TABLE_NAME4);

    }

    //get total item count
    public Cursor getTotalItems(String id) {
        SQLiteDatabase dbRef = this.getReadableDatabase();
        String total_quantity = "SELECT " + "SUM( " + COLUMN_my_sub_list_Quantity +")" + "FROM " + TABLE_NAME5+" WHERE m_id= "+id ;

        Cursor cursor = null;
        if (dbRef != null) {
            cursor = dbRef.rawQuery(total_quantity, null);
        }
        return cursor;
    }



}

