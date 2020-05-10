package com.example.lifter;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {
    private RecyclerView rvContacts;
    private View contactsFragmentView;

    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contactsFragmentView = inflater.inflate(R.layout.fragment_contacts, container, false);

        InitializeFields();

        FloatingActionButton fabAddGymBuddy = contactsFragmentView.findViewById(R.id.find_gym_buddy_fab);
        fabAddGymBuddy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendToFindGymBuddyActivity();
            }
        });

        return contactsFragmentView;
    }

    private void SendToFindGymBuddyActivity() {
        Intent intent = new Intent(getContext(), GymBuddyFinderActivity.class);
        startActivity(intent);
    }

    private void InitializeFields() {
        rvContacts = contactsFragmentView.findViewById(R.id.contacts_recycler_view);
//        rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
