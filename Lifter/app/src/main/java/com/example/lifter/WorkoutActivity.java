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
    private Button finbutton;
    private RecyclerView for_ex;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        //buttons - to connect to buttons
        finbutton = (Button)findViewById(R.id.Finishworkbutton);
        finbutton.setOnClickListener(this);

        //Recycle view connection
        for_ex = findViewById(R.id.for_ex);

        // making a layout
        LinearLayout layoutManager = new LinearLayout(this);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        // need to fix

        //for_ex.setLayoutManager(layoutManager);

        //making list of exercise
        List<Exercise> ex_list = new ArrayList<>();
        ex_list.add(new Exercise(R.drawable.ic_launcher_background,"Push up"));

        //setting up adapter
        RecycleWorkViewAdapter rwa = new RecycleWorkViewAdapter(ex_list,context);
        for_ex.setAdapter(rwa);

        rwa.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        Intent workoutintent;
        // switch case for the buttons
        switch(v.getId()){
            case R.id.Finishworkbutton:
                Toast.makeText(this,"Finished workout",Toast.LENGTH_SHORT).show();
        }



    }
}
