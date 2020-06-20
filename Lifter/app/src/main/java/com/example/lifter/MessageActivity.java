package com.example.lifter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {
    private String messageRequestID, messageRequestName, senderID;

    private TextView username;
    private CircleImageView profilePic;
    private Toolbar messageToolbar;
    private Button sendButton;
    private EditText messageToSend;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference rootRef;

    private final List<Messages> messagesList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        messageRequestID = getIntent().getExtras().get("message_user_id").toString();
        messageRequestName = getIntent().getExtras().get("message_user_name").toString();

        InitializeFields();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage();
            }
        });
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

        sendButton = findViewById(R.id.send_message_button);
        messageToSend = findViewById(R.id.input_message);

        firebaseAuth = FirebaseAuth.getInstance();
        senderID = firebaseAuth.getCurrentUser().getUid();

        rootRef = FirebaseDatabase.getInstance().getReference();

        messageAdapter = new MessageAdapter(messagesList);
        recyclerView = findViewById(R.id.private_messages_list);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messageAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        rootRef.child("Messages").child(senderID).child(messageRequestID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Messages messages = dataSnapshot.getValue(Messages.class);
                messagesList.add(messages);
                messageAdapter.notifyDataSetChanged();

                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void SendMessage() {
        String message = messageToSend.getText().toString().trim();
        if (message.isEmpty()) {
            Toast.makeText(this, "Please enter a message before sending", Toast.LENGTH_SHORT).show();
        } else {
            String messageSenderRef = "Messages/"+senderID+"/"+messageRequestID;
            String messageReceiverRef = "Messages/"+messageRequestID+"/"+senderID;

            DatabaseReference messageKey = rootRef.child("Messages").child(messageSenderRef).child(messageReceiverRef).push();

            String messagePushID = messageKey.getKey();
            Map messageBody = new HashMap();
            messageBody.put("message", message);
            messageBody.put("type", message);
            messageBody.put("from", senderID);

            Map messageBodyDetails = new HashMap();
            messageBodyDetails.put(messageSenderRef+"/"+messagePushID, messageBody);
            messageBodyDetails.put(messageReceiverRef+"/"+messagePushID, messageBody);
            
            rootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MessageActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MessageActivity.this, "Message failed to send", Toast.LENGTH_SHORT).show();
                    }
                    messageToSend.setText("");
                }
            });
        }
    }
}