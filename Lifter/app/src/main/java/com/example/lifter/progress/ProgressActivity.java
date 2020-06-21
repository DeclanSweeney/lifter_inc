package com.example.lifter.progress;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifter.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProgressActivity extends AppCompatActivity implements ProgressPhotoGalleryFragment.OnListFragmentInteractionListener {

    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 1;
    private MyProgressPhotoGalleryRecyclerViewAdapter adapter;
    private List<Photo> photos = new ArrayList<Photo>();
    private PhotoRepository photoRepository = new PhotoRepository();
    private ImageView thumbnailImageView;
    private Toolbar mainToolbar;
    private Button btnTakePhoto;
    private TextView photoCreatedOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        thumbnailImageView = findViewById(R.id.photo_thumbnail);
        photoCreatedOn = findViewById(R.id.photo_created_on);

        if (recyclerViewAdapter == null) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment);
            recyclerView = (RecyclerView) currentFragment.getView();
            recyclerViewAdapter = ((RecyclerView) currentFragment.getView()).getAdapter();
        }

        adapter = new MyProgressPhotoGalleryRecyclerViewAdapter(photos, this);
        recyclerView.setAdapter(adapter);
        loadPhotos();
        setThumbnail();
        mainToolbar = findViewById(R.id.progress_app_bar);

        setSupportActionBar(mainToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Progress");
        btnTakePhoto = findViewById(R.id.btn_take_photo);


        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
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
    private void loadPhotos() {
        // First clear all the photos we currently have.
        photos.clear();

        // Get photos from storage using repository
        List<Photo> photoList = photoRepository.getPhotosFromDeviceStorage(this);

        // Add all the photos to list which is used inside the UI.
        photos.addAll(photoList);

        // Notify the UI that photo has been added so that it can display them.
        adapter.notifyDataSetChanged();
    }

    /*
     * Sets the Thumbnail photo
     **/
    private void setThumbnail(){
        // If we have photos then show the first photo as the thumbnail
        if(photos.size() >= 1){
            Photo firstPhoto = photos.get(0);
            // Set the first photo as thumbnail
            thumbnailImageView.setImageURI(firstPhoto.photoPath);
            photoCreatedOn.setText(firstPhoto.date);
        }
    }

    @Override
    public void onListFragmentInteraction(final Photo item) {
        thumbnailImageView.setImageURI(item.photoPath);
        photoCreatedOn.setText(item.date);
    }

    @Override
    public void onLongPress(final Photo item) {
           new AlertDialog.Builder(this)
                .setTitle("Progress Photo")
                .setMessage("Delete this progress photo?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Try delete the photo and store if it was deleted into a new variable
                        boolean deleted = photoRepository.deletePhoto(item);

                        // If photo was deleted notify the user and refresh the photo list.
                        if (deleted) {
                            Toast.makeText(ProgressActivity.this, "Progress Photo Deleted.", Toast.LENGTH_SHORT).show();
                            // Refresh photo view once the photo has been deleted
                            loadPhotos();
                        } else {
                            // Show error message to user whether we have successfully deleted the photo.
                            Toast.makeText(ProgressActivity.this, "Could not delete Progress Photo.", Toast.LENGTH_SHORT).show();
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

            Uri photoUri = photoRepository.createFile(this);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

            // Launch the camera and start taking photo. Once the user comes back to our app from camera app.
            // The onActivityResult will be called.
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // When the photo is taken by the camera app this method will be called
        // And we will take the image and show it on the image view in our activity.
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            // reload the images and update the list.
            loadPhotos();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
