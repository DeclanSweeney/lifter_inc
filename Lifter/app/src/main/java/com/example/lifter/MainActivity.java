package com.example.lifter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button calendarButton, workoutButton, progressButton, chatButton, surveyButton;
    private Toolbar mainToolbar;

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeFields();
        VerifyUser();
    }

    //If the user is not logged in, send them back to the Login Activity
    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseUser == null) {
            SendUserToLogin();
        }
    }

    //Send user to login activity and disable return via back button
    private void SendUserToLogin() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    //Send user to the settings activity
    private void SendUserToSettings() {
        Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(settingsIntent);
        finish();
    }

    //Set onClick listeners for the main screen buttons
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

    private void InitializeFields() {
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

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    //Hamburger menu for settings and logout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu ,menu);

        return true;
    }

    //Configure hamburger menu with options and listeners
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.settings_option:
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.logout_option:
                firebaseAuth.signOut();
                SendUserToLogin();
                finish();
        }
        return true;
    }

    //Will send the user to settings if they don't have at least a name set for their account
    private void VerifyUser() {
        if (firebaseUser == null) {
            firebaseAuth.signOut();
        }
        else {
            String currentUserId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

            databaseReference.child("Users").child(currentUserId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("name").exists()) {
                        Toast.makeText(MainActivity.this, "Welcome " + dataSnapshot.child("name").getValue(), Toast.LENGTH_SHORT).show();
                    } else {
                        SendUserToSettings();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
