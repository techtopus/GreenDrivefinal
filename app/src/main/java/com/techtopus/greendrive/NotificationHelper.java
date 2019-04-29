package com.techtopus.greendrive;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class NotificationHelper {
    public static void displaynotification(Context context,String title,String body) {
        Intent intent=new Intent(context,PendingRequest.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(
          context,100,intent,PendingIntent.FLAG_CANCEL_CURRENT
        );


        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(context,driverdetails.CHANNEL_ID)
                .setSmallIcon(R.drawable.gogreen)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat mNotificationMgr=NotificationManagerCompat.from(context);
        mNotificationMgr.notify(1,mBuilder.build());

    }
}
