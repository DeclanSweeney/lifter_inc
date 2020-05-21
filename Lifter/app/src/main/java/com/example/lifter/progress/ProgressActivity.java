package com.example.lifter.progress;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lifter.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProgressActivity extends AppCompatActivity implements ProgressPhotoGalleryFragment.OnListFragmentInteractionListener {

    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton buttonTakePhoto;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private String currentPhotoPath;
    private static final int REQUEST_STORAGE_PERMISSION = 1;
    private  MyProgressPhotoGalleryRecyclerViewAdapter adapter;
    private List<PictureItem> items = new ArrayList<PictureItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        buttonTakePhoto = findViewById(R.id.button_take_photo);

        if (recyclerViewAdapter == null) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment);
            recyclerView = (RecyclerView) currentFragment.getView();
            recyclerViewAdapter = ((RecyclerView) currentFragment.getView()).getAdapter();
        }

        adapter = new MyProgressPhotoGalleryRecyclerViewAdapter(items,this);
        recyclerView.setAdapter(adapter);
        loadImages();

        buttonTakePhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // First we need to ask the user to give permission to save photo to external location
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // If you do not have permission, request it
                    ActivityCompat.requestPermissions(ProgressActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_STORAGE_PERMISSION);
                } else {
                    // When button_take_photo is clicked this method will run.
                    takePicture();
                }
            }
        });
    }

    /**
     * Load images from storage and add them to the list view.
     */
    private void loadImages(){
        items.clear();
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] list = storageDir.listFiles();

        for (File file : list) {
            PictureItem newItem = new PictureItem();
            newItem.uri = Uri.fromFile(file);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
            Date lastModDate = new Date(file.lastModified());
            newItem.date = format.format(lastModDate);
            items.add(newItem);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onListFragmentInteraction(final PictureItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Progress Photo")
                .setMessage("Delete this progress photo?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File file = new File(item.uri.getPath());
                        if (file.exists()) {
                            if (file.delete()) {
                                Toast.makeText(ProgressActivity.this, "Progress Photo Deleted.", Toast.LENGTH_SHORT).show();
                                // refresh photo view once the photo has been deleted
                                loadImages();
                            } else {
                                Toast.makeText(ProgressActivity.this, "Could not delete Progress Photo.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    // This method will show the camera app and allow you to take a photo.
    private void takePicture() {
        // The camera app is launched using a intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createPhotoFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.lifter",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                // Launch the camera and start taking photo. Once the user comes back to our app from camera app.
                // The onActivityResult will be called.
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    // This method returns the file which we will use the save to photo.
    private File createPhotoFile() throws IOException {

        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                ""+timeStamp+"",  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Set the path of the file this is where the photo will be temporally saved
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // When the photo is taken by the camera app this method will be called
        // And we will take the image and show it on the image view in our activity.
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            // reload the images and update the list.
            loadImages();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
