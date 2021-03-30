package com.example.CSWordApp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GestureDetectorCompat;

import com.example.CSWordApp.Broadcasters.RemindBroadcaster;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {

    private static final String TAG = "ReminderActivity";

    private TimePicker reminderTimePicker;
    private Toolbar toolbar;

    private Switch reminderSwitch;

    private GestureDetectorCompat swipeListener;


    private final static int SECOND = 1000;
    private final static int MINUTE = 60 * SECOND;
    private final static int HOUR = 60 * MINUTE;
    private final static int DAY = 24 * HOUR;

    private final static long delay =  30 * DAY ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        toolbar = findViewById(R.id.toolbar);
        setupToolbarAction();

        swipeListener = new GestureDetectorCompat(this, new SwipeGestureListener(this));

        reminderTimePicker = findViewById(R.id.remind_time_picker);
        reminderTimePicker.setIs24HourView(true);

        reminderSwitch = findViewById(R.id.remind_switch);

        Intent reminderIntent = setupReminderIntent();
        PendingIntent runningIntent = PendingIntent.getBroadcast(this, 0, reminderIntent, PendingIntent.FLAG_NO_CREATE);
        boolean isAlarmUp = runningIntent != null;

        reminderSwitch.setChecked(isAlarmUp);
        setTimePickerEnabled(reminderTimePicker, !isAlarmUp);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        reminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    setTimePickerEnabled(reminderTimePicker, false);
                    Calendar calendar = setupCalendar();
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderActivity.this, 0,
                            reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), delay, pendingIntent);
                    Log.w(TAG, alarmManager.toString());

                } else{
                    if(alarmManager != null){
                        setTimePickerEnabled(reminderTimePicker, true);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderActivity.this, 0,
                                reminderIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                        alarmManager.cancel(pendingIntent);
                        pendingIntent.cancel();
                        Log.w(TAG, alarmManager.toString());
                    }
                }
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return swipeListener.onTouchEvent(event);
    }

    private void setTimePickerEnabled(ViewGroup timePicker, boolean state){
        for(int i = 0; i < timePicker.getChildCount(); ++i){
            View view = timePicker.getChildAt(i);
            if(view instanceof ViewGroup){
                setTimePickerEnabled((ViewGroup)view, state);
            } else{
                view.setEnabled(state);
            }
        }
    }

    private void setupToolbarAction(){
        toolbar.setTitle("Нагадування");
        setSupportActionBar(toolbar);

        Drawable backArrow = ResourcesCompat.getDrawable(this.getResources(), R.drawable.ic_baseline_arrow_back_32, null);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(backArrow);
    }

    private Calendar setupCalendar(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, reminderTimePicker.getCurrentHour());
        calendar.set(Calendar.MINUTE, reminderTimePicker.getCurrentMinute());

        return calendar;
    }

    private Intent setupReminderIntent(){
        Intent reminderIntent = new Intent(getApplicationContext(), RemindBroadcaster.class);
        reminderIntent.putExtra("CHANNEL_ID", "REMINDER");
        reminderIntent.putExtra("CHANNEL_NAME", "Reminder");
        reminderIntent.putExtra("TITLE", getResources().getString(R.string.remind_default_title));
        reminderIntent.putExtra("MESSAGE", getResources().getString(R.string.remind_default_message));
        return  reminderIntent;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {    //закрити віртуальну клаву, якщо клік за межі поля вводу
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int[] scrCoordinates = new int[2];
            w.getLocationOnScreen(scrCoordinates);
            float x = event.getRawX() + w.getLeft() - scrCoordinates[0];
            float y = event.getRawY() + w.getTop() - scrCoordinates[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom()) ) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;

    }
}