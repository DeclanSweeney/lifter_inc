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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    private Button loginButton;
    private EditText emailInput, passwordInput;
    private TextView forgetPasswordLink, registerAccountLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InitializeFields();
    }

//    protected void onStart() {
//        super.onStart();
//
//        if (currentUser != null) {
//            SendUserToMain();
//        }
//    }

    private void SendUserToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void SendUserToRegister() {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.login_button:
                UserLogin();
                break;
            case R.id.forget_password_link:
                break;
            case R.id.register_link:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
        }
    }

    private void UserLogin() {
        String userEmail = emailInput.getText().toString().trim();
        String userPassword = passwordInput.getText().toString().trim();

        if (userEmail.isEmpty()) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
        }
        else if (userPassword.isEmpty() || userPassword.length() < 6) {
            Toast.makeText(this, "Please enter a valid password", Toast.LENGTH_SHORT).show();
        }
        else {
//            progressDialog.setTitle("Log In");
//            progressDialog.setMessage("Please wait...\n Logging in");
//            progressDialog.setCanceledOnTouchOutside(true);
//            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        SendUserToMain();
                        Toast.makeText(LoginActivity.this, "Welcome TODO:ADD_NAME", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
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
    }
}
