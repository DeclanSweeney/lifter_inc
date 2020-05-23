package com.example.lifter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


class RecyclerWorkoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //variables that may help
    public ImageView image;
    public TextView text;

    // need to use the interface
    private WorkoutItemClickListener workoutitemClickListener;

    // this is what holds the view so we need the images
    // and txt
    public RecyclerWorkoutViewHolder(View itemView){
        super(itemView);
        image = (ImageView)itemView.findViewById(R.id.ex_image);
        text = (TextView)itemView.findViewById(R.id.ex_name);

        itemView.setOnClickListener(this);
    }

    //setter for the interface as the object is private
    public void setWorkoutitemClickListener(WorkoutItemClickListener workoutitemClickListener) {
        this.workoutitemClickListener = workoutitemClickListener;
    }

    @Override
    public void onClick(View v) {
        workoutitemClickListener.onClick(v,getAdapterPosition());
    }
}

public class RecyclerViewAdapterWorkout extends RecyclerView.Adapter<RecyclerWorkoutViewHolder> {

    //Making exercises
    private List<Exercise> exerciseList;
    private Context context;

    public RecyclerViewAdapterWorkout(List<Exercise> exerciseList, Context baseContext) {
        this.exerciseList = exerciseList;
        this.context = baseContext;
    }

    @NonNull
    @Override
    public RecyclerWorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_exercise,parent,false);

        return new RecyclerWorkoutViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerWorkoutViewHolder holder, int position) {
        holder.image.setImageResource(exerciseList.get(position).getImg_ex());
        holder.text.setText(exerciseList.get(position).getEx_name());

        holder.setWorkoutitemClickListener(new WorkoutItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText();
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }}