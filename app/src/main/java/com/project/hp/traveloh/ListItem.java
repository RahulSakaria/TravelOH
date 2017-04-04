package com.project.hp.traveloh;

/**
 * Created by HP on 26-02-2017.
 */

public class ListItem {

    ListItem() {
    }

    public String mName;
    public String mNumber;
    public String mFrom;
    public String mTo;
    public String mDate;
    public String mTime;


    ListItem(String mName, String mNumber, String mFrom, String mTo, String mDate, String mTime) {
        this.mName = mName;
        this.mNumber = mNumber;
        this.mFrom = mFrom;
        this.mTo = mTo;
        this.mDate = mDate;
        this.mTime = mTime;
    }


    public String getmNumber() {
        return mNumber;
    }

    public String getmName() {
        return mName;
    }


    public String getmFrom() {
        return mFrom;
    }

    public String getmTo() {
        return mTo;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmTime() {
        return mTime;
    }
}
