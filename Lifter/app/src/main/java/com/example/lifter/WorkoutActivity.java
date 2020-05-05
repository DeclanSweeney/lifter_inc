package com.example.lifter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WorkoutActivity extends AppCompatActivity implements View.OnClickListener  {

    // objects
    private Button finbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        finbutton = (Button)findViewById(R.id.Finishworkbutton);
        finbutton.setOnClickListener(this);
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
