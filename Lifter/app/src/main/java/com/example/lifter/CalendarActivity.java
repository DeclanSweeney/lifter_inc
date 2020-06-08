package com.example.lifter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.lifter.database.Note;
import com.example.lifter.database.mySQLiteDBHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";
    private mySQLiteDBHandler dbHandler;
    private TextView tvTitle, tvDesc, tvworkPlan, tvRandomworkPlan, tvdayName;
    private CalendarView calendarView;
    private String selectedDate;
    FloatingActionButton addEvent, addSuggestedEvent;
    LinearLayout eventLayout, workOutLay, suggesteventLayout, suggestedDayLay;
    String[] workOutActivity = new String[]{"Leg", "Chest", "Back", "Core", "Arms"};

    private CardView eventCardLayout;
    private Button buttonDone;
    private Note note=new Note();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        tvTitle = findViewById(R.id.tvTitle);
        tvDesc = findViewById(R.id.tvDesc);
        tvworkPlan = findViewById(R.id.tvworkPlan);
        calendarView = findViewById(R.id.calendarView);
        addEvent = findViewById(R.id.addEvent);
        addSuggestedEvent = findViewById(R.id.addSuggestedEvent);
        eventLayout = findViewById(R.id.eventLayout);
        workOutLay = findViewById(R.id.workOutLay);
        suggesteventLayout = findViewById(R.id.suggesteventLayout);
        suggestedDayLay = findViewById(R.id.suggestedDayLay);
        tvRandomworkPlan = findViewById(R.id.tvRandomworkPlan);
        tvdayName = findViewById(R.id.tvdayName);
        eventCardLayout = findViewById(R.id.eventCardLayout);
        buttonDone = findViewById(R.id.buttonDone);


        //=================== Initialize Table ===================//
        dbHandler = new mySQLiteDBHandler(this);


        //======================== Calender Date Change ====================== //
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                ///================ Get Event by this method =====================///
                selectedDate = Integer.toString(year) + Integer.toString(month) + Integer.toString(dayOfMonth);
                ReadDatabase(selectedDate);

                ///================ Get Suggested Event by this method =====================///
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
                String day = days[calendar.get(Calendar.DAY_OF_WEEK) - 1];
                ReadAllSuggested(day);

            }
        });


        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddEventActivity.class);
                startActivity(intent);
            }
        });

        addSuggestedEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SuggestedEventActivity.class);
                startActivity(intent);

            }
        });

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatabase();
            }
        });

    }

    //======================== Get Data From Database (Event Data by Date wise) ========================//
    public void ReadDatabase(String selectedDate) {
        if (dbHandler.getNote(selectedDate) != null) {
            if (dbHandler.getNote(selectedDate).getStatus().equals("1")) {
                eventCardLayout.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
                buttonDone.setVisibility(View.GONE);
            } else {
                eventCardLayout.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                buttonDone.setVisibility(View.VISIBLE);
            }
            eventLayout.setVisibility(View.VISIBLE);

            tvTitle.setText(dbHandler.getNote(selectedDate).getEvent());
            tvDesc.setText(dbHandler.getNote(selectedDate).getDescription());
            if (dbHandler.getNote(selectedDate).getWork().length() > 3) {
                workOutLay.setVisibility(View.VISIBLE);
                String text = dbHandler.getNote(selectedDate).getWork().replace("[", "").replace("]", "");
                tvworkPlan.setText(text);
            }
        } else {
            eventLayout.setVisibility(View.GONE);
        }

    }


    //======================== Get Data From Database (Suggested Event Data by date wise) ========================//
    public void ReadAllSuggested(String dayOfWeek) {

        dbHandler.getAllSuggestDay();
        if (dbHandler.getAllSuggestDay() != null) {
            if (dbHandler.getAllSuggestDay().size() > 0) {

                String days = dbHandler.getAllSuggestDay().get(0).getDay().replace("[", "").replace("]", "");
                ArrayList<String> myList = new ArrayList<>(Arrays.asList(days.split(",")));
                for (int i = 0; i < myList.size(); i++) {
                    Log.e(TAG, "ReadAllSuggested: " + myList.get(i).trim());
                    Log.e(TAG, "dayOfWeek: " + dayOfWeek);
                    if (myList.get(i).trim().equals(dayOfWeek)) {
                        int randomIndex = new Random().nextInt(workOutActivity.length);
                        String randomName = workOutActivity[randomIndex];
                        tvRandomworkPlan.setText(randomName);
                        suggesteventLayout.setVisibility(View.VISIBLE);
                        break;
                    } else {
                        suggesteventLayout.setVisibility(View.GONE);
                    }
                }
            }
        }

    }

    //=================== Update status in database(Event Data by Date wise) ============//

    public void updateDatabase() {
        if (dbHandler.getNote(selectedDate) != null) {
            dbHandler.updateNote(dbHandler.getNote(selectedDate).getDate());
        }
    }

    //=================== Every time method Called when you come on this page ============//
    @Override
    protected void onResume() {
        super.onResume();
        if (dbHandler.getAllSuggestDay() != null) {
            if (dbHandler.getAllSuggestDay().size() > 0) {
                suggestedDayLay.setVisibility(View.VISIBLE);
                String days = dbHandler.getAllSuggestDay().get(0).getDay().replace("[", "").replace("]", "");
                tvdayName.setText(days);
            } else {
                suggestedDayLay.setVisibility(View.GONE);
            }
        } else {
            suggestedDayLay.setVisibility(View.GONE);
        }
    }
}

