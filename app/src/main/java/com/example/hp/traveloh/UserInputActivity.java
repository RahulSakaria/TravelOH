package com.example.hp.traveloh;

import android.app.ProgressDialog;
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


public class UserInputActivity extends AppCompatActivity {
    private EditText emailID, password;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        emailID = (EditText) findViewById(R.id.enter_person_emailid);
        password = (EditText) findViewById(R.id.enter_person_password);

    }

    public void CreateAccount(View view) {
        registerUser();
    }

    public void registerUser() {
        String email = emailID.getText().toString().trim();
        String passwd = password.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(passwd)) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        }

        progressDialog.setMessage("Registering");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserInputActivity.this, "Registered Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UserInputActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }

                    }
                });
    }


}
