package com.example.ayush.smartparking;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class BookingActivity extends AppCompatActivity {
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databasereference;
    public static DatabaseReference databaseReference2;
    public static DatabaseReference databaserefernce3;
    public static CountDownTimer time;
    TextView[]p = new TextView[16];
    LinearLayout[]l = new LinearLayout[16];
    Button bookbutton;
    public static int q=0;
    public static int []slot = {R.id.slotp5,R.id.slotp6,R.id.slotp4,R.id.slotp11,R.id.slotp12,R.id.slotp7,R.id.slotp3,R.id.slotp13,R.id.slotp14,R.id.slotp8,
            R.id.slotp2,R.id.slotp15,R.id.slotp16,R.id.slotp9,R.id.slotp1,R.id.slotp10,};
    public static int []lay = {R.id.layout5,R.id.layout6,R.id.layout4,R.id.layout11,R.id.layout12,R.id.layout7,R.id.layout3,R.id.layout13,R.id.layout14,R.id.layout8,
            R.id.layout2,R.id.layout15,R.id.layout16,R.id.layout9,R.id.layout1,R.id.layout10,};

    public static int []slot1 = {R.id.slotp1,R.id.slotp2,R.id.slotp3,R.id.slotp4,R.id.slotp5,R.id.slotp6,R.id.slotp7,R.id.slotp8,R.id.slotp9,R.id.slotp10,
            R.id.slotp11,R.id.slotp12,R.id.slotp13,R.id.slotp14,R.id.slotp15,R.id.slotp16,};
    public static int []lay1 = {R.id.layout1,R.id.layout2,R.id.layout3,R.id.layout4,R.id.layout5,R.id.layout6,R.id.layout7,R.id.layout8,R.id.layout9,R.id.layout10,
            R.id.layout11,R.id.layout12,R.id.layout13,R.id.layout14,R.id.layout15,R.id.layout16,};



     public static int []flag = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    //public static ArrayList<Integer> pos = new ArrayList<>(Arrays.asList(0,0,0,5,1,0,1,2,1,3,1,5,2,0,2,2,2,3,2,5,3,0,3,2,3,3,3,5,4,0,4,5));
      public static ArrayList<Integer> pos = new ArrayList<>(Arrays.asList(4,0,3,0,2,0,1,0,0,0,0,5,1,5,2,5,3,5,4,5,1,2,1,3,2,2,2,3,3,2,3,3));
      public static String seats2[]={"slot40","slot30","slot20","slot10","slot00","slot05","slot15","slot25","slot35","slot45","slot12","slot13","slot22","slot23","slot32","slot33"};
    int selectedseat = 0;
    final String []seats = {"P1","P2","P3","P4","P5","P6","P7","P8","P9","P10","P11","P12","P13","P14","P15","P16"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        bookbutton = findViewById(R.id.buttonbook);

        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute();


        final int[] selected = {0};
        for(int i=0;i<16;i++){
            p[i] = findViewById(slot1[i]);
            l[i] = findViewById(lay1[i]);
            final int finalI = i;
            p[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(flag[finalI]==1&&selected[0]==1){
                        l[finalI].setBackgroundResource(R.drawable.shape);
                        selected[0]=0;
                        flag[finalI]=0;
                    }
                    else if(selected[0] ==0&&flag[finalI]==0){
                        l[finalI].setBackgroundResource(R.drawable.shape1);
                        selected[0] =1;
                        flag[finalI]=1;
                        selectedseat = finalI;

                    }
                    else{
                        Toast.makeText(BookingActivity.this,"One selection Allowed",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        final String[] value = {"false","false","false","false","false","false","false","false","false","false","false","false","false","false","false","false"};
        firebaseDatabase = FirebaseDatabase.getInstance();
        databasereference = firebaseDatabase.getReference();

        databaserefernce3 = firebaseDatabase.getReference().child("BookedUser");
        databasereference.child("Grid").child("slot"+pos.get(selectedseat*2)+""+pos.get(selectedseat*2+1)).child("value").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value[selectedseat] = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
       // Log.i("value",value[selectedseat]+"");
        bookbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(value[selectedseat] =="true"){
                    Toast.makeText(BookingActivity.this,"Already Booked",Toast.LENGTH_SHORT).show();
                    l[selectedseat].setBackgroundResource(R.drawable.shape2);
                    selected[0] =0;

                }else{
                    databasereference.child("Grid").child("slot"+pos.get(selectedseat*2)+""+pos.get(selectedseat*2+1)).child("value").setValue("true");
                    Toast.makeText(BookingActivity.this,"Slot Booked",Toast.LENGTH_SHORT).show();
                    l[selectedseat].setBackgroundResource(R.drawable.shape);
                    MainActivity.texttodisplay.setText("Your Slot is: "+ seats[selectedseat]);
                    value[selectedseat]="true";
                    Log.i("booking",RegisterActivity.email1.split("@")[0]);
                    BookedUser bu = new BookedUser(RegisterActivity.data.Name,RegisterActivity.data.contact,RegisterActivity.data.email,RegisterActivity.data.vehicle,pos.get(selectedseat*2)+","+pos.get(selectedseat*2+1));
                    databaserefernce3.child(RegisterActivity.email1.split("@")[0]).setValue(bu);
                    CountDownTimer time = new CountDownTimer(120000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            MainActivity.timer.setText("Slot Time: "  + millisUntilFinished / 1000);
                            try {

                                BookingActivity.databasereference.child("AllocatedUser").child(RegisterActivity.email1.split("@")[0]);
                                BookingActivity.time.cancel();

                            }
                            catch(Exception e)
                            {

                            }
                            //here you can have your logic to set text to edittext
                        }

                        public void onFinish() {
                           // mTextField.setText("done!");
                            Toast.makeText(BookingActivity.this, "Time Has Expired!! Plz Book again!", Toast.LENGTH_SHORT).show();
                            BookingActivity.databaserefernce3.child(RegisterActivity.email1.split("@")[0]).removeValue();

                            BookingActivity.databasereference.child("Grid").child("slot"+pos.get(selectedseat*2)+""+pos.get(selectedseat*2+1)).child("value").setValue("false");
                            Intent i =new Intent(BookingActivity.this,BookingActivity.class);
                            startActivity(i);
                        }

                    }.start();
                    finish();

                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference2 = firebaseDatabase.getReference().child("Grid");
            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int count = 0;
                    for (DataSnapshot s : dataSnapshot.getChildren()) {
                        for (DataSnapshot f : s.getChildren()) {
                            if (f.getKey().equals("value")) {
                                if (f.getValue().toString().equals("true")) {
                                    findViewById(BookingActivity.lay[count]).setBackgroundResource(R.drawable.shape2);
                                    Log.i("hello",""+count);
                                } else {
                                    findViewById(BookingActivity.lay[count]).setBackgroundResource(R.drawable.shape);

                                }
                            }

                        }
                        count++;

                    }

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
            return "";
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation

        }


        @Override
        protected void onPreExecute() {


        }


        @Override
        protected void onProgressUpdate(String... text) {
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute();

    }





}


