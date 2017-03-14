package com.example.hp.traveloh;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home_Page extends AppCompatActivity implements View.OnClickListener {
    private Button logout, searchtravellers;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private int day_x, month_x, year_x;
    final static int DIALOG = 0;
    private EditText dateoftravel;
    Spinner fromstation, tostation, timerange, modeoftravel;
    String from, to, date, time, mode;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__page);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        logout = (Button) findViewById(R.id.logout_user);
        progressDialog = new ProgressDialog(this);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Toast.makeText(Home_Page.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(Home_Page.this, LoginActivity.class));
            }
        });
        dateoftravel = (EditText) findViewById(R.id.date_of_travel);
        final Calendar calendar = Calendar.getInstance();
        day_x = calendar.get(Calendar.DATE);
        month_x = calendar.get(Calendar.MONTH);
        year_x = calendar.get(Calendar.YEAR);
        ShowDialogOnButtonClick();
        searchtravellers = (Button) findViewById(R.id.search_travellers_button);
        searchtravellers.setOnClickListener(this);
        spinnersInput();


    }

    public void ShowDialogOnButtonClick() {

        dateoftravel.setOnClickListener(new View.OnClickListener() {
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

            dateoftravel.setText(day_x + "/" + month_x + "/" + year_x);
            date = dateoftravel.getText().toString();
        }
    };

    @Override
    public void onClick(View view) {
        if (view == searchtravellers) {
            travelInfo();
            progressDialog.setMessage("Searching People Around You...");
            progressDialog.show();
            startActivity(new Intent(Home_Page.this, PeopleAround.class));
        }
    }

    public void travelInfo() {

        UserTravelData travelData = new UserTravelData(from, to, date, time, mode);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).child("Travelling Details").setValue(travelData);


    }

    public void spinnersInput() {
        fromstation = (Spinner) findViewById(R.id.from_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.from_station, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromstation.setAdapter(adapter);

        fromstation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                from = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        tostation = (Spinner) findViewById(R.id.to_spinner);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.to_station, android.R.layout.simple_spinner_item);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tostation.setAdapter(adapter2);

        tostation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                to = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        timerange = (Spinner) findViewById(R.id.time_spinner);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.travel_time_range, android.R.layout.simple_spinner_item);

        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        timerange.setAdapter(adapter3);

        timerange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                time = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });


        modeoftravel = (Spinner) findViewById(R.id.mode_of_travel_spinner);

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.mode_of_travel, android.R.layout.simple_spinner_item);

        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        modeoftravel.setAdapter(adapter4);

        modeoftravel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mode = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });


    }
}
