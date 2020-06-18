package com.example.lifter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ExerciseAdapter extends FirestoreRecyclerAdapter<Exercise,ExerciseAdapter.ExerciseHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ExerciseAdapter(@NonNull FirestoreRecyclerOptions<Exercise> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ExerciseAdapter.ExerciseHolder holder, int position, @NonNull Exercise model) {
    holder.ExerciseName.setText(model.getEx_name());
    }

    @NonNull
    @Override
    public ExerciseAdapter.ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise,parent,false);
        return new ExerciseHolder(v);
    }

    class ExerciseHolder extends RecyclerView.ViewHolder{

        TextView ExerciseName;

        public ExerciseHolder(@NonNull View itemView) {
            super(itemView);
            ExerciseName = itemView.findViewById(R.id.ex_name);
        }
    }

}

