package com.example.lifter;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.lifter.helper.NotificationService;

public class LifterApplication extends Application {
    private NotificationService notificationService;
    private Intent mServiceIntent;

    @Override
    public void onCreate() {
        super.onCreate();


        notificationService = new NotificationService(getApplicationContext());
        mServiceIntent = new Intent(getApplicationContext(), notificationService.getClass());
        if (!isMyServiceRunning(notificationService.getClass())) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                getApplicationContext().startForegroundService(mServiceIntent);
            } else {
                startService(mServiceIntent);
            }

        }
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }


}
