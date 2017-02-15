package com.example.hp.traveloh;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeScreenActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private EditText username, lastnames, location, dateofBirth, number;
    private Button saveData;
    private ImageButton calender;
    private int day_x, month_x, year_x;
    static final int DIALOG = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        username = (EditText) findViewById(R.id.enter_person_names);
        location = (EditText) findViewById(R.id.enter_person_location);
        saveData = (Button) findViewById(R.id.saveData);
        lastnames = (EditText) findViewById(R.id.last_name);
        dateofBirth = (EditText) findViewById(R.id.date_of_birth);
        number = (EditText) findViewById(R.id.mobile_number);
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final Calendar calendar = Calendar.getInstance();
        day_x = calendar.get(Calendar.DATE);
        month_x = calendar.get(Calendar.MONTH);
        year_x = calendar.get(Calendar.YEAR);
        ShowDialogOnButtonClick();
        //TextView userEmail = (TextView) findViewById(R.id.userID);
        //userEmail.setText("Welcome " + user.getEmail());
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
        String mlastname = lastnames.getText().toString().trim();
        String madd = location.getText().toString().trim();
        String date = dateofBirth.getText().toString().trim();
        String numbers = number.getText().toString().trim();

        UserInformationData userInformationData = new UserInformationData(mname, mlastname, madd, date, numbers);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInformationData);

        Toast.makeText(HomeScreenActivity.this, "User Data Saved", Toast.LENGTH_SHORT).show();
    }

    public void ShowDialogOnButtonClick() {
        calender = (ImageButton) findViewById(R.id.calender_button);

        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG)
            return new DatePickerDialog(this, datepickerListener, year_x, month_x, day_x);
        return null;
    }

    private DatePickerDialog.OnDateSetListener datepickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year;
            month_x = month + 1;
            day_x = dayOfMonth;

            dateofBirth.setText(day_x + "/" + month_x + "/" + year_x);
        }
    };

}

