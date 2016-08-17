package com.umutkina.cevsen.pushnotif.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.umutkina.cevsen.MainActivity;
import com.umutkina.cevsen.R;
import com.umutkina.cevsen.pushnotif.ContentDetailActivity;
import com.umutkina.cevsen.pushnotif.WebViewActivity;
import com.umutkina.cevsen.pushnotif.YoutubePlayerActivity;
import com.umutkina.cevsen.pushnotif.modals.Notification;
import com.umutkina.cevsen.pushnotif.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by umutkina on 07/08/16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private String TAG = "com.umutkina.ydsservice";

    ArrayList<Map<String, String>> maps;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        Map<String, String> data = remoteMessage.getData();
        if (data.size() > 0) {
            Log.d(TAG, "Message data payload: " + data);
            String denemeKey = data.get("denemeKey");
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "Message Notification collapseKey: " + remoteMessage.getCollapseKey());
//            Log.d(TAG, "Message Notification collapseKey: " + remoteMessage.getNotification().get);
            String collapseKey = remoteMessage.getCollapseKey();
            sendNotification(data,remoteMessage.getNotification().getBody());
        }



        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void sendNotification(Map<String, String> data, String title) {
        Intent intent = null;


        if (data.size() > 0) {

            Log.d(TAG, "data.size() > 0 " + data);

            String type = data.get("type");
             title = data.get("title");
            String videoUrl = data.get("videoUrl");
            String webUrl = data.get("webUrl");
            String message = data.get("message");
            String link = data.get("linkUrl");
            String imageUrl = data.get("imageUrl");
            String buttonText = data.get("buttonText");
            String id = System.currentTimeMillis() + "";

            Notification notification = new Notification();

            notification.setTitle(title);
            notification.setMessage(message);
            notification.setImageUrl(imageUrl);
            notification.setLink(link);
            notification.setType(type);
            notification.setVideoUrl(videoUrl);
            notification.setWebUrl(webUrl);
            notification.setId(id);
            notification.setButtonText(buttonText);


            Utils.saveNotification(notification, this);


            switch (type) {
                case "video":
                    intent = YoutubePlayerActivity.newIntent(this, videoUrl);
                    break;

                case "content":
                    HashMap<String, String> stringStringHashMap = new HashMap<>();
//                    Set<String> strings = data.keySet();
//
//                    String[] objects = (String[]) strings.toArray();
//                    for (String object : objects) {
//
//                        stringStringHashMap.pu
//                    }
//                    stringStringHashMap.put(data.)
                    stringStringHashMap.putAll(data);
                    intent = ContentDetailActivity.newIntent(this, notification);

                    break;


                case "web":

                    intent = WebViewActivity.newIntent(this, webUrl);

                    break;

            }


        } else {
            Log.d(TAG, "MenuActivity");
            intent = new Intent(this,  MainActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.pray)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(title)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void sendNotification(Map<String, String> data) {
        Intent intent = null;
        if (data.size() > 0&&data.get("type")!=null) {

            Log.d(TAG, "data.size() > 0 " + data);

            String type = data.get("type");
            String videoUrl = data.get("videoUrl");
            String webUrl = data.get("webUrl");
            String message = data.get("message");
            String link = data.get("linkUrl");
            String imageUrl = data.get("imageUrl");
            String title = data.get("title");
            String buttonText = data.get("buttonText");
            String id = System.currentTimeMillis() + "";

            Notification notification= new Notification();

            notification.setMessage(message);
            notification.setImageUrl(imageUrl);
            notification.setLink(link);
            notification.setType(type);
            notification.setVideoUrl(videoUrl);
            notification.setWebUrl(webUrl);
            notification.setId(id);
            notification.setTitle(title);
            notification.setButtonText(buttonText);



            Utils.saveNotification(notification,this);
            switch (type) {
                case "video":
                    intent = YoutubePlayerActivity.newIntent(this, videoUrl);
                    break;

                case "content":
                    intent = ContentDetailActivity.newIntent(this, notification);

                    break;

                case "web":

                    intent= WebViewActivity.newIntent(this,webUrl);

                    break;


            }
            this.startActivity(intent);
        }

//        } else {
//            Log.d(TAG, "MenuActivity");
//            intent = new Intent(this, MenuActivity.class);
//        }
//
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.icon)
//                .setContentTitle("FCM Message")
//                .setContentText(data.get("Body"))
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


}
