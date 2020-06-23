package com.example.lifter;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Messages> messageList;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference userRef;

    public MessageAdapter(List<Messages> userMessages) {
        super();
        this.messageList = userMessages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_messages_layout, parent, false);
        firebaseAuth = FirebaseAuth.getInstance();
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, int position) {
        String currentUserID = firebaseAuth.getCurrentUser().getUid();
        Messages messages = messageList.get(position);

        String receiverOfMessage = messages.getFrom();
        String fromMessageType = messages.getType();

        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(receiverOfMessage);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("profile_image")) {
                    String receiverImage = dataSnapshot.child("profile_image").getValue().toString();
                    Picasso.get().load(receiverImage).placeholder(R.drawable.default_profile_icon).into(holder.receiverProfilePic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.receivedMessageText.setVisibility(View.GONE);
        holder.receiverProfilePic.setVisibility(View.GONE);
        holder.sentMessageText.setVisibility(View.GONE);

            if (receiverOfMessage.equals(currentUserID)) {
                holder.sentMessageText.setVisibility(View.VISIBLE);
                holder.sentMessageText.setBackgroundResource(R.drawable.sender_messages_layout);
                holder.sentMessageText.setText(messages.getMessage());
            } else {
                holder.receivedMessageText.setVisibility(View.VISIBLE);
                holder.receiverProfilePic.setVisibility(View.VISIBLE);

                holder.receivedMessageText.setBackgroundResource(R.drawable.receiver_messages_layout);
                holder.receivedMessageText.setText(messages.getMessage());
            }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageViewHolder extends  RecyclerView.ViewHolder {
        public TextView sentMessageText, receivedMessageText;
        public CircleImageView receiverProfilePic;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            sentMessageText = itemView.findViewById(R.id.sender_message_text);
            receivedMessageText = itemView.findViewById(R.id.receiver_message_text);
            receiverProfilePic = itemView.findViewById(R.id.message_profile_image);
        }
    }
}
