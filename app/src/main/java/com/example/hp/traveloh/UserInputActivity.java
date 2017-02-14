package com.example.hp.traveloh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


public class UserInputActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText emailID, password, retyprPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Button createAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        emailID = (EditText) findViewById(R.id.enter_person_emailid);
        password = (EditText) findViewById(R.id.enter_person_password);
        retyprPassword = (EditText) findViewById(R.id.retypepassword);
        createAccount = (Button) findViewById(R.id.createAccount);
        createAccount.setOnClickListener(this);
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
            progressDialog.setMessage("Registering");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, passwd)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(UserInputActivity.this, "Registered Successful", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(UserInputActivity.this, HomeScreenActivity.class);
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




}
