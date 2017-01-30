package com.example.hp.traveloh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
    public void LogIn(View view){

        EditText emailid = (EditText) findViewById(R.id.enter_email);
        EditText password = (EditText) findViewById(R.id.enter_password);
        boolean email = emailid.getText().toString().equals("rahulsakaria97@gmail.com");
        boolean pass = password.getText().toString().equals("password");
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

