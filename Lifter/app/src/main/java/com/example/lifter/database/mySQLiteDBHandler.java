package com.example.lifter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class mySQLiteDBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_db";


    public mySQLiteDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Note.CREATE_TABLE);
        db.execSQL(Suggested.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Suggested.TABLE_SUGGESTED);

        // Create tables again
        onCreate(db);
    }

    public long insertNote(String note, String event, String desc,String listwork,String status) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Note.COLUMN_DATE, note);
        values.put(Note.COLUMN_EVENT, event);
        values.put(Note.COLUMN_DESC, desc);
        values.put(Note.COLUMN_WORK, listwork);
        values.put(Note.COLUMN_STATUS, status);

        // insert row
        long id = db.insert(Note.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Note getNote(String id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Note.TABLE_NAME,
                new String[]{Note.COLUMN_ID, Note.COLUMN_DATE, Note.COLUMN_EVENT, Note.COLUMN_DESC, Note.COLUMN_WORK,Note.COLUMN_STATUS},
                Note.COLUMN_DATE + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Note note = null;
        try {
            note = new Note(
                    cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Note.COLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndex(Note.COLUMN_EVENT)),
                    cursor.getString(cursor.getColumnIndex(Note.COLUMN_DESC)),
                    cursor.getString(cursor.getColumnIndex(Note.COLUMN_WORK)),
                    cursor.getString(cursor.getColumnIndex(Note.COLUMN_STATUS)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // close the db connection
        cursor.close();

        return note;
    }

    public long insertSuggested(String day) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Suggested.COLUMN_DAY, day);

        // insert row
        long id = db.insert(Suggested.TABLE_SUGGESTED, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public void clearSuggestedList(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Suggested.TABLE_SUGGESTED, null, null);
    }

    public Suggested getSuggested(String id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Suggested.TABLE_SUGGESTED,
                new String[]{Suggested.COLUMN_ID,  Suggested.COLUMN_DAY},
                Suggested.COLUMN_DAY + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Suggested suggested = null;
        try {
            suggested = new Suggested(
                    cursor.getInt(cursor.getColumnIndex(Suggested.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Suggested.COLUMN_DAY)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // close the db connection
        cursor.close();

        return suggested;
    }



    public List<Suggested> getAllSuggestDay() {
        List<Suggested> suggestedArrayList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Suggested.TABLE_SUGGESTED + " ORDER BY " +
                Suggested.COLUMN_DAY + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Suggested suggested = new Suggested();
                suggested.setId(cursor.getInt(cursor.getColumnIndex(Suggested.COLUMN_ID)));
                suggested.setDay(cursor.getString(cursor.getColumnIndex(Suggested.COLUMN_DAY)));

                suggestedArrayList.add(suggested);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return suggestedArrayList;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + Note.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateNote(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_STATUS,"1");

        // updating row
        return db.update(Note.TABLE_NAME, values, Note.COLUMN_DATE + "=?",
                new String[]{String.valueOf(id)});
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Note.TABLE_NAME, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }

}