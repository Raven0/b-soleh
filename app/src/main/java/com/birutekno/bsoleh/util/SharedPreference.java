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

    public void setSharedPrefGooglePermission(){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constant.PREFS_GPERMISSION, context.MODE_PRIVATE).edit();
        editor.putBoolean("status", true);
        editor.apply();
    }

    public boolean getSharedPrefGooglePermission(){
        SharedPreferences prefs = context.getSharedPreferences(Constant.PREFS_GPERMISSION, context.MODE_PRIVATE);
        return prefs.getBoolean("status", false);
    }

    public void setSharedPrefLocation(String sharedPrefLocation){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constant.PREFS_LOCATION, context.MODE_PRIVATE).edit();
        editor.putString("location", sharedPrefLocation);
        editor.apply();
    }

    public String getSharedPrefLocation(){
        SharedPreferences prefs = context.getSharedPreferences(Constant.PREFS_LOCATION, context.MODE_PRIVATE);
        return prefs.getString("location", null);
    }

    public void setSharedPrefLatlng(double latitude, double longitude ){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constant.PREFS_LATLNG, context.MODE_PRIVATE).edit();
        editor.putLong("latitude", Double.doubleToRawLongBits(latitude));
        editor.putLong("longitude", Double.doubleToRawLongBits(longitude));
        editor.apply();
    }

    public SharedPreferences getSharedPrefLatlng(){
        SharedPreferences prefs = context.getSharedPreferences(Constant.PREFS_LATLNG, context.MODE_PRIVATE);
        return prefs;
    }

}
