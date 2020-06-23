package com.example.lifter;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatFragment extends Fragment {
    private View privateChatsView;
    private RecyclerView recyclerView;
    private DatabaseReference chatReference;
    private FirebaseAuth firebaseAuth;
    private String currentUID;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        privateChatsView = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = privateChatsView.findViewById(R.id.chat_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseAuth = FirebaseAuth.getInstance();
        currentUID = firebaseAuth.getCurrentUser().getUid();
        chatReference = FirebaseDatabase.getInstance().getReference().child("Users");

        return privateChatsView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Contacts> options = new FirebaseRecyclerOptions.Builder<Contacts>().setQuery(chatReference, Contacts.class).build();

        FirebaseRecyclerAdapter<Contacts, ChatViewHolder> chatAdapter = new FirebaseRecyclerAdapter<Contacts, ChatViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ChatViewHolder holder, final int position, @NonNull final Contacts model) {
                holder.itemView.findViewById(R.id.friend_request_icon).setVisibility(View.INVISIBLE);

                holder.userName.setText(model.getName());
                holder.userGym.setText(model.getGym());
                Picasso.get().load(model.getProfile_image()).placeholder(R.drawable.default_profile_icon).into(holder.profilePic);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String clickedProfileUID = getRef(position).getKey();
                        Intent messageIntent = new Intent(getContext(), MessageActivity.class);
                        messageIntent.putExtra("message_user_id", clickedProfileUID);
                        messageIntent.putExtra("message_user_name", model.getName());
                        startActivity(messageIntent);
                    }
                });
            }

            @NonNull
            @Override
            public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_display_layout, parent, false);
                ChatFragment.ChatViewHolder holder = new ChatFragment.ChatViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(chatAdapter);
        chatAdapter.startListening();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profilePic;
        TextView userName, userGym;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_name_text);
            userGym = itemView.findViewById(R.id.user_gym_name_text);
            profilePic = itemView.findViewById(R.id.user_image);
        }
    }
}

