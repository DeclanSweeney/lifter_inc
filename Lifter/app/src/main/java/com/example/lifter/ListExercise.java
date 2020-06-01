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

        //list of exercises will remain here
        exerciseList.add(new Exercise(R.drawable.pushup,"Push up on knees ","Nothing","Nothing","BEGINNER","CHEST",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Push up","Nothing","Nothing","BEGINNER","CHEST",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Bench Press","Nothing","Nothing","INTERMEDIATE","CHEST",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Dumbell Press","Nothing","Nothing","INTERMEDIATE","CHEST",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Dumbell fly","Nothing","Nothing","EXPERIENCED","CHEST",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Machine row","Nothing","Nothing","BEGINNER","BACK",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Lat Pull down","Nothing","Nothing","BEGINNER","BACK",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Bent Over row","Nothing","Nothing","INTERMEDIATE","BACK",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Single arm pull down","Nothing","Nothing","INTERMEDIATE","BACK",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Pull up","Nothing","Nothing","EXPERIENCED","BACK",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Seated Alternating Curl","Nothing","Nothing","BEGINNER","BACIEP",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Seated Alternating Hammer Curl","Nothing","Nothing","BEGINNER","BACIEP",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Standing cable Curl","Nothing","Nothing","INTERMEDIATE","BACIEP",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Rope Hammer curl","Nothing","Nothing","INTERMEDIATE","BACIEP",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Preacher curl","Nothing","Nothing","EXPERIENCED","BACIEP",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Cable rope pushdown","Nothing","Nothing","BEGINNER","TRCIEPS",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Chair dips","Nothing","Nothing","BEGINNER","TRCIEPS",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Overhead tricep extension","Nothing","Nothing","INTERMEDIATE","TRCIEPS",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Skull Crusher","Nothing","Nothing","INTERMEDIATE","TRCIEPS",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"DIPS","Nothing","Nothing","EXPERIENCED","TRCIEPS",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Air Squats","Nothing","Nothing","BEGINNER","LEGS",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Walking Lunges","Nothing","Nothing","BEGINNER","LEGS",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Leg Press","Nothing","Nothing","INTERMEDIATE","LEGS",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"Weighted back squat","Nothing","Nothing","INTERMEDIATE","LEGS",20.0));
        exerciseList.add(new Exercise(R.drawable.pushup,"DeadLift","Nothing","Nothing","EXPERIENCED","LEGS",20.0));









    }}



