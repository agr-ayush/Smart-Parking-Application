package com.example.ayush.smartparking;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.io.FileOutputStream;

public class RegisterActivity extends AppCompatActivity {
    public Context context;
    public static EditText name,number,email,vehicle;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private Bitmap bitmap;
    public static String email2;
    public static SharedPreferences.Editor editor;
    public static String name1,email1,contact1,vehicle1;
    public static UserData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = getApplicationContext();

         name = findViewById(R.id.name);
     number = findViewById(R.id.number);
         email = findViewById(R.id.email);
         vehicle = findViewById(R.id.vehicle);

        Button saveandgenerate = findViewById(R.id.savedetails);
        saveandgenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qrtext = name.getText().toString()+"/"+number.getText().toString()+"/"+email.getText().toString()+"/"+vehicle.getText().toString();

                editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                name1 = name.getText().toString();
                email1 = email.getText().toString();
                contact1 = email.getText().toString();
                vehicle1 = vehicle.getText().toString();
                editor.putString("name", name1);
                editor.putString("email", email1);
                editor.putString("contact",contact1);
                editor.putString("vehicle",vehicle1);
                editor.commit();
                Log.i("text",qrtext);
                String a = email.getText().toString();
                String []b = a.split("@");
                email2=b[0];

                data = new UserData(name.getText().toString(),number.getText().toString(),email.getText().toString(),vehicle.getText().toString());
                MainActivity.databasereference.child(b[0]).setValue(data);
                //MainActivity.nametext.setText(RegisterActivity.name1);
                //MainActivity.contacttext.setText(RegisterActivity.contact1);
                WindowManager manager = (WindowManager)getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int smallerside = width < height? width:height;
                smallerside *= (0.75);
                QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrtext,
                        null,
                        Contents.Type.TEXT,
                        BarcodeFormat.QR_CODE.toString(),
                        smallerside);
                try {
                    bitmap = qrCodeEncoder.encodeAsBitmap();
                    //image.setImageBitmap(bitmap);

                } catch (WriterException e) {
                    e.printStackTrace();
                }
                boolean check = saveQR(bitmap);
                if(check){
                    Toast.makeText(RegisterActivity.this,"QR Saved",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Unable to save",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public boolean saveQR(Bitmap image) {

        try {
            FileOutputStream fos = context.openFileOutput("QRCode.png", Context.MODE_PRIVATE);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            return true;
        } catch (Exception e) {
            //Log.e("saveToInternalStorage()", e.getMessage());
            return false;
        }
    }
}
