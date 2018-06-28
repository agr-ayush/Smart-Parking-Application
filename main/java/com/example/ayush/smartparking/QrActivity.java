package com.example.ayush.smartparking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class QrActivity extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        context = getApplicationContext();
        ImageView imageqr = findViewById(R.id.imageqr);
        imageqr.setImageBitmap(getThumbnail("QRCode.png"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public Bitmap getThumbnail(String filename) {
        Bitmap thumbnail = null;
        if (thumbnail == null) {
            try {
                File filePath = context.getFileStreamPath(filename);
                FileInputStream fi = new FileInputStream(filePath);
                thumbnail = BitmapFactory.decodeStream(fi);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return thumbnail;
    }
}
