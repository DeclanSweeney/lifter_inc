package com.example.lifter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button calendarButton, workoutButton, progressButton, chatButton, surveyButton;
    private Toolbar mainToolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mainToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Lifter");

        //Instantiate the buttons
        calendarButton = findViewById(R.id.button_calendar);
        workoutButton = findViewById(R.id.button_workout);
        progressButton = findViewById(R.id.button_progress);
        surveyButton = findViewById(R.id.button_survey);
        chatButton = findViewById(R.id.button_chat);

        //Set onlclick listeners for the buttons
        calendarButton.setOnClickListener(this);
        workoutButton.setOnClickListener(this);
        progressButton.setOnClickListener(this);
        surveyButton.setOnClickListener(this);
        chatButton.setOnClickListener(this);
        
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.button_calendar:
                intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
                break;
            case R.id.button_chat:
                Toast.makeText(this, "Chat", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
                break;
            case R.id.button_workout:
                Toast.makeText(this, "Workout", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, WorkoutActivity.class);
                startActivity(intent);
                break;
            case R.id.button_survey:
                Toast.makeText(this, "Survey", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, SurveyActivity.class);
                startActivity(intent);
                break;
            case R.id.button_progress:
                Toast.makeText(this, "Progress", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, ProgressActivity.class);
                startActivity(intent);
                break;
        }
    }
}
