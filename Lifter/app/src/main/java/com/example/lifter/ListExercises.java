package com.example.lifter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ListExercises extends AppCompatActivity {

    //Making a list of Exercise
    List<Exercise> exerciseList = new ArrayList<>();

    //When creating an exercise for the list
    @Override
    protected void OnCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    //
    public void initData(){

    }



}
