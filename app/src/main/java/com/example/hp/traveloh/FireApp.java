package com.example.hp.traveloh;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by HP on 27-02-2017.
 */

public class FireApp extends Application{

    public void onCreate(){
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
