package com.example.lifter.database;

public class Note {
    public static final String TABLE_NAME = "notes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_EVENT = "event";
    public static final String COLUMN_DESC = "desc";
    public static final String COLUMN_WORK = "work";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_TIME = "time";


    private int id;
    private String date;
    private String event;
    private String description;
    private String work;
    private String status;
    private String time;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_DATE + " TEXT,"
                    + COLUMN_EVENT + " TEXT,"
                    + COLUMN_DESC + " TEXT,"
                    + COLUMN_WORK + " TEXT,"
                    + COLUMN_STATUS + " TEXT,"
                    + COLUMN_TIME + " TEXT"
                    + ")";


    public Note() {
    }


    public Note(int id, String date, String event, String description, String work,String status,String time) {
        this.id = id;
        this.date = date;
        this.event = event;
        this.description = description;
        this.work = work;
        this.status = status;
        this.time = time;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}