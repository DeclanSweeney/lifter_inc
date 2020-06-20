package com.example.lifter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MessageActivity extends AppCompatActivity {
    private String messageRequestID, messageRequestName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        messageRequestID = getIntent().getExtras().get("message_user_id").toString();
        messageRequestName = getIntent().getExtras().get("message_user_name").toString();

        Toast.makeText(this, messageRequestID, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, messageRequestName, Toast.LENGTH_SHORT).show();

    }
}