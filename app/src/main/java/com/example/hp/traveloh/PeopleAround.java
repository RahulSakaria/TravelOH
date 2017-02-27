package com.example.hp.traveloh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PeopleAround extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    private Firebase mref;
    private String uid;
    private DatabaseReference mref2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_around);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mref2 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://traveloh-fa3fa.firebaseio.com/Travelling Details/"+uid);
        mref = new Firebase("https://traveloh-fa3fa.firebaseio.com/User Details");
        mref2.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    String from = dataSnapshot.child("from").getValue().toString();
                    String mode = dataSnapshot.child("mode").getValue().toString();
                    String time = dataSnapshot.child("time").getValue().toString();
                    String to = dataSnapshot.child("to").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String phonenumber = dataSnapshot.child("number").getValue().toString();

                recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(PeopleAround.this));
                listItems = new ArrayList<>();
                for(int i = 0; i<=10 ; i++){
                    ListItem listItem = new ListItem(name , phonenumber);
                    listItems.add(listItem);
                }
                adapter = new PeopleDescriptionAdapter(listItems,PeopleAround.this);

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
