package com.example.hp.traveloh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
    private String uid;
    private DatabaseReference mref;
    private String date, from, mode, time, to, userid, name, number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_around);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://traveloh-fa3fa.firebaseio.com/");
        mref.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                date = dataSnapshot.child(uid).child("Travelling Details").child("date").getValue().toString();
                from = dataSnapshot.child(uid).child("Travelling Details").child("from").getValue().toString();
                to = dataSnapshot.child(uid).child("Travelling Details").child("to").getValue().toString();
                mode = dataSnapshot.child(uid).child("Travelling Details").child("mode").getValue().toString();
                time = dataSnapshot.child(uid).child("Travelling Details").child("time").getValue().toString();
                name = dataSnapshot.child(uid).child("name").getValue().toString();
                number = dataSnapshot.child(uid).child("number").getValue().toString();
                listItems = new ArrayList<ListItem>();
                for (com.google.firebase.database.DataSnapshot dsp : dataSnapshot.getChildren()) {
                    traveldet single = dsp.child("Travelling Details").getValue(traveldet.class);
                    userdet singles = dsp.getValue(userdet.class);
                    if (single.getFrom().equals(from) && single.getTo().equals(to) && single.getTime().equals(time) && single.getDate().equals(date)) {
                        Log.e("TESTING", "onDataChange: " + single.getFrom());
                        ListItem listItem = new ListItem(name, number, single.getFrom() + "  -", single.getTo(), single.getDate(), single.getTime());
                        listItems.add(listItem);
                    }

                }

                recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(PeopleAround.this));
                adapter = new PeopleDescriptionAdapter(listItems, PeopleAround.this);

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static class userdet {

        String name;
        String dob;
        String gender;
        String email;
        String phone_number;

        public userdet() {
        }

        public userdet(String name, String dob, String gender, String email, String phone_number) {
            this.name = name;
            this.dob = dob;
            this.gender = gender;
            this.email = email;
            this.phone_number = phone_number;
        }

        public String getName() {
            return name;
        }

        public String getDob() {
            return dob;
        }

        public String getGender() {
            return gender;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone_number() {
            return phone_number;
        }
    }

    public static class traveldet {
        String from;
        String to;
        String time;
        String mode;
        String date;

        public traveldet() {
        }


        public traveldet(String from, String to, String time, String mode, String date) {
            this.from = from;
            this.to = to;
            this.time = time;
            this.mode = mode;
            this.date = date;

        }

        public String getTime() {
            return time;
        }

        public String getMode() {
            return mode;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }

        public String getDate() {
            return date;
        }
    }
}
