package com.example.ayush.smartparking;

public class BookedUser {
    String Name;
    String contact;
    String email;
    String vehicle;
    String slot;
    BookedUser(){

    }
    BookedUser(String x,String y,String p,String q,String s){
        Name = x;
        contact = y;
        email = p;
        vehicle = q;
        slot = s;
    }
}
