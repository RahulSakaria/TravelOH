package com.project.hp.traveloh;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeScreenFragment extends Fragment implements View.OnClickListener {
    private TextView mName;
    private DatabaseReference mref;
    private String uid;
    String name;
    LinearLayout search, message, info;
    private ImageView profileImage, showImage;


    public HomeScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(com.project.hp.traveloh.R.layout.fragment_home_screen, container, false);
        mName = (TextView) view.findViewById(com.project.hp.traveloh.R.id.profile_name_home_screen_fragment);
        profileImage = (ImageView) view.findViewById(com.project.hp.traveloh.R.id.profile_image_home_screen);
        search = (LinearLayout) view.findViewById(com.project.hp.traveloh.R.id.search_linear_layout);
        message = (LinearLayout) view.findViewById(com.project.hp.traveloh.R.id.message_linear_layout);
        info = (LinearLayout) view.findViewById(com.project.hp.traveloh.R.id.info_linear_layout);
        search.setOnClickListener(this);
        message.setOnClickListener(this);
        info.setOnClickListener(this);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mref = FirebaseDatabase.getInstance().getReference();
        mref.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                name = dataSnapshot.child(uid).child("name").getValue().toString();
                mName.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == search) {
            Intent intent = new Intent(getActivity().getApplication(), Home_Page.class);
            startActivity(intent);
        }
        if (view == message) {
            Intent intent = new Intent(getActivity().getApplication(), MessageActivity.class);
            startActivity(intent);
        }
        if (view == info) {
            Toast.makeText(getActivity().getApplication(),"Search People For Sharing Your Travel Expense!",Toast.LENGTH_LONG).show();
        }
    }
}
