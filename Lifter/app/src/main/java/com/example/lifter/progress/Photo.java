package com.example.lifter.progress;

import android.net.Uri;

public class Photo {
    public Uri photoPath;
    public String date;

    public Photo(Uri uri, String date){
        this.photoPath = uri;
        this.date = date;
    }
}
