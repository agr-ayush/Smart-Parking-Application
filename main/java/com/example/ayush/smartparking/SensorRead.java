package com.example.ayush.smartparking;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SensorRead extends AsyncTask<String, String, String> {
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference4;
    public static DatabaseReference databaseReference5;
    public static int i=0;
    @Override
    protected String doInBackground(String... strings) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference4 = firebaseDatabase.getReference().child("sensordata");
        databaseReference5=firebaseDatabase.getReference();
        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot = dataSnapshot.child("result");
                String sensorvalue = dataSnapshot.getValue().toString();
                char[] sensorsvalue=sensorvalue.toCharArray();
                for(i=0;i<sensorsvalue.length;i++)
                {
                   if(Integer.parseInt(""+sensorsvalue[i])==1)
                   {
                       databaseReference5.child("SensorGrid").child(BookingActivity.seats2[i]).child("value").setValue("true");
                       databaseReference5.child("Grid").child(BookingActivity.seats2[i]).child("value").setValue("true");

                   }
                   else
                   {
                       databaseReference5.child("Grid").child(BookingActivity.seats2[i]).child("value").setValue("false");
                   }
                }
                Log.i("Sensor Value",sensorvalue);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
