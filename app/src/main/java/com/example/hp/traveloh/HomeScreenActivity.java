package com.example.hp.traveloh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class HomeScreenActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private EditText username, location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        firebaseAuth = FirebaseAuth.getInstance();
        username = (EditText) findViewById(R.id.enter_person_name);
        location = (EditText) findViewById(R.id.enter_person_location);
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        TextView userEmail = (TextView) findViewById(R.id.userID);
        userEmail.setText("Welcome " + user.getEmail());
    }

    public void LogOut(View view) {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void saveData(View view) {
        SaveUserInfo();
    }

    private void SaveUserInfo() {
        String mname = username.getText().toString().trim();
        String madd = location.getText().toString().trim();

        UserInformationData userInformationData = new UserInformationData(mname, madd);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInformationData);

        Toast.makeText(this,"User Data Saved",Toast.LENGTH_SHORT).show();
    }

}

