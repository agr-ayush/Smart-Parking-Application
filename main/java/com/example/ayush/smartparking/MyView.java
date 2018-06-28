package com.example.ayush.smartparking;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class MyView extends View {
    public static Canvas mCanvas;
    public Paint paint1,paint2,paint3;
    public Path path;
    public int x,y,start,end;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint1 = new Paint();
        paint2 = new Paint();
        paint3 = new Paint();
        path = new Path();
    }

    void drawpath() {

        paint1.setColor(Color.BLUE);
        paint2.setColor(Color.GREEN);
        paint3.setColor(Color.RED);
        paint1.setStrokeWidth(20);
        paint1.setStyle(Paint.Style.STROKE);
        path.moveTo(PathClass.points.get(0+1)*50+40, PathClass.points.get(0)*50+40);
        //Log.i("arraylist",PathClass.points.toString());
        for(int i=0;i<PathClass.points.size();i+=2){
            path.lineTo(PathClass.points.get(i+1)*50+40, PathClass.points.get(i)*50+40);
            x=PathClass.points.get(i+1)*50+40;
            y=PathClass.points.get(i)*50+40;
            //Log.i("xy",x+","+y);
        }
        start = PathClass.points.get(0+1)*50+40;
        end = PathClass.points.get(0)*50+40;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCanvas = canvas;
        super.onDraw(mCanvas);
        if(!path.isEmpty()){
            canvas.scale(3.0f,3.0f);
            canvas.drawColor(Color.TRANSPARENT);
            canvas.drawPath(path, paint1);
            canvas.drawCircle(x,y,20,paint2);
            canvas.drawCircle(start,end,20,paint3);
            path.reset();}


    }
}
