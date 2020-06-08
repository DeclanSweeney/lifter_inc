package com.example.lifter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WorkoutActivity extends AppCompatActivity implements View.OnClickListener  {

    // objects
    private Button finbutton,workoutButton,exerciseButton;
    private RecyclerView for_ex;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        //buttons - to connect to buttons
        finbutton = (Button)findViewById(R.id.Finishworkbutton);
        finbutton.setOnClickListener(this);
        exerciseButton = (Button)findViewById(R.id.exercise);
        exerciseButton.setOnClickListener(this);

        //Recycle view connection
        for_ex = findViewById(R.id.for_ex);
        workoutButton = (Button)findViewById(R.id.Workout);
        workoutButton.setOnClickListener(this);

        // making a layout
        LinearLayout layoutManager = new LinearLayout(this);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        // need to fix

        //for_ex.setLayoutManager(layoutManager);
    }


    @Override
    public void onClick(View v) {
        // switch case for the buttons
        switch(v.getId()){
            case R.id.Finishworkbutton:
                Toast.makeText(this,"Finished workout",Toast.LENGTH_SHORT).show();
                break;
            case R.id.Workout:
                Intent intent = new Intent(WorkoutActivity.this,ListExercise.class);
                startActivity(intent);
                break;
            case R.id.exercise:
                Intent beta = new Intent(WorkoutActivity.this,specific_exercise.class);
                startActivity(beta);
        }



    }
}
