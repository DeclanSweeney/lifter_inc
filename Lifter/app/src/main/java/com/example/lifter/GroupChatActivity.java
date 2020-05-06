package com.example.lifter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class GroupChatActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button  sendGroupMessageButton;
    private EditText groupMessageInput;
    private ScrollView groupMessageScrollView;
    private TextView displayGroupMessage;
    private String groupName, uid, username, gymName, time, date;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference, groupNameRef, groupMessageKeyReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        InitializeFields();
        PullUserData();

        sendGroupMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = groupMessageInput.getText().toString().trim();
                String messageKey = databaseReference.child("Groups").push().getKey();

                if (!message.isEmpty()) {
                    GetTimeDate();
                    PushMessageToDatabase(message, messageKey);
                    groupMessageScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        groupNameRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    UpdateMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    UpdateMessages(dataSnapshot);
                }
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

    private void UpdateMessages(DataSnapshot ds) {
        Iterator it = ds.getChildren().iterator();

        while (it.hasNext()) {
            String chatDate = ((DataSnapshot)it.next()).getValue().toString();
            String chatMessage = ((DataSnapshot)it.next()).getValue().toString();
            String chatName = ((DataSnapshot)it.next()).getValue().toString();
            String chatTime = ((DataSnapshot)it.next()).getValue().toString();

            displayGroupMessage.append(chatName +": \n"+chatMessage+"\n"+chatTime+", "+chatDate+"\n\n");
        }

        groupMessageScrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    private void PushMessageToDatabase(String msg, String msgKey) {
        HashMap<String, Object> groupMessageMap = new HashMap<>();
        databaseReference.child("Groups").updateChildren(groupMessageMap);
        groupMessageKeyReference = groupNameRef.child(msgKey);

        HashMap<String, Object> messageInfoMap = new HashMap<>();
        messageInfoMap.put("name", username);
        messageInfoMap.put("message", msg);
        messageInfoMap.put("date", date);
        messageInfoMap.put("time", time);
        groupMessageKeyReference.updateChildren(messageInfoMap);

        groupMessageInput.setText("");
    }

    private void GetTimeDate() {
        Calendar calDate = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E dd MMM, yyyy");
        date = simpleDateFormat.format(calDate.getTime());

        Calendar calTime = Calendar.getInstance();
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm a");
        time = simpleTimeFormat.format(calTime.getTime());
    }


    private void InitializeFields() {
        toolbar = findViewById(R.id.group_chat_bar_layout);
        setSupportActionBar(toolbar);

        sendGroupMessageButton = findViewById(R.id.send_group_message_button);
        groupMessageInput = findViewById(R.id.group_message_input);
        groupMessageScrollView = findViewById(R.id.group_chat_scroll_view);
        displayGroupMessage = findViewById(R.id.group_chat_text);

        groupName = getIntent().getExtras().get("GroupName").toString();
        getSupportActionBar().setTitle(groupName);

        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        groupNameRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(groupName);
    }

    private void PullUserData() {
        databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    username = dataSnapshot.child("name").getValue().toString().trim();
                    gymName = dataSnapshot.child("gym").toString().trim();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
