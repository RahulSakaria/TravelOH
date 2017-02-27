package com.example.hp.traveloh;

/**
 * Created by HP on 26-02-2017.
 */

public class ListItem {

    ListItem() {
    }

    public String mName;
    public String mNumber;

    ListItem(String mName, String mNumber) {
        this.mName = mName;
        this.mNumber = mNumber;
    }


    public String getmNumber() {
        return mNumber;
    }

    public String getmName() {
        return mName;
    }
}
