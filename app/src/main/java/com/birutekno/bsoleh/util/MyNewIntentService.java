package com.birutekno.bsoleh.util;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationManagerCompat;

import com.birutekno.bsoleh.MainActivity;
import com.birutekno.bsoleh.R;

public class MyNewIntentService extends IntentService {
    private static final int NOTIFICATION_ID = 3;

    private SharedPreference sharedPreference;

    boolean masterNotifBool;
    boolean subuhBool;
    boolean dzuhurBool;
    boolean asharBool;
    boolean magribBool;
    boolean isyaBool;

    public MyNewIntentService() {
        super("MyNewIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sharedPreference = new SharedPreference(getApplicationContext());
        loadBoolean();

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String prayerName = intent.getStringExtra("prayer");
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Waktunya " + prayerName);
        builder.setContentText("Shalatlah tepat waktu");
        builder.setSound(alarmSound);
        builder.setSmallIcon(R.drawable.logo_bsoleh);
        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);

        if (masterNotifBool){
            if (prayerName.equals("Subuh") && subuhBool){
                if (System.currentTimeMillis() < intent.getLongExtra("time",0)){
                    managerCompat.notify(NOTIFICATION_ID, notificationCompat);
                }
            }else if (prayerName.equals("Dzuhur") && dzuhurBool){
                if (System.currentTimeMillis() < intent.getLongExtra("time",0)){
                    managerCompat.notify(NOTIFICATION_ID, notificationCompat);
                }
            }else if (prayerName.equals("Ashar") && asharBool){
                if (System.currentTimeMillis() < intent.getLongExtra("time",0)){
                    managerCompat.notify(NOTIFICATION_ID, notificationCompat);
                }
            }else if (prayerName.equals("Magrib") && magribBool){
                if (System.currentTimeMillis() < intent.getLongExtra("time",0)){
                    managerCompat.notify(NOTIFICATION_ID, notificationCompat);
                }
            }else if (prayerName.equals("Isya") && isyaBool){
                if (System.currentTimeMillis() < intent.getLongExtra("time",0)){
                    managerCompat.notify(NOTIFICATION_ID, notificationCompat);
                }
            }
        }
    }

    private void loadBoolean(){
        SharedPreferences preferences = sharedPreference.getSharedPrefPrayer();
        masterNotifBool = preferences.getBoolean("prayer_notif_master", false);
        subuhBool = preferences.getBoolean("prayer_notif_subuh", false);
        dzuhurBool = preferences.getBoolean("prayer_notif_dzuhur", false);
        asharBool = preferences.getBoolean("prayer_notif_ashar", false);
        magribBool = preferences.getBoolean("prayer_notif_magrib", false);
        isyaBool = preferences.getBoolean("prayer_notif_isya", false);
    }
}
