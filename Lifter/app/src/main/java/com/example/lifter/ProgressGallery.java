package com.example.lifter;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.view.View;
import android.widget.GridView;

import java.io.File;

public class ProgressGallery extends AppCompatActivity {

    boolean boolean_folder;

    GridView gv_folder;
    private static final int REQUEST_PERMISSIONS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_gallery);
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] list = storageDir.listFiles();

        for (File file : list) {
            int i = 0;
        }

    }

}
