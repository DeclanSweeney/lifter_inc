package com.example.lifter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProgressActivity extends AppCompatActivity implements View.OnClickListener {
    private Button takePhotoButton;
    private Button buttonViewProgressPhotos;
    private ImageView imageView;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String currentPhotoPath;
    private static final int REQUEST_STORAGE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        // map view buttons to button class
        takePhotoButton = findViewById(R.id.button_take_photo);
        buttonViewProgressPhotos = findViewById(R.id.button_view_progress_photos);
        //Add click listener to our buttons

        // When ever the takephoto button is clicked it will open a camera
        takePhotoButton.setOnClickListener(this);

        // Everytime the buttonViewProgressPhotos is clicked it will open another activity and show yuo all the photos that user has taken.
        buttonViewProgressPhotos.setOnClickListener(this);

        // map image view
        imageView = findViewById(R.id.imageView);
    }

    // This method will run when ever the  takePhotoButton or buttonViewProgressPhotos is clicked
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_take_photo:
                // First we need to ask the user to give permission to save photo to external location
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // If you do not have permission, request it
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_STORAGE_PERMISSION);
                } else {
                    // When button_take_photo is clicked this method will run.
                    takePicture();
                }
                break;
            case R.id.button_view_progress_photos:
                Intent intent = new Intent(this, ProgressGallery.class);
                startActivity(intent);
                break;
        }
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

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
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

            // This method will show the photo on the image view.
            showPhotoOnImageView();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showPhotoOnImageView() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

}
