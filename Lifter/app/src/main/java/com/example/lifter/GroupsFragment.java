package com.example.lifter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class GroupsFragment extends Fragment {
    private DatabaseReference dbRef;

    private View groupFragmentView;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> listOfGroups = new ArrayList<>();;

    public GroupsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void InitializeFields() {
        dbRef = FirebaseDatabase.getInstance().getReference().child("Groups");

        listView = (ListView)groupFragmentView.findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listOfGroups);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedGroupName = parent.getItemAtPosition(position).toString().trim();

                Intent groupChatIntent = new Intent(getContext(), GroupChatActivity.class);
                groupChatIntent.putExtra("GroupName", clickedGroupName);
                startActivity(groupChatIntent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        groupFragmentView = inflater.inflate(R.layout.fragment_groups, container, false);
        listOfGroups = new ArrayList<>();

        InitializeFields();
        FetchGroups();

        FloatingActionButton fabCreateGroup = (FloatingActionButton) groupFragmentView.findViewById(R.id.create_group_fab);
        fabCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetupNewGroup();
            }
        });

        return groupFragmentView;
    }

    private void FetchGroups() {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator it = dataSnapshot.getChildren().iterator();
                Set<String> groupSet = new HashSet<>();
                while(it.hasNext()) {
                    groupSet.add(((DataSnapshot)it.next()).getKey());
                }

                listOfGroups.clear();
                listOfGroups.addAll(groupSet);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void SetupNewGroup() {
        final AlertDialog.Builder createGroupDialog = new AlertDialog.Builder(groupFragmentView.getContext());
        createGroupDialog.setTitle("New Group Name: ");
        final EditText groupNameInput = new EditText((groupFragmentView.getContext()));
        groupNameInput.setHint("<Group Name>");
        createGroupDialog.setView(groupNameInput);

        createGroupDialog.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String groupName = groupNameInput.getText().toString().trim();
                if (groupName.isEmpty()) {
                    Toast.makeText(groupFragmentView.getContext(), "Please enter a valid group name", Toast.LENGTH_SHORT).show();
                } else {
                    CreateNewGroup(groupName);
                }
            }
        });
        createGroupDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        createGroupDialog.show();
    }

    private void CreateNewGroup(String name) {
        dbRef.child(name).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(groupFragmentView.getContext(), "Group Created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(groupFragmentView.getContext(), "Error: "+task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
