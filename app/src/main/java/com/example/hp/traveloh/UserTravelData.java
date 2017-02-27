package com.example.hp.traveloh;

/**
 * Created by HP on 26-02-2017.
 */

public class UserTravelData {

    UserTravelData() {
    }

    public String from, to, date, time, mode;

    UserTravelData(String from, String to, String date, String time, String mode) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.mode = mode;
    }
}
