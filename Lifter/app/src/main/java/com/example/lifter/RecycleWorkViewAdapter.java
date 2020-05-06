package com.example.lifter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//making a class to hold the view
class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    //variables for text and images
    private ImageView image;
    private TextView txt;

    //references to other class
    private  ItemClickListener itemClickListener;

    //constructor
    public RecycleViewHolder(View itemView){
        super(itemView);
        //this is for listing the images.
        image = (ImageView) itemView.findViewById(R.id.ex_img);
        txt = (TextView) itemView.findViewById(R.id.ex_name);

        // apply actionListener
        itemView.setOnClickListener(this);
    }

    // Need to set clicker
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

    }
}

public class RecycleWorkViewAdapter extends RecyclerView.Adapter<RecycleViewHolder> {

    // making the list of exercises
    private List<Exercise> exerciseList;
    private Context context;

    //constructor
    public RecycleWorkViewAdapter(List<Exercise> exerciseList, Context context) {
        this.exerciseList = exerciseList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        //making the container
        View itemView = inflater.inflate(R.layout.list_exercise,parent,false);
        return new RecycleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
