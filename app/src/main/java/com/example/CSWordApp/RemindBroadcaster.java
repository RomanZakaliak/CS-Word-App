package com.example.CSWordApp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RemindBroadcaster extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String CHANNEL_ID = intent.getStringExtra("CHANNEL_ID");
        String CHANNEL_NAME = intent.getStringExtra("CHANNEL_NAME");
        String Title = intent.getStringExtra("TITLE");
        String Message = intent.getStringExtra("MESSAGE");

        new NotificationProvider(CHANNEL_ID, Title, Message, CHANNEL_NAME, context).showNotification();
    }
}
