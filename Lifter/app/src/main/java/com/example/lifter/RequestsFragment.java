//package com.example.lifter;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.squareup.picasso.Picasso;
//
//import java.util.HashMap;
//import java.util.Iterator;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class RequestsFragment extends Fragment {
//    private View requestsFragmentView;
//    private RecyclerView requestsRecyclerView;
//    private DatabaseReference databaseReference, contactsReference;
//    private FirebaseAuth userAuth;
//    private String userUID;
//    private HashMap<String, Object> receivedRequestsMap, acceptedContactMap;
//
//    public RequestsFragment() {
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        requestsFragmentView = inflater.inflate(R.layout.fragment_requests, container, false);
//        requestsRecyclerView = requestsFragmentView.findViewById(R.id.gym_buddy_requests_rv);
//        InitializeFields();
//
//        requestsRecyclerView.setAdapter(buddyRequestsAdapter);
//        buddyRequestsAdapter.startListening();
//
//        return requestsFragmentView;
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        databaseReference.child(userUID).child("Requests").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                GetRequestLists(dataSnapshot);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void InitializeFields() {
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
//        contactsReference = FirebaseDatabase.getInstance().getReference();
//        receivedRequestsMap = new HashMap<>();
//        acceptedContactMap = new HashMap<>();
//        userAuth = FirebaseAuth.getInstance();
//        userUID = userAuth.getCurrentUser().getUid();
//    }
//
//    private void GetRequestLists(DataSnapshot ds) {
//        Iterator it = ds.getChildren().iterator();
//        while (it.hasNext()) {
//            String contact_uid = ((DataSnapshot)it.next()).getKey();
//            assert contact_uid != null;
//            if (ds.child(contact_uid).getValue().toString().equals("request_pending"))
//                receivedRequestsMap.put(contact_uid, "request_pending");
//            else
//                acceptedContactMap.put(contact_uid, "accepted");
//        }
//    }
//
//    FirebaseRecyclerOptions<Contacts> contactsOptions = new FirebaseRecyclerOptions.Builder<Contacts>()
//            .setQuery(databaseReference, Contacts.class).build();
//
//    final FirebaseRecyclerAdapter<Contacts, RequestsFragment.BuddyRequestsViewHolder> buddyRequestsAdapter = new FirebaseRecyclerAdapter<Contacts, RequestsFragment.BuddyRequestsViewHolder>(contactsOptions) {
//        @Override
//        protected void onBindViewHolder(@NonNull RequestsFragment.BuddyRequestsViewHolder holder, final int position, @NonNull Contacts model) {
//            if (receivedRequestsMap.containsKey(model.getUid())) {
//                holder.username.setText(model.getName());
//                holder.userGym.setText(model.getGym());
//                Picasso.get().load(model.getProfile_image()).placeholder(R.drawable.place_holder).into(holder.userProfileImage);
////                 || acceptedContactMap.containsKey(model.getUid())
//                if ((model.getUid() != null && model.getUid().equals(userUID))) {
//                    holder.HideAddIcon();
//                } else if (receivedRequestsMap.containsKey(model.getUid())) {
//                    holder.SentRequestIcon();
//                } else {
//                    holder.AddFriendIconVisible();
//                }
//            }
//
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String clickedProfileUID = getRef(position).getKey();
//                    if (clickedProfileUID.equals(userUID)) {
//                        Toast.makeText(getContext(), "Can't click your own", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getContext(), clickedProfileUID, Toast.LENGTH_SHORT).show();
////                        AttemptAddContact(clickedProfileUID);
//                        notifyDataSetChanged();
//                        Log.d("Onclick", "userUID: "+userUID);
//                        Log.d("Onclick", "clickedUID: "+clickedProfileUID);
//                    }
//                }
//            });
//        }
//
//        @NonNull
//        @Override
//        public RequestsFragment.BuddyRequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_display_layout, parent, false);
//            RequestsFragment.BuddyRequestsViewHolder viewHolder = new RequestsFragment.BuddyRequestsViewHolder(view);
//            return viewHolder;
//        }
//    };
//
//    public static class BuddyRequestsViewHolder extends RecyclerView.ViewHolder {
//        TextView username, userGym;
//        CircleImageView userProfileImage;
//        ImageView friendRequestIcon;
//
//        public BuddyRequestsViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            username = itemView.findViewById(R.id.user_name_text);
//            userGym = itemView.findViewById(R.id.user_gym_name_text);
//            userProfileImage = itemView.findViewById(R.id.user_image);
//            friendRequestIcon = itemView.findViewById(R.id.friend_request_icon);
//        }
//
//        public void HideAddIcon() {
//            //TODO: FIX THIS ASAP
//            //friendRequestIcon.setVisibility(View.INVISIBLE);
//        }
//        public void SentRequestIcon() {
//            friendRequestIcon.setImageResource(R.drawable.friend_request_sent_icon);
//        }
//
//        public void AddFriendIconVisible() {
//            friendRequestIcon.setImageResource(R.drawable.add_friend_icon);
//        }
//    }
//}
