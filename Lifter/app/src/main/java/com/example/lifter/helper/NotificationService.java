package com.example.lifter.helper;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.lifter.R;
import com.example.lifter.database.mySQLiteDBHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {

    private static final String TAG = "NotificationService";

    public static final int notify = 5000;  //interval between two services(Here Service run every 5 Minute)
    private Handler mHandler = new Handler();   //run on another Thread to avoid crash
    private Timer mTimer = null;    //timer handling

    //creating a mediaplayer object

    public int counter = 0;

    private mySQLiteDBHandler dbHandler;
    private String currentDate;

    private int mHour = 0;
    private int mMinute = 0;

    private int hours;
    private int minutes;

    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";
    private String eventName;

    String[] workOutActivity = new String[]{};
    private String randomName;


    public NotificationService(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
    }

    public NotificationService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dbHandler = new mySQLiteDBHandler(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");

        stoptimertask();

        Intent broadcastIntent = new Intent(this, MyReceiver.class);

        sendBroadcast(broadcastIntent);
    }


    private Timer timer;
    private TimerTask timerTask;
    long oldTime = 0;

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 5000, 5000); //
        // timer.schedule(timerTask, notify, notify); //
    }


    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  " + (counter++));

                //Toast.makeText(NotificationService.this, "Success", Toast.LENGTH_SHORT).show();

                final Calendar c = Calendar.getInstance();


                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                //================== Today date ======================//
                currentDate = Integer.toString(year) + Integer.toString(month) + Integer.toString(day);

                ReadDatabase(currentDate);


                c.set(year, month, day);
                String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
                String dayToNotify = days[c.get(Calendar.DAY_OF_WEEK) - 1];
                ReadAllSuggested(dayToNotify);


            }
        };
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void ReadDatabase(String selectedDate) {
        if (dbHandler.getNote(selectedDate) != null) {

            eventName = dbHandler.getNote(currentDate).getWork().replace("[", "").replace("]", "");

            String time = dbHandler.getNote(currentDate).getTime();
            String[] str = time.split("_");
            mHour = Integer.parseInt(str[0]);
            mMinute = Integer.parseInt(str[1]);
            startNotification(mHour, mMinute, false);

        }

    }

    public void ReadAllSuggested(String dayOfWeek) {

        dbHandler.getAllSuggestDay();
        if (dbHandler.getAllSuggestDay() != null) {
            if (dbHandler.getAllSuggestDay().size() > 0) {

                String days = dbHandler.getAllSuggestDay().get(0).getDay().replace("[", "").replace("]", "");
                ArrayList<String> myList = new ArrayList<>(Arrays.asList(days.split(",")));
                for (int i = 0; i < myList.size(); i++) {
                    Log.e(TAG, "ReadAllSuggested: " + myList.get(i).trim());
                    Log.e(TAG, "dayOfWeek: " + dayOfWeek);
                    if (myList.get(i).trim().equals(dayOfWeek)) {

                        if (myList.size() <= 2) {
                            workOutActivity = getResources().getStringArray(R.array.less_than_two);
                            int randomIndex = new Random().nextInt(workOutActivity.length);
                            randomName = workOutActivity[randomIndex];
                        } else if (myList.size() == 3) {
                            workOutActivity = getResources().getStringArray(R.array.equal_three);
                            int randomIndex = new Random().nextInt(workOutActivity.length);
                            randomName = workOutActivity[randomIndex];
                        } else {
                            workOutActivity = getResources().getStringArray(R.array.more_than_three);
                            int randomIndex = new Random().nextInt(workOutActivity.length);
                            randomName = workOutActivity[randomIndex];
                        }

                        String time = dbHandler.getAllSuggestDay().get(0).getTime();
                        String[] str = time.split("_");
                        mHour = Integer.parseInt(str[0]);
                        mMinute = Integer.parseInt(str[1]);
                        startNotification(mHour, mMinute, true);

                    }
                }
            }
        }

    }


    public void startNotification(int hour, int minute, boolean isSuggested) {

        Calendar alarmFor = Calendar.getInstance();
        alarmFor.set(Calendar.HOUR_OF_DAY, hour);
        alarmFor.set(Calendar.MINUTE, minute);
        alarmFor.set(Calendar.SECOND, 0);

        Long timeMillis = alarmFor.getTimeInMillis() - 1800000;

        Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        c.set(Calendar.SECOND, 0);

        if (alarmFor.getTimeInMillis() != 0) {
            if (c.getTimeInMillis() == timeMillis) {
                //createNotification();
                String h = hour + "", m = minute + "";

                if (h.length() == 1) h = "0" + h;
                if (m.length() == 1) m = "0" + m;

                String timeData = h + ":" + m;

                if (isSuggested) {
                    scheduleNotification(getNotification(String.format(getResources().getString(R.string.notification_des), randomName, timeData)), 0, timeMillis);
                } else {
                    scheduleNotification(getNotification(String.format(getResources().getString(R.string.notification_des), eventName, timeData)), 0, timeMillis);
                }

            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "com.example.lifter";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }


    //========================= Get local notification ================= //

    private void scheduleNotification(Notification notification, int delay, long timeMillis) {
        Intent notificationIntent = new Intent(this, MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long futureInMillis = timeMillis + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, default_notification_channel_id);
        builder.setContentTitle(getResources().getString(R.string.exercise));
        builder.setContentText(content);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(content));
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setAutoCancel(true);
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        return builder.build();
    }

}