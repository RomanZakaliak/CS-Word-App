package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {

    private static final String TAG = "ReminderActivity";

    private TimePicker reminderTimePicker;
    private Toolbar toolbar;
    private TextInputEditText reminderTitle;
    private TextInputEditText reminderMessage;
    private Switch reminderSwitch;

    private final static int SECOND = 1000;
    private final static int MINUTE = 60 * SECOND;
    private final static int HOUR = 60 * MINUTE;
    private final static int DAY = 24 * HOUR;

    private final static int delay =  MINUTE ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        toolbar = findViewById(R.id.toolbar);
        setupToolbarAction();

        reminderTimePicker = findViewById(R.id.remind_time_picker);
        reminderTimePicker.setIs24HourView(true);

        reminderSwitch = findViewById(R.id.remind_switch);

        boolean isAlarmUp = (PendingIntent.getBroadcast(this, 0, new Intent(this,
                RemindBroadcaster.class), PendingIntent.FLAG_UPDATE_CURRENT)) != null;

        reminderSwitch.setChecked(isAlarmUp);

        reminderTitle = findViewById(R.id.remind_title);
        reminderMessage = findViewById(R.id.remind_message);

        Intent reminderIntent = setupReminderIntent();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        reminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Calendar calendar = setupCalendar();
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderActivity.this, 0,
                            reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), delay, pendingIntent);
                    Log.w(TAG, alarmManager.toString());

                } else{
                    if(alarmManager != null){
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderActivity.this, 0,
                                reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.cancel(pendingIntent);
                        Log.w(TAG, alarmManager.toString());
                    }
                }
            }
        });

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
        reminderIntent.putExtra("TITLE", "Placeholder");
        reminderIntent.putExtra("MESSAGE", "Placeholder");
        return  reminderIntent;
    }
}