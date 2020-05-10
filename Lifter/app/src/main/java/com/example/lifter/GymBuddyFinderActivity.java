package com.example.lifter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import de.hdodenhof.circleimageview.CircleImageView;

public class GymBuddyFinderActivity extends AppCompatActivity {
    private Toolbar gymBuddyFinderToolbar;
    private RecyclerView gymBuddyFinderRV;
    private DatabaseReference databaseReference;
    private FirebaseAuth userAuth;
    private String userUID;
    private HashMap<String, Object> requestedContactMap, acceptedContactMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_buddy_finder);

        InitializeFields();
    }

    private void InitializeFields() {
        gymBuddyFinderToolbar = findViewById(R.id.gym_buddy_finder_toolbar);

        gymBuddyFinderRV = findViewById(R.id.gym_buddy_finder_rv);
        gymBuddyFinderRV.setLayoutManager(new LinearLayoutManager(this));

        setSupportActionBar(gymBuddyFinderToolbar);
        getSupportActionBar().setTitle("Find a Buddy:");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Contacts");
        userAuth = FirebaseAuth.getInstance();
        userUID = userAuth.getCurrentUser().getUid();

        requestedContactMap = new HashMap<>();
        acceptedContactMap = new HashMap<>();
    }

    private void GetContactLists(DataSnapshot ds) {
        Iterator it = ds.child("requests").getChildren().iterator();
        while (it.hasNext()) {
            String contact_uid = ((DataSnapshot)it.next()).getKey();
            requestedContactMap.put(contact_uid, "uid");
        }

        it = ds.child("accepted").getChildren().iterator();
        while (it.hasNext()) {
            String contact_uid = ((DataSnapshot)it.next()).getKey();
            acceptedContactMap.put(contact_uid, "uid");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                GetContactLists(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                GetContactLists(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                GetContactLists(dataSnapshot);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        FirebaseRecyclerOptions<Contacts> contactsOptions = new FirebaseRecyclerOptions.Builder<Contacts>()
                .setQuery(databaseReference, Contacts.class).build();

        FirebaseRecyclerAdapter<Contacts, FindGymBuddyViewHolder> gymBuddyAdapter = new FirebaseRecyclerAdapter<Contacts, FindGymBuddyViewHolder>(contactsOptions) {
            @Override
            protected void onBindViewHolder(@NonNull FindGymBuddyViewHolder holder, final int position, @NonNull Contacts model) {
                holder.username.setText(model.getName());
                holder.userGym.setText(model.getGym());
                Picasso.get().load(model.getProfile_image()).placeholder(R.drawable.place_holder).into(holder.userProfileImage);
                if (model.getUid().equals(userUID)) {
                    holder.HideAddIcon();
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String clickedProfileUID = getRef(position).getKey();
                        if (clickedProfileUID.equals(userUID)) {
                            Toast.makeText(GymBuddyFinderActivity.this, "Can't click your own", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(GymBuddyFinderActivity.this, clickedProfileUID, Toast.LENGTH_SHORT).show();
                            AttemptAddContact(clickedProfileUID);
                            Log.d("Onclick", "userUID: "+userUID);
                            Log.d("Onclick", "clickedUID: "+clickedProfileUID);
                        }
                    }
                });
            }

            @NonNull
            @Override
            public FindGymBuddyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_display_layout, parent, false);
                FindGymBuddyViewHolder viewHolder = new FindGymBuddyViewHolder(view);
                return viewHolder;
            }
        };

        gymBuddyFinderRV.setAdapter(gymBuddyAdapter);

        gymBuddyAdapter.startListening();
    }

    private void AttemptAddContact(String clickedUID) {
        if (requestedContactMap.containsValue(clickedUID)) {
            Toast.makeText(this, "Cancelled Request", Toast.LENGTH_SHORT).show();
        } else  if (acceptedContactMap.containsValue(clickedUID)) {
            Toast.makeText(this, "This person is already a friend", Toast.LENGTH_SHORT).show();
        } else {
            requestedContactMap.put(clickedUID, "uid");
            databaseReference.child("requests").updateChildren(requestedContactMap);
        }
    }

    public static class FindGymBuddyViewHolder extends RecyclerView.ViewHolder {
        TextView username, userGym;
        CircleImageView userProfileImage;
        ImageView friendRequestIcon;
        String uid;

        public FindGymBuddyViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.user_name_text);
            userGym = itemView.findViewById(R.id.user_gym_name_text);
            userProfileImage = itemView.findViewById(R.id.user_image);
            friendRequestIcon = itemView.findViewById(R.id.friend_request_icon);
        }

        public void HideAddIcon() {
            friendRequestIcon.setVisibility(View.INVISIBLE);
        }
    }
}
