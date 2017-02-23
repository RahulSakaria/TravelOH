package com.example.hp.traveloh;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UserInputActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText emailID, password, retyprPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Button createAccount;
    private DatabaseReference databaseReference;
    private EditText fullname, dateofBirth, gender, number;
    private int day_x, month_x, year_x;
    static final int DIALOG = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        emailID = (EditText) findViewById(R.id.enter_person_emailid);
        password = (EditText) findViewById(R.id.enter_person_password);
        retyprPassword = (EditText) findViewById(R.id.retypepassword);
        createAccount = (Button) findViewById(R.id.createAccount);
        createAccount.setOnClickListener(this);
        fullname = (EditText) findViewById(R.id.full_name_enter);
        dateofBirth = (EditText) findViewById(R.id.date_of_birth);
        gender = (EditText) findViewById(R.id.enter_gender_register);
        number = (EditText) findViewById(R.id.phone_number_register);
        dateofBirth.setOnClickListener(this);
        final Calendar calendar = Calendar.getInstance();
        day_x = calendar.get(Calendar.DATE);
        month_x = calendar.get(Calendar.MONTH);
        year_x = calendar.get(Calendar.YEAR);
        ShowDialogOnButtonClick();
    }

    @Override
    public void onClick(View view) {
        if (view == createAccount) {
            registerUser();

        }
    }

    private void registerUser() {

        String email = emailID.getText().toString().trim();
        String passwd = password.getText().toString().trim();
        String retypepwd = retyprPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passwd)) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(retypepwd)) {
            Toast.makeText(this, "Please Retype Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwd.equals(retypepwd)) {
            progressDialog.setMessage("Registering...");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, passwd)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                SaveUserInfo();
                                progressDialog.dismiss();
                                Toast.makeText(UserInputActivity.this, "Registered Successful", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(UserInputActivity.this, Home_Page.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(UserInputActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    });
        } else {
            progressDialog.dismiss();
            Toast.makeText(UserInputActivity.this, "Retype Correct Password", Toast.LENGTH_SHORT).show();
        }
    }

    private void SaveUserInfo() {
        String mname = fullname.getText().toString().trim();
        String genders = gender.getText().toString().trim();
        String emails = emailID.getText().toString().trim();
        String dates = dateofBirth.getText().toString().trim();
        String numbers = number.getText().toString().trim();

        UserInformationData userInformationData = new UserInformationData(mname, genders, emails, dates, numbers);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInformationData);

        Toast.makeText(UserInputActivity.this, "User Data Saved", Toast.LENGTH_SHORT).show();
    }


    public void ShowDialogOnButtonClick() {

        dateofBirth.setOnClickListener(new View.OnClickListener() {
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
