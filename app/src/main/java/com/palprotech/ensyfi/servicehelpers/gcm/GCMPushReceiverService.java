package com.palprotech.ensyfi.servicehelpers.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.loginmodule.SplashScreenActivity;


/**
 * Created by Belal on 4/15/2016.
 */

//Class is extending GcmListenerService
public class GCMPushReceiverService extends GcmListenerService {

    //This method will be called on every new message received
    @Override
    public void onMessageReceived(String from, Bundle data) {
        //Getting the message from the bundle
        String message = data.getString("message");
        String title = data.getString("title");
        String subtitle = data.getString("subtitle");
        String tickerText = data.getString("tickerText");

        //Displaying a notiffication with the message
        sendNotification(message, title, subtitle, tickerText);
    }

    //This method is generating a notification and displaying the notification
    private void sendNotification(String message, String title, String subtitle, String tickerText) {
        Intent intent = new Intent(this, SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ab_logo);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ab_logo)
                    .setLargeIcon(bmp)
                    .setContentTitle(title)
                    .setSubText(subtitle)
                    .setTicker(tickerText)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(sound)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, noBuilder.build()); //0 = ID of notification
        } else {
            // Lollipop specific setColor method goes here.
            NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ab_logo)
                    .setLargeIcon(bmp)
                    .setContentTitle(title)
                    .setSubText(subtitle)
                    .setTicker(tickerText)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(sound)
                    .setColor(Color.argb(255, 118, 118, 188))
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, noBuilder.build()); //0 = ID of notification
        }
    }
}
