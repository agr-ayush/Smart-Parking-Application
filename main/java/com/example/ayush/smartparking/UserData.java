package com.example.ayush.smartparking;

/**
 * Created by AYUSH on 27-03-2018.
 */

public class UserData {
    String Name;
    String contact;
    String email;
    String vehicle;
    UserData(){

    }
    UserData(String x,String y,String p,String q){
        Name = x;
        contact = y;
        email = p;
        vehicle = q;
    }
}
