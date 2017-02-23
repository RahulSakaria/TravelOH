 package com.example.hp.traveloh;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity implements View.OnClickListener {


    private EditText emailid, password;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private TextView signUp;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            Intent intent = new Intent(this, Home_Page.class);
            startActivity(intent);
        }
        emailid = (EditText) findViewById(R.id.enter_email);
        password = (EditText) findViewById(R.id.enter_password);
        signUp = (TextView) findViewById(R.id.SignUp);
        signUp.setOnClickListener(this);
        login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(this);
    }

    private void loginUser() {
        String email = emailid.getText().toString().trim();
        String passwd = password.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passwd)) {
            Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(LoginActivity.this, Home_Page.class);
                            startActivity(intent);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Login Failed Check Username/Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void sharedPref() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", emailid.getText().toString());
        editor.putString("password", password.getText().toString());
        editor.apply();
    }

    @Override
    public void onClick(View view) {
        if (view == signUp) {
            Intent intent = new Intent(this, UserInputActivity.class);
            startActivity(intent);
        }
        if (view == login) {
            sharedPref();
            loginUser();
        }
    }
}

