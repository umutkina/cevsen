package com.umutkina.cevsen;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.firebase.client.Firebase;

/**
 * Created by mac on 12/03/15.
 */
public class CevsenApplication extends Application {
    private int vibrateType;
    private SharedPreferences prefs;

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = getApplicationContext().getSharedPreferences("myprefs",
                Context.MODE_PRIVATE);
        Firebase.setAndroidContext(this);

        vibrateType = prefs.getInt("vibrateType", 0);
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public int getVibrateType() {
        return vibrateType;
    }

    public void setVibrateType(int vibrateType) {
        this.vibrateType = vibrateType;
    }
}
