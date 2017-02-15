package com.example.hp.traveloh;


/**
 * Created by HP on 14-02-2017.
 */

public class UserInformationData {

    public UserInformationData() {
    }

    public String name;
    public String location;
    public String lastname;
    public String dob;
    public String number;

    public UserInformationData(String name, String lastname, String location, String dob, String number) {
        this.name = name;
        this.lastname = lastname;
        this.location = location;
        this.dob = dob;
        this.number = number;
    }


}



