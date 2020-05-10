package com.example.lifter.database;

public class Note {
    public static final String TABLE_NAME = "notes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_EVENT = "event";
    public static final String COLUMN_DESC = "desc";
    public static final String COLUMN_WORK = "work";

    private int id;
    private String date;
    private String event;
    private String description;
    private String work;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_DATE + " TEXT,"
                    + COLUMN_EVENT + " TEXT,"
                    + COLUMN_DESC + " TEXT,"
                    + COLUMN_WORK + " TEXT"
                    + ")";


    public Note() {
    }


    public Note(int id, String date, String event, String description, String work) {
        this.id = id;
        this.date = date;
        this.event = event;
        this.description = description;
        this.work = work;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }
}