package com.example.lifter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class WorkoutActivity extends AppCompatActivity implements View.OnClickListener  {

    // objects
    private Button finbutton,exerciseButton;

    // for the firebase database
    Map<String, String> map = new HashMap<String,String>();
    private DatabaseReference databaseReference, backReference,BicepReference,ChestReference,LegReference,TricepReference;
    private FirebaseAuth userAuth;
    private RecyclerView rv;

    private FirebaseRecyclerOptions<Exercise> options;
    private FirebaseRecyclerAdapter<Exercise,RecyclerWorkoutViewHolder> adapter;

    private ArrayList<Exercise> arrayList ;
    //List<Exercise> exerciseList = new Map<Exercise>();

    ListExercise listex;
    Context context;
    Scanner userInput;

    //new method
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference exerciseref= db.collection("Exercise");
    private ExerciseAdapter exadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        //buttons - to connect to buttons
        finbutton = (Button)findViewById(R.id.Finishworkbutton);
        finbutton.setOnClickListener(this);
        exerciseButton = (Button)findViewById(R.id.exercise);
        exerciseButton.setOnClickListener(this);

        // recyclerView = findViewById(R.id.for_ex);
        // adapt = new RecyclerViewAdapterWorkout(exerciseList,getBaseContext());
        // layoutManager = new LinearLayoutManager(this);
        // recyclerView.setLayoutManager(layoutManager);
        // recyclerView.setAdapter(adapt)

        //rv = findViewById(R.id.for_ex);
        // rv.setLayoutManager(new LinearLayoutManager(this));
        //arrayList = new ArrayList<Exercise>();

        userAuth = FirebaseAuth.getInstance();

        //getting reference to the database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Exercise");
        backReference = FirebaseDatabase.getInstance().getReference().child("Exercise").child("Back");
        BicepReference = FirebaseDatabase.getInstance().getReference().child("Exercise").child("Bicep");
        ChestReference = FirebaseDatabase.getInstance().getReference().child("Exercise").child("Chest");
        LegReference = FirebaseDatabase.getInstance().getReference().child("Exercise").child("Legs");
        TricepReference = FirebaseDatabase.getInstance().getReference().child("Exercise").child("Tricep");

        options = new FirebaseRecyclerOptions.Builder<Exercise>().setQuery(databaseReference,Exercise.class).build();
        adapter = new FirebaseRecyclerAdapter<Exercise, RecyclerWorkoutViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerWorkoutViewHolder holder, int position, @NonNull Exercise model) {
            }

            @NonNull
            @Override
            public RecyclerWorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise,parent,false);
                return new RecyclerWorkoutViewHolder(view);
            }
        };
        setUpRecyclerView();
    }

    private void setUpRecyclerView(){
        Query query = exerciseref.orderBy("Back", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Exercise> options = new FirestoreRecyclerOptions.Builder<Exercise>()
                .setQuery(query,Exercise.class).build();

        exadapter = new ExerciseAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.for_ex);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(exadapter);

    }



    @Override
    public void onClick(View v) {
        // switch case for the buttons
        switch(v.getId()){
            case R.id.Finishworkbutton:
                Toast.makeText(this,"Finished workout",Toast.LENGTH_SHORT).show();
                break;
            case R.id.exercise:
                Intent beta = new Intent(WorkoutActivity.this,specific_exercise.class);
                startActivity(beta);
        }



    }
}