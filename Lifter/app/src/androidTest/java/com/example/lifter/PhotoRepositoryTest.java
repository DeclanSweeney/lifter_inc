package com.example.lifter;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.lifter.progress.Photo;
import com.example.lifter.progress.PhotoRepository;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class PhotoRepositoryTest {

    /**
     * This Test ensure that a blank file can be created when taking photo.
     */
    @Test
    public void Test_CreateBlankFileForPhoto() {

        // Context of the app under test.
        Context appContext = ApplicationProvider.getApplicationContext();

        // Create the repository
        PhotoRepository repository = new PhotoRepository();

        // Before starting we have to clear all the photos that are saved on the device to simulate
        // the unit test.
        repository.deleteAllPhotos(appContext);

        // Check how many photos there are in the app. There should be none.
        int numberOfFileBeforeBlankFileCreation = repository.getPhotoCounts(appContext);

        // Test to make sure there are no numberOfPhotos
        assertEquals(0,numberOfFileBeforeBlankFileCreation);

        // Now create the blank file which the photo will be saved to.
        repository.createFile(appContext);

        // Check if the new file is created by getting the number of files.
        int numberOfFileAfterBlankFileCreation = repository.getPhotoCounts(appContext);

        // Test to make sure there are no numberOfPhotos we should have 1 file.
        assertEquals(1,numberOfFileAfterBlankFileCreation);

        // Clear the photo we created for unit testing
        repository.deleteAllPhotos(appContext);
    }

    /**
     * This Test ensure that a photo can be deleted.
     */
    @Test
    public void Test_DeletePhoto() {

        // Context of the app under test.
        Context appContext = ApplicationProvider.getApplicationContext();

        // Create the repository
        PhotoRepository repository = new PhotoRepository();

        // Before starting we have to clear all the photos that are saved on the device to simulate
        // the unit test.
        repository.deleteAllPhotos(appContext);

        // Now create the blank photo which we will delete
        repository.createFile(appContext);

        // Now check if photo exists.
        int numberOfPhotos = repository.getPhotoCounts(appContext);

        // Test to make sure there are no numberOfPhotos we should have 1 file.
        assertEquals(1,numberOfPhotos);

        // Now we will delete the photo to simulate a user deleting the photo via the app.
        List<Photo> photos = repository.getPhotosFromDeviceStorage(appContext);

        // Ensure we have at least 1 photo
        assertEquals(1,photos.size());

        // Get the only photo we have
        Photo photo = photos.get(0);

        // Delete the photo
        boolean isDeleted = repository.deletePhoto(photo);

        assertEquals(true,isDeleted);

        // Clear the photo we created for unit testing
        repository.deleteAllPhotos(appContext);
    }

}
