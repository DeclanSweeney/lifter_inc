package com.example.lifter;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//making a class to hold the view
class RecycleViewHolder implements View.OnClickListener
{
    //variables for text and images
    private ImageView image;
    private TextView txt;

    //references to other class
    private  ItemClickListener itemClickListener;

    //constructor
    public RecycleViewHolder(View itemView){
        //super(itemView);
    }

    @Override
    public void onClick(View v) {

    }
}

public class RecycleWorkViewAdapter {
}
