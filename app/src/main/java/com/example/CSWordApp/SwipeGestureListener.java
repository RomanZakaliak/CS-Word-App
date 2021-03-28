package com.example.CSWordApp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private static final int SWIPE_START_THRESHOLD = 100;

    private final Context context;
    public SwipeGestureListener(Context context){

        this.context = context;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;

        try{
            float diffX = e1.getX() - e2.getX();

            if(Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD){
                if(diffX < 0 && e1.getX() < SWIPE_START_THRESHOLD){
                    ((Activity)this.context).finish();
                    result = true;
                }
            }
        } catch (Exception e){
            Log.w("SwipeListener", e.getMessage(), e);
        }
        return result;
    }
}
