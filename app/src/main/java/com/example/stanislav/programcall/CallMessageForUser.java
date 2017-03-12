package com.example.stanislav.programcall;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.concurrent.TimeUnit;

public class CallMessageForUser extends Service {

    NotificationManager nm;
    Notification myNotication;

    @Override
    public void onCreate() {
        super.onCreate();
        nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendNotif();
        return super.onStartCommand(intent, flags, startId);
    }

    void sendNotif(){

        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent pIntent=PendingIntent.getActivity(this,0,intent,0);

        Notification.Builder builder=new Notification.Builder(this);

        builder.setAutoCancel(false);
        builder.setTicker("Back to program");
        builder.setContentTitle("Program Call");
        builder.setContentText("Back to program! Friend wait for your call:)");
        builder.setSmallIcon(R.drawable.ic_action_name);
        builder.setContentIntent(pIntent);
        builder.setOngoing(false);
        builder.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS);
        //builder.setSubText("This is subtext...");   //API level 16
        //builder.setNumber(100);
        builder.build();

        myNotication = builder.getNotification();
        nm.notify(1, myNotication);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
