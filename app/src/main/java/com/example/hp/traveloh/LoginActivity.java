package com.example.hp.traveloh;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);


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

