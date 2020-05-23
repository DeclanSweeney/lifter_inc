package com.example.lifter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button registerButton;
    private EditText emailInput, passwordInput;
    private TextView existingAccountLink;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        InitializeFields();


    }

    private void InitializeFields() {
        registerButton = findViewById(R.id.register_button);
        emailInput = findViewById(R.id.register_email);
        passwordInput = findViewById(R.id.register_password);
        existingAccountLink = findViewById(R.id.existing_account_link);
        progressDialog = new ProgressDialog(this);

        registerButton.setOnClickListener(this);
        existingAccountLink.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    //Sets on click listener for button/ links
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.existing_account_link:
                intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            case R.id.register_button:
                CreateNewAccount();
        }
    }

    //Called to create a new entry within the database for a new user
    //Firebase user auth handles account duplication requests and all possible errors for now
    private void CreateNewAccount() {
        final String userEmail = emailInput.getText().toString().trim();
        String userPassword = passwordInput.getText().toString().trim();

        //If the user or password is empty then it will return an error
        //TODO: Add email verification and add password length rules
        if (userEmail.isEmpty()) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
        }
        else if (userPassword.isEmpty() || userPassword.length() < 6) {
            Toast.makeText(this, "Please enter a valid password", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.setTitle("Creating New User");
            progressDialog.setMessage("Please wait...\n account creation in progress");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();

            //Creates the user entry in the database
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String currentUserId = firebaseAuth.getUid();
                        assert currentUserId != null;
                        databaseReference.child("Users").child(currentUserId).setValue("");

                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        SendUserToMain();
                    } else {
                        String errorMessage = Objects.requireNonNull(task.getException()).toString();
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Error: "+errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //Sends the user to Main Activity
    private void SendUserToMain() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
