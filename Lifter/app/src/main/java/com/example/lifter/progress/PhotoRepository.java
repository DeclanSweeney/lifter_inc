package com.example.lifter.progress;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is responsible for load all images inside the directory of the app.
 **/
public class PhotoRepository {

    /**
     * @return This method will return the number of photos taken by the user.
     */
    public int getPhotoCounts(Context context) {
        // Find all the photos saved
        List<Photo> photos = getPhotosFromDeviceStorage(context);
        int numberOfPhotos = photos.size();
        return numberOfPhotos;
    }

    /**
     * @return This method will return all the photos the user currently has taken and which have
     * been saved to the device.
     */
    public List<Photo> getPhotosFromDeviceStorage(Context context) {
        // Get the reference to the Android/data/com.example.lifter/files/Pictures folder.
        File folder = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Get all the files inside the Android/data/com.example.lifter/files/Pictures folder.
        File[] files = folder.listFiles();

        // Photo response
        List<Photo> photos = new ArrayList<Photo>();

        // Loop through all the files photos inside Android/data/com.example.lifter/files/Pictures
        // folder and add them into our photos array list.
        for (File pic : files) {

            // Find the path of our photo.
            Uri photoPath = Uri.fromFile(pic);

            // Find the date when the photo was last created we will show in the UI.
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
            Date lastModDate = new Date(pic.lastModified());

            // Convert the date object into string
            String date = format.format(lastModDate);

            // Finally add the data into our photo object so that we can return it.
            Photo photo = new Photo(photoPath, date);
            photos.add(photo);
        }
        return photos;
    }

    /**
     * This method deletes photos
     *
     * @param photoToDelete this is the photo to be deleted.
     * @return Returns return if the photo is deleted or else false.
     */
    public boolean deletePhoto(Photo photoToDelete) {

        // Get the file path of the photo we going to delete.
        File file = new File(photoToDelete.photoPath.getPath());

        // Check if photo exist.
        boolean exist = file.exists();

        // if photo does not exist we have to return false so that inside the progress activity
        // we can display that photo could not be deleted due to system issues.
        if (exist == false) return false;

        // Keep track if the photo is deleted
        boolean deleted = false;

        // If the photo exist we delete it.
        if (exist) {
            // try delete the photo and store if the photo is deleted into the deleted variable.
            deleted = file.delete();
        }

        // return whether photo was deleted.
        return deleted;
    }

    /**
     * This method creates a blank file where the photo will be saved to.
     * When the user takes the photo the blank file will be filled with the photo.
     * NOTE - If the camera app is launched and photo is not taken. The blank file
     * will be displayed inside the Progress activity.
     *
     * @return URI of the file which the photo should be saved to.
     */
    public Uri createFile(Context context) {
        // Create the File where the photo should go
        File photoFile = null;
        try {
            // Generate the file name using a time stamp. Note file name must be unique, we are
            // using datetime to represent the file name this will guarantee a unique name.
            String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
            File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            photoFile = File.createTempFile(
                    "" + timeStamp + "",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

        } catch (IOException ex) {
            // Error occurred while creating the File
            ex.printStackTrace();
        }

        // Get the path of the newly created blank photo.
        Uri photoURI = FileProvider.getUriForFile(context, "com.example.lifter", photoFile);
        return photoURI;
    }

    /**
     * This method deletes all the photos that are saved on the device.
     */
    public void deleteAllPhotos(Context context){
        // Get all the photos on the device
        List<Photo> photos = getPhotosFromDeviceStorage(context);

        // Delete all photos 1 by 1.
        for (Photo photo: photos) {
            deletePhoto(photo);
        }
    }
}