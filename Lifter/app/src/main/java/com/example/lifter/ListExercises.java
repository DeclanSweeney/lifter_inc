package com.example.lifter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListExercises extends AppCompatActivity {

    //Making a list of Exercise
    List<Exercise> exerciseList = new ArrayList<Exercise>();

    //When creating an exercise for the list
    protected void OnCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list_exercise);
    }


    public void initData(){
    }



}
