package com.example.CSWordApp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.CSWordApp.Broadcasters.InactivityBroadcaster;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;

    private final static int SECOND = 1000;
    private final static int MINUTE = 60 * SECOND;
    private final static int HOUR = 60 * MINUTE;
    private final static int DAY = 24 * HOUR;

    private final static int delay = 2 * DAY;

    private Button btnLogin, btnRegistration;
    private FirebaseAuth  mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerInactivityNotification();

        tryLogin();

        database = FirebaseDatabase.getInstance(getResources().getString(R.string.realtime_db_reference));
        database.setPersistenceEnabled(true);
    }

    private void registerInactivityNotification() {
        Intent notificationIntent = new Intent(this, InactivityBroadcaster.class);
        notificationIntent.putExtra("CHANNEL_ID", "INACTIVITY");
        notificationIntent.putExtra("CHANNEL_NAME", "Inactivity");
        notificationIntent.putExtra("TITLE", getResources().getString(R.string.notification_inactive_title));
        notificationIntent.putExtra("MESSAGE", getResources().getString(R.string.notification_inactive_context));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC,System.currentTimeMillis() + delay, pendingIntent);
        Log.w("Main", alarmManager.toString());
    }


    private void tryLogin(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            Intent dashboardIntent = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(dashboardIntent);
            finish();
        } else{
            addListenerOnButton();
        }
    }

    private void addListenerOnButton() {
        btnLogin = (Button)findViewById(R.id.loginButtonMain);
        //btnRegistration = (Button)findViewById(R.id.registrationButtonMain);

        btnLogin.setOnClickListener(
                v -> {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
        );

        //Does not required in current state, saved for potential future using
//        btnRegistration.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
//                        startActivity(intent);
//                    }
//                }
//        );
    }


}