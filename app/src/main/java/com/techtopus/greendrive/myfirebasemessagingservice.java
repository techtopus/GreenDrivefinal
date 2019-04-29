package com.techtopus.greendrive;

import android.app.NotificationManager;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class myfirebasemessagingservice extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getNotification()!=null)
        {
            String title=remoteMessage.getNotification().getTitle();
            String text=remoteMessage.getNotification().getBody();
            NotificationHelper.displaynotification(getApplicationContext(),title,text);
        }
    }
}
