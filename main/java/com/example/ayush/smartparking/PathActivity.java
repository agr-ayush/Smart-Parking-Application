package com.example.ayush.smartparking;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PathActivity extends AppCompatActivity {
    public static String x,y;
    public static int x1,y1;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static DatabaseReference databaseReference1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
        TextView textView = findViewById(R.id.timing);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Grid");
        databaseReference1 = firebaseDatabase.getReference().child("SensorGrid");
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                textView.setText("Time to park:"+ l/1000);
            }

            @Override
            public void onFinish() {
                databaseReference1.child("slot"+x+""+y).child("value");
                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String get = dataSnapshot.getValue().toString();
                        if(get=="false"){
                            Toast.makeText(PathActivity.this, "You didn't reach!!", Toast.LENGTH_SHORT).show();
                            databaseReference.child("slot"+x+""+y).setValue("false");
                            finish();
                        }
                        else{
                            Toast.makeText(PathActivity.this, "Reached!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        }.start();

        Intent i = getIntent();
        x = i.getStringExtra("X");
        y = i.getStringExtra("Y");
        //Log.i("x",x);
        x1 = Integer.parseInt(x);
        y1 = Integer.parseInt(y);
        //Log.i("test","Hello");
        PathClass p = new PathClass(x1,y1);
        MyView myView = findViewById(R.id.canavas);
        myView.drawpath();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
