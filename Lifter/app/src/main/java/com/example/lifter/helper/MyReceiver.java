package com.example.lifter.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("Service Stops", "Oops");

        context.startService(new Intent(context, NotificationService.class));

    }

}