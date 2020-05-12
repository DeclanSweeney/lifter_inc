package com.example.lifter.database;

public class Suggested {

    public static final String TABLE_SUGGESTED = "suggested";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DAY = "day";

    private int id;
    private String day;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_SUGGESTED + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_DAY + " TEXT"
                    + ")";

    public Suggested() {
    }

    public Suggested(int id, String day) {
        this.id = id;
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
