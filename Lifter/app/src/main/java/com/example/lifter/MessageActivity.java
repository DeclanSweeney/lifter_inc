package com.example.lifter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {
    private String messageRequestID, messageRequestName;

    private TextView username;
    private CircleImageView profilePic;
    private Toolbar messageToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        messageRequestID = getIntent().getExtras().get("message_user_id").toString();
        messageRequestName = getIntent().getExtras().get("message_user_name").toString();

        InitializeFields();

        username.setText(messageRequestName);
    }

    private void InitializeFields() {
        messageToolbar = (Toolbar) findViewById(R.id.message_toolbar);
        setSupportActionBar(messageToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView = layoutInflater.inflate(R.layout.custom_chat_bar, null);
        actionBar.setCustomView(actionBarView);

        profilePic = findViewById(R.id.custom_profile_image);
        username = findViewById(R.id.custom_profile_name);
    }
}