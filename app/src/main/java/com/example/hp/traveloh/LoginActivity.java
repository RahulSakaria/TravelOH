package com.example.hp.traveloh;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(LoginActivity.this, SplashActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);

    }
    public void LogIn(View view){

        EditText emailid = (EditText) findViewById(R.id.enter_email);
        EditText password = (EditText) findViewById(R.id.enter_password);
        boolean email = emailid.getText().toString().equals("rahul");
        boolean pass = password.getText().toString().equals("pass");
        if(email && pass)
        {
            Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,HomeScreenActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,"Enter valid email/password",Toast.LENGTH_SHORT).show();
        }
    }
        public void SignUp(View view){
            Intent intent = new Intent(this,UserInputActivity.class);
            startActivity(intent);
    }
}

