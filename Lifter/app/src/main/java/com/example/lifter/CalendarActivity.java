package com.example.lifter;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lifter.database.mySQLiteDBHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";
    private mySQLiteDBHandler dbHandler;
    private TextView tvTitle, tvDesc,tvworkPlan;
    private CalendarView calendarView;
    private String selectedDate;
    private SQLiteDatabase sqLiteDatabase;
    FloatingActionButton addEvent;
    LinearLayout eventLayout,workOutLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        tvTitle = findViewById(R.id.tvTitle);
        tvDesc = findViewById(R.id.tvDesc);
        tvworkPlan = findViewById(R.id.tvworkPlan);
        calendarView = findViewById(R.id.calendarView);
        addEvent = findViewById(R.id.addEvent);
        eventLayout = findViewById(R.id.eventLayout);
        workOutLay = findViewById(R.id.workOutLay);

        dbHandler = new mySQLiteDBHandler(this);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = Integer.toString(year) + Integer.toString(month) + Integer.toString(dayOfMonth);
                ReadDatabase(selectedDate);
            }
        });

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddEventActivity.class);
                startActivity(intent);
            }
        });

    }

   //======================== Get Data From Database (Date wise) ========================//
    public void ReadDatabase(String selectedDate) {
        if (dbHandler.getNote(selectedDate)!=null){
            eventLayout.setVisibility(View.VISIBLE);
            tvTitle.setText(dbHandler.getNote(selectedDate).getEvent());
            tvDesc.setText(dbHandler.getNote(selectedDate).getDescription());
            if(dbHandler.getNote(selectedDate).getWork().length()>3){
                workOutLay.setVisibility(View.VISIBLE);
                String text = dbHandler.getNote(selectedDate).getWork().replace("[", "").replace("]", "");
                tvworkPlan.setText(text);
            }
        }else {
            eventLayout.setVisibility(View.GONE);
        }

    }
}

