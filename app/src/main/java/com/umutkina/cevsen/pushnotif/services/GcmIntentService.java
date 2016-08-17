package com.umutkina.cevsen.pushnotif.services;//package com.umutkina.ydshazirlik.services;
//
///**
// * Created by umutkina on 07/08/16.
// */
//
//import android.app.IntentService;
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.app.NotificationCompat;
//import android.util.Log;
//
//import com.umutkina.ydshazirlik.R;
//
//import java.util.Set;
//
//;
//
///**
// * Created by haditok on 31/10/14.
// */
//public class GcmIntentService extends IntentService {
//    private static final String logTag = "PushSample";
//    private static final int PAGE_KEY_NULL_ID = -1;
//    public static final int NOTIFICATION_ID = 1;
//    private static final String TAG = "GcmIntentService";
//    NotificationCompat.Builder builder;
//
//    public GcmIntentService() {
//        super("GcmIntentService");
//    }
//
////    Class[] classes = {ChildDevelopmentMainpageActivity.class, ChildDevelopmentDemoActivity.class, PediatricPageActivity.class};
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        Bundle extras = intent.getExtras();
//        logPushExtras(intent);
//
//
//        fireba gcm = GoogleCloudMessaging.getInstance(this);
//        // The getMessageType() intent parameter must be the intent you received
//        // in your BroadcastReceiver.
//        String messageType = gcm.getMessageType(intent);
//
//        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
//
//            if (GoogleCloudMessaging.
//                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
//
//                sendNotification(extras);
//                Log.i(TAG, "Received: " + extras.toString());
//            }
//        }
//        // Release the wake lock provided by the WakefulBroadcastReceiver.
//        PushNotificationReceiver.completeWakefulIntent(intent);
//    }
//
//
//    private void sendNotification(Bundle msg) {
//        PSOApplication tdApplication = (PSOApplication) getApplication();
//        NotificationManager mNotificationManager = (NotificationManager)
//                this.getSystemService(Context.NOTIFICATION_SERVICE);
//        final PSOApplication application = (PSOApplication) getApplication();
//        SharedPreferences pref;
//
//
//        final String string2 = msg.getString("pageKey");
//        Intent intentStore;
//
//        if (tdApplication.getLoginSignupResponse() != null && string2 != null) {
//            if (string2.equalsIgnoreCase("timeline")) {
//
//                intentStore = new Intent(tdApplication, TimeLineActivity.class);
//
//                String requestId = msg.getString("requestId");
//                System.out.println("huu msg reqID" + requestId);
//                intentStore.putExtra("requestId", requestId);
//                intentStore.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            } else {
//                intentStore = new Intent(tdApplication, MessagesAlertsActivity.class);
////                YKBApplication.isPushActive = true;
//                intentStore.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            }
//
//        } else {
//            System.out.println("login intent");
//            intentStore = new Intent(tdApplication, LoginActivity.class);
////                YKBApplication.isPushActive = true;
//            intentStore.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        }
//
////        startActivity(intentStore);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intentStore, 0);
//
//        Notification.Builder mBuilder =
//                new Notification.Builder(this)
//                        .setSmallIcon(getNotificationIcon())
//                        .setContentTitle("PetSleepOver")
//                        .setAutoCancel(true)
////                        .setColor(getResources().getColor(R.color.pso_red))
//                        .setVibrate(new long[]{200, 200, 200, 200, 1000})
//                        .setLights(getResources().getColor(R.color.AntiqueWhite), 500, 500)
//                        .setStyle(new Notification.BigTextStyle()
//                                .bigText(msg.getString("message")))
////                        .setVisibility(VISIBILITY_PUBLIC)
//                        .setContentText(msg.getString("message"));
//
//
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        mBuilder.setSound(alarmSound);
//        mBuilder.setContentIntent(contentIntent);
//        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
//
//    }
//
//
//    private int getNotificationIcon() {
//        boolean whiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
//        return whiteIcon ? R.drawable.app_ikon_silhouette : R.drawable.app_ikon;
//    }
//
//    private void logPushExtras(Intent intent) {
//        Bundle extras = intent.getExtras();
//        Set<String> keys = extras.keySet();
//        String s = extras.toString();
//        extras.getString("pageKey");
//        System.out.println(logTag + " : " + s);
//
//        for (String key : keys) {
//
//            // ignore standard extra keys (GCM + UA)
////            List<String> ignoredKeys = (List<String>) Arrays.asList("collapse_key",// GCM collapse key
////                    "from",// GCM sender
////                    PushManager.EXTRA_NOTIFICATION_ID,// int id of generated notification (ACTION_PUSH_RECEIVED only)
////                    PushManager.EXTRA_PUSH_ID,// internal UA push id
////                    PushManager.EXTRA_ALERT);// ignore alert
////            if (ignoredKeys.contains(key)) {
////                continue;
////            }
//            Log.i(logTag, "Push Notification Extra: [" + key + " : " + intent.getStringExtra(key) + "]");
//        }
//    }
//
//
//}
