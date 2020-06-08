package com.example.lifter.progress;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lifter.R;
import com.example.lifter.progress.ProgressPhotoGalleryFragment.OnListFragmentInteractionListener;

import java.util.List;

public class MyProgressPhotoGalleryRecyclerViewAdapter extends RecyclerView.Adapter<MyProgressPhotoGalleryRecyclerViewAdapter.ViewHolder> {

    private final List<Photo> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyProgressPhotoGalleryRecyclerViewAdapter(List<Photo> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_progress_photo_gallery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mImageView.setImageURI(mValues.get(position).photoPath);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {
                    mListener.onLongPress(holder.mItem);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;
        public Photo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = view.findViewById(R.id.item_image_view);
        }
    }
}