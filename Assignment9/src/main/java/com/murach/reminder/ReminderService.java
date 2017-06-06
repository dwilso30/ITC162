package com.murach.reminder;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.os.Build;
import android.util.Log;
import android.content.Intent;
import android.os.IBinder;
import java.util.Timer;
import java.util.TimerTask;
import android.app.NotificationManager;

/**
 * Created by wilso49 on 6/5/2017.
 */

public class ReminderService extends Service {

    private Timer timer;


    private void sendNotification(){

        Intent notificationIntent = new Intent(this, ReminderActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, flags);

        int icon = R.drawable.ic_launcher;
        CharSequence tickerText ="Look into the distance. It's good for your eyes!";
        CharSequence contentTitle ="Reminder";
        CharSequence contentText ="Look into the distance. It's good for your eyes!";

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(icon)
                .setTicker(tickerText)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager manager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        final int NOTIFICATION_ID = 1;
        manager.notify(NOTIFICATION_ID, notification);

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d("News REader", "Service Started");
        startTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        Log.d("News Reader", "Service Destroyed");
        stopTimer();
    }

    @Override
    public IBinder onBind(Intent intent){

        return null;
    }



    private void startTimer(){
        TimerTask task = new TimerTask(){
            @Override
            public void run(){
                Log.d("News Reader", "Look into the distance. It's good for your eyes!");
                sendNotification();
            }
        };
        timer = new Timer(true);
        int delay = 1000 * 5;
        int interval = 1000 * 5;
        timer.schedule(task, delay, interval);
    }
    private void stopTimer(){
        if (timer != null){
            timer.cancel();
        }
    }
}
