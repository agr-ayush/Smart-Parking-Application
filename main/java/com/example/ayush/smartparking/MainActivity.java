package com.example.ayush.smartparking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static TextView texttodisplay;
    public static final int RC_SIGN_IN = 1;
    public static FirebaseDatabase firebaseDatabase;
    public static FirebaseDatabase firebaseDatabase2;
    public static DatabaseReference databasereference;
    public static DatabaseReference databaseReference2;
    public static FirebaseAuth firebaseAuth;
    public static FirebaseInstanceId firebaseInstanceId;
    //public static ArrayList<Integer> availablepositions = new ArrayList<>(Arrays.asList(0,0,0,5,1,0,1,2,1,3,1,5,2,0,2,2,2,3,2,5,3,0,3,2,3,3,3,5,4,0,4,5));
    public static TextView nametext,contacttext,timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        SensorRead sensorRead = new SensorRead();
        sensorRead.execute();


        //startService(new Intent(MainActivity.this, ServiceActivity.class));
        firebaseDatabase = FirebaseDatabase.getInstance();
        timer = findViewById(R.id.timer);
        firebaseDatabase2 = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseInstanceId = FirebaseInstanceId.getInstance();
        databasereference = firebaseDatabase.getReference().child("User");
        databaseReference2 = firebaseDatabase2.getReference().child("Grid");
//        for(int i=0;i<availablepositions.size();i+=2){
//            GridSlot g = new GridSlot(availablepositions.get(i),availablepositions.get(i+1),false);
//            databaseReference2.child("slot"+availablepositions.get(i)+""+availablepositions.get(i+1)).setValue(g);
//        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        texttodisplay = findViewById(R.id.display);
        nametext = findViewById(R.id.usertext);
        contacttext = findViewById(R.id.contacttext);


        FirebaseDatabase firebaseDatabase3=FirebaseDatabase.getInstance();



        final DatabaseReference[] databaseReference3 = {firebaseDatabase3.getReference().child("AllocatedUser")};
        databaseReference3[0].addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
                Log.i("path","chosen");
                    String mail = prefs.getString("email", "");
                   // Log.i("mail",mail);
                    if(mail!=""){
                    String[] m = mail.split("@");
                    Log.i("mail",m[0]);
                    // Log.i("", email2[0]);
                    dataSnapshot = dataSnapshot.child(m[0]).child("seat");
                    //Log.i("data: ", dataSnapshot.toString());
                    String value = dataSnapshot.getValue().toString();
                    String[] values = value.split(",");
                    int x = Integer.parseInt(values[0]);
                    int y = Integer.parseInt(values[1]);
                    //Toast.makeText(MainActivity.this, x + "," + y, Toast.LENGTH_SHORT).show();
                    //BookingActivity.time.cancel();
                    Intent i = new Intent(MainActivity.this, PathActivity.class);
                    i.putExtra("X", x + "");
                    i.putExtra("Y", y + "");
                    startActivity(i);}

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        try{
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        RegisterActivity.name1 = prefs.getString("name","");
        RegisterActivity.contact1 = prefs.getString("contact","");
        RegisterActivity.email1 = prefs.getString("email","");
        RegisterActivity.vehicle1 = prefs.getString("vehicle","");
        RegisterActivity.data = new UserData(RegisterActivity.name1,RegisterActivity.contact1,RegisterActivity.email1,RegisterActivity.vehicle1);
        Log.i("data",RegisterActivity.name1);}
        catch(Exception e){ e.printStackTrace();}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN_IN){
            if (resultCode==RESULT_OK){
               // Toast.makeText(MainActivity.this,"Signed in!",Toast.LENGTH_SHORT).show();
            }
            else if(resultCode==RESULT_CANCELED){
                Toast.makeText(MainActivity.this,"Cancelled Signin",Toast.LENGTH_SHORT).show();
                finish();
            }}


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            //stopService(new Intent(MainActivity.this,ServiceActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out:
                AuthUI.getInstance().signOut(MainActivity.this);
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_register) {
           Intent register = new Intent(MainActivity.this,RegisterActivity.class);
           startActivity(register);
        } else if (id == R.id.nav_qrcode) {
            if(RegisterActivity.email1==""){
                Toast.makeText(MainActivity.this, "Please Register", Toast.LENGTH_SHORT).show();
                Intent register = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(register);

            }else{
            Intent qrcode = new Intent(MainActivity.this,QrActivity.class);
            startActivity(qrcode);}

        } else if (id == R.id.nav_booking) {
            Intent book = new Intent(MainActivity.this,BookingActivity.class);
            startActivity(book);

        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
