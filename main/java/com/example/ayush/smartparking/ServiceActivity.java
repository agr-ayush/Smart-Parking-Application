package com.example.ayush.smartparking;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by AYUSH on 28-03-2018.
 */

public class ServiceActivity extends Service {
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       firebaseDatabase = FirebaseDatabase.getInstance();
       databaseReference = firebaseDatabase.getReference().child("Grid");
       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               String value = dataSnapshot.getValue(String.class);
               Log.i("data", "Value is: " + value);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

}
