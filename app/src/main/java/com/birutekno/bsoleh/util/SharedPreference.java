package com.birutekno.bsoleh.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.birutekno.bsoleh.constant.Constant;

public class SharedPreference {

    Context context;

    public SharedPreference(Context context) {
        this.context = context;
    }

    public void setSharedPrefIntro(){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constant.PREFS_INTRO, context.MODE_PRIVATE).edit();
        editor.putBoolean("status", true);
        editor.apply();
    }

    public boolean getSharedPrefIntro(){
        SharedPreferences prefs = context.getSharedPreferences(Constant.PREFS_INTRO, context.MODE_PRIVATE);
        return prefs.getBoolean("status", false);
    }

}
