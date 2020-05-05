package com.example.lifter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText usernameInput, gymNameInput;
    private Button updateSettingsButton;
    String uid;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

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

        updateSettingsButton.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        uid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_settings_button:
                UpdateSettings();
                break;
        }
    }

    private void UpdateSettings() {
        String username = usernameInput.getText().toString().trim();
        String gymName = gymNameInput.getText().toString().trim();

        if (username.isEmpty()) {
            Toast.makeText(this, "Please enter a user name", Toast.LENGTH_SHORT).show();
        }
        else if (gymName.isEmpty()) {
            Toast.makeText(this, "Please enter a gym or N/A", Toast.LENGTH_SHORT).show();    
        }
        else {
            HashMap<String, String> userMap = new HashMap<>();
            userMap.put("uid", uid);
            userMap.put("name", username);
            userMap.put("gym", gymName);

            databaseReference.child("Users").child(uid).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SettingsActivity.this, "Update Complete", Toast.LENGTH_SHORT).show();
                        SendUserToMain();
                    } else {
                        Toast.makeText(SettingsActivity.this, "Error: "+task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void SendUserToMain() {
        Intent settingsIntent = new Intent(SettingsActivity.this, MainActivity.class);
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(settingsIntent);
        finish();
    }

    private void GetUserData() {
        databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && (dataSnapshot.hasChild("profile_image"))) {
                    String profileImageFromDB = dataSnapshot.child("profile_image").getValue().toString().trim();
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
