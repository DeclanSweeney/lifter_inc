package com.example.lifter;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Contacts fragment to display added and available contacts
 * Pulls contacts from database list and updates accordingly within
 * the recycler view
 *
 * TODO: Complete this after SPRINT 1 REVIEW
 */
public class ContactsFragment extends Fragment {
    private View contactsFragView;
    private RecyclerView contactsList;
    private DatabaseReference contactsReference, usersRef;
    private FirebaseAuth userAuth;
    private String userUID;
    private Map<String, Object> contactMap;


    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contactsFragView = inflater.inflate(R.layout.fragment_contacts, container, false);

        InitializeFields();

        FloatingActionButton fabAddGymBuddy = contactsFragView.findViewById(R.id.find_gym_buddy_fab);
        fabAddGymBuddy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendToFindGymBuddyActivity();
            }
        });

        return contactsFragView;
    }

    private void SendToFindGymBuddyActivity() {
        Intent intent = new Intent(getContext(), GymBuddyFinderActivity.class);
        startActivity(intent);
    }

    private void InitializeFields() {
        contactsList = (RecyclerView) contactsFragView.findViewById(R.id.contacts_list);
        contactsList.setLayoutManager(new LinearLayoutManager(getContext()));

        userAuth = FirebaseAuth.getInstance();
        userUID = userAuth.getCurrentUser().getUid();
        contactsReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userUID).child("Contacts");
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        contactMap = new HashMap<>();
        PopulateContactMap();
    }

    private void PopulateContactMap() {
        usersRef.child(userUID).child("Contacts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GetContactsList(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

        PopulateContactMap();

        FirebaseRecyclerOptions<Contacts> options = new FirebaseRecyclerOptions.Builder<Contacts>().setQuery(usersRef, Contacts.class).build();

        FirebaseRecyclerAdapter<Contacts, ContactsFragment.ReqViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<Contacts, ContactsFragment.ReqViewHolder>(options) {


            @NonNull
            @Override
            public ReqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_display_layout, parent, false);
                ContactsFragment.ReqViewHolder holder = new ContactsFragment.ReqViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull final ReqViewHolder holder, int position, @NonNull Contacts model) {
                holder.itemView.findViewById(R.id.friend_request_icon).setVisibility(View.INVISIBLE);

                final String list_user_id = getRef(position).getKey();
                DatabaseReference getTypeRef = getRef(position).getRef();

                holder.username.setText(model.getName());
                holder.userGym.setText(model.getGym());
                Picasso.get().load(model.getProfile_image()).placeholder(R.drawable.default_profile_icon).into(holder.profilePic);
            }
        };
        contactsList.setAdapter(recyclerAdapter);
        recyclerAdapter.startListening();
    }


//        FirebaseRecyclerAdapter<Contacts, ContactsFragment.ReqViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<Contacts, ContactsFragment.ReqViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull final ContactsFragment.ReqViewHolder holder, int position, @NonNull Contacts model) {
//                holder.username.setText(model.getName());
//                holder.userGym.setText(model.getGym());
//
////                holder.itemView.findViewById(R.id.friend_request_icon).setVisibility(View.INVISIBLE);
//
////                final String list_user_id = getRef(position).getKey();
////                Log.d("key", list_user_id);
////
////                usersRef.child(list_user_id).addValueEventListener(new ValueEventListener() {
////                    @Override
////                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                        if (dataSnapshot.exists()) {
////                            if (dataSnapshot.hasChild("image")) {
////                                final String reqProfileImage = dataSnapshot.child("profile_image").getValue().toString();
////                                Picasso.get().load(reqProfileImage).into(holder.profilePic);
////                            }
////                            final String reqUserName = dataSnapshot.child("name").getValue().toString();
////                            final String reqUserGym = dataSnapshot.child("gym").getValue().toString();
////                            holder.username.setText(reqUserName);
////                            holder.userGym.setText(reqUserGym);
////                        }
////                    }
////
////                    @Override
////                    public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                    }
////                });
//            }
//
//            @NonNull
//            @Override
//            public ContactsFragment.ReqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_display_layout, parent, false);
//                ContactsFragment.ReqViewHolder holder = new ContactsFragment.ReqViewHolder(view);
//                return holder;
//            }
//        };
//
//        contactsList.setAdapter(recyclerAdapter);
//        recyclerAdapter.startListening();
//    }
//


        private void GetContactsList (DataSnapshot ds){
            Iterator it = ds.getChildren().iterator();
            while (it.hasNext()) {
                String contact_uid = ((DataSnapshot) it.next()).getKey();
                Log.d("Iterator ID", contact_uid);
                assert contact_uid != null;
                if (ds.child(contact_uid).getValue().toString().equals("request_sent")) {
                    contactMap.put(contact_uid, "request_sent");
                }
            }
        }

        public static class ReqViewHolder extends RecyclerView.ViewHolder {
            TextView username, userGym;
            ImageView reqIcon;
            CircleImageView profilePic;

            public ReqViewHolder(@NonNull View itemView) {
                super(itemView);

                username = itemView.findViewById(R.id.user_name_text);
                userGym = itemView.findViewById(R.id.user_gym_name_text);
                reqIcon = itemView.findViewById(R.id.friend_request_icon);
                profilePic = itemView.findViewById(R.id.user_image);
            }
        }
    }
