package com.example.lifter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;


public class ListExercise extends AppCompatActivity {
    // objects - in order words an exercise
    List<Exercise> exerciseList = new ArrayList<>();

    //RecyclerViews
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecyclerViewAdapterWorkout adapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exercise);

        init();
        recyclerView = findViewById(R.id.list_ex);
        adapt = new RecyclerViewAdapterWorkout(exerciseList,getBaseContext());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapt);
    }

    private void init() {

        //TODO: File - input/ output to store descriptions, recode the images










    }}



