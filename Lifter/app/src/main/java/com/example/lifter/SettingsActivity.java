package com.example.lifter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText usernameInput, gymNameInput;
    private Button updateSettingsButton;
    private ImageView profilePic;
    private static final int GALLERY_SELECTION = 1;

    String uid, imageURL;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private StorageReference databaseUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        InitializeFields();
        GetUserData();

    }

    public void InitializeFields() {
        usernameInput = findViewById(R.id.user_name_input);
        gymNameInput = findViewById(R.id.gym_name_input);
        updateSettingsButton = findViewById(R.id.update_settings_button);
        profilePic = findViewById(R.id.profile_image);

        updateSettingsButton.setOnClickListener(this);
        profilePic.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        uid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        databaseUserRef = FirebaseStorage.getInstance().getReference().child("Profile Pictures");
    }

    //Called when selecting the profile picture to change the image locally. Allows the user to crop
    //their picture using code from a public git repo
    //If the image selection and crop are successful then adds the image to the database file storage
    //TODO: Find name of public repo for credit
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_SELECTION && resultCode == RESULT_OK && data != null) {
            Uri profileImageUri = data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                final Uri resultUri = result.getUri();
                final StorageReference databaseFilePath = databaseUserRef.child(uid + ".jpeg");
                databaseFilePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SettingsActivity.this, "Image updated", Toast.LENGTH_SHORT).show();
                            Toast.makeText(SettingsActivity.this, imageURL, Toast.LENGTH_SHORT).show();
                            UpdateProfilePicture();

                        } else {
                            Toast.makeText(SettingsActivity.this, "Error: "+task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, "Error: "+error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Uploads the image to the database file storage with the file name generated with the current
    //user's UID and .jpeg extension
    private void UpdateProfilePicture() {
        StorageReference imageFilepath = FirebaseStorage.getInstance().getReference("Profile Pictures/"+uid+".jpeg");
        imageFilepath.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(SettingsActivity.this, "Download URL: "+uri.toString(), Toast.LENGTH_SHORT).show();
                        databaseReference.child("Users").child(uid).child("profile_image").setValue(uri.toString());
                    }
                });
    }

    @Override
    //Sets onclick listeners
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_settings_button:
                UpdateSettings();
                break;
            case R.id.profile_image:
                Intent sendToGallery = new Intent();
                sendToGallery.setAction(Intent.ACTION_GET_CONTENT);
                sendToGallery.setType("image/*");
                startActivityForResult(sendToGallery, GALLERY_SELECTION);
        }
    }

    //Attempts to push the data entered to the database to update the current user's entry
    private void UpdateSettings() {
        //Gets the text from the user edittexts
        String username = usernameInput.getText().toString().trim();
        String gymName = gymNameInput.getText().toString().trim();

        //Will reject if the information is empty
        if (username.isEmpty()) {
            Toast.makeText(this, "Please enter a user name", Toast.LENGTH_SHORT).show();
        }
        else if (gymName.isEmpty()) {
            Toast.makeText(this, "Please enter a gym or N/A", Toast.LENGTH_SHORT).show();    
        }
        else {
            HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("uid", uid);
            userMap.put("name", username);
            userMap.put("gym", gymName);

            //Updates database using local map variable for key/value pairs
            databaseReference.child("Users").child(uid).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SettingsActivity.this, "Update Complete", Toast.LENGTH_SHORT).show();
                        UpdateProfilePicture();
                        SendUserToMain();
                    } else {
                        Toast.makeText(SettingsActivity.this, "Error: "+task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //Sends the user to the Main Activity
    private void SendUserToMain() {
        Intent settingsIntent = new Intent(SettingsActivity.this, MainActivity.class);
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(settingsIntent);
        finish();
    }

    //Gets the user data from the database for use by the Settings UI
    private void GetUserData() {
        databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && (dataSnapshot.hasChild("profile_image"))) {
                    String profileImageFromDB = dataSnapshot.child("profile_image").getValue().toString();
                    Picasso.get().load(profileImageFromDB).into(profilePic);
                }
                if (dataSnapshot.exists() && (dataSnapshot.hasChild("name"))) {
                    String usernameFromDB = dataSnapshot.child("name").getValue().toString().trim();
                    String gymNameFromDB = dataSnapshot.child("gym").getValue().toString().trim();

                    usernameInput.setText(usernameFromDB);
                    gymNameInput.setText(gymNameFromDB);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
