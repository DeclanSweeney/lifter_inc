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
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

/**
 * Activity that the user will be first redirected to when opening the app. Allows them
 * to uniquely log in for access to the chat feature and future database storage of information
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    //Deprecated type
    //TODO: Find an alternative for this
    private ProgressDialog progressDialog;

    private Button loginButton;
    private EditText emailInput, passwordInput;
    private TextView forgetPasswordLink, registerAccountLink;

    public boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InitializeFields();
    }

    //Used when there was an error allowing the user to open to main without being logged in
    //I think if has been fixed now but leaving this here for now in case it comes up again
//    protected void onStart() {
//        super.onStart();
//
//        if (currentUser != null) {
//            SendUserToMain();
//        }
//    }

    //Sends the user to the Main Activity and clears access to Login activity once this is complete
    private void SendUserToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    //Sends the user to the Register activity allowing for use of the back button to return to login
    private void SendUserToRegister() {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    @Override
    //onClick listeners for all of the buttons and links
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.login_button:
                //Gets user input from text fields
                String userEmail = emailInput.getText().toString().trim();
                String userPassword = passwordInput.getText().toString().trim();
                UserLogin(userEmail, userPassword);
                break;
            case R.id.forget_password_link:
                break;
            case R.id.register_link:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
        }
    }

    //Method for allowing the user to login with their entry to the Firebase Database
    public void UserLogin(String userEmail, String userPassword) {
//        //Gets user input from text fields
//        String userEmail = emailInput.getText().toString().trim();
//        String userPassword = passwordInput.getText().toString().trim();

        //Condition checking for user and password
        //TODO: add check for email verification
//        if (userEmail.isEmpty()) {
//            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
//        }
//        else if (userPassword.isEmpty() || userPassword.length() < 6) {
//            Toast.makeText(this, "Please enter a valid password", Toast.LENGTH_SHORT).show();
//        }
//        else {
        if (isValidUserPass(userEmail, userPassword)){
            //Attempts to login with credentials compared to the Database
            //If the user exists and their password matches the entry then they will be redirected to
            //the main activity, otherwise they will receive an error in a toast message
            firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        SendUserToMain();
                        progressDialog.dismiss();
                        isLoggedIn = true;
                        finish();
                    } else {
                        String errorMessage = Objects.requireNonNull(task.getException()).toString();
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Error: "+errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void InitializeFields() {
        loginButton = findViewById(R.id.login_button);
        emailInput = findViewById(R.id.login_email);
        passwordInput = findViewById(R.id.login_password);
        forgetPasswordLink = findViewById(R.id.forget_password_link);
        registerAccountLink = findViewById(R.id.register_link);

        loginButton.setOnClickListener(this);
        forgetPasswordLink.setOnClickListener(this);
        registerAccountLink.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        isLoggedIn = false;
    }

    public boolean isValidUserPass(String username, String password) {
        boolean isValid;
        if (username.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
        }
        else if (password.isEmpty() || password.length() < 6) {
            Toast.makeText(this, "Please enter a password off at least 6 characters", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else {
            isValid = true;
        }
        return isValid;
    }
}
