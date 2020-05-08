package com.example.lifter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListExercises extends AppCompatActivity {


    //need to make a recycleview to display the list of exercises
    // first the objects of the recycle views
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecycleWorkViewAdapter adapter;


    //Making a list of Exercise
    List<Exercise> exerciseList = new ArrayList<Exercise>();



    //When creating an exercise for the list
    protected void OnCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list_exercise);

        //retrieved data
        initData();

        //now making the components and assigning context to the components
        recyclerView = (RecyclerView)findViewById(R.id.list_ex);
        adapter = new RecycleWorkViewAdapter(exerciseList,getBaseContext());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }


    //adding the exercises to the list
    public void initData(){
    exerciseList.add(new Exercise(R.drawable.push_up,"push up"));
    }



}
