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

    public void setSharedPrefCity(String sharedPrefLocation){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constant.PREFS_LOCATION, context.MODE_PRIVATE).edit();
        editor.putString("city", sharedPrefLocation);
        editor.apply();
    }

    public String getSharedPrefCity(){
        SharedPreferences prefs = context.getSharedPreferences(Constant.PREFS_LOCATION, context.MODE_PRIVATE);
        return prefs.getString("city", null);
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

    public void setSharedPrefPrayer(String method, String methodName, String school, String schoolName, String lat, String latName){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constant.PREFS_PRAYER, context.MODE_PRIVATE).edit();
        editor.putString("method", method);
        editor.putString("methodName", methodName);
        editor.putString("school", school);
        editor.putString("schoolName", schoolName);
        editor.putString("lat", lat);
        editor.putString("latName", latName);
        editor.apply();
    }

    public void setSharedPrefTuning(String subuh, String dzuhur, String asr, String magrib, String isya){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constant.PREFS_PRAYER, context.MODE_PRIVATE).edit();
        editor.putString("subuh", subuh);
        editor.putString("dzuhur", dzuhur);
        editor.putString("asr", asr);
        editor.putString("magrib", magrib);
        editor.putString("isya", isya);
        editor.apply();
    }

    public SharedPreferences getSharedPrefPrayer(){
        SharedPreferences prefs = context.getSharedPreferences(Constant.PREFS_PRAYER, context.MODE_PRIVATE);
        return prefs;
    }

    public void setSharedPrefPrayerCache(String args, String year, boolean bool){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constant.PREFS_CACHE, context.MODE_PRIVATE).edit();
        editor.putString("prayer_cache", args);
        editor.putString("prayer_cache_year", year);
        editor.putBoolean("prayer_cache_bool", bool);
        editor.apply();
    }

    public void setSharedPrefPrayerCacheBool(){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constant.PREFS_CACHE, context.MODE_PRIVATE).edit();
        editor.putBoolean("prayer_cache_bool", false);
        editor.apply();
    }

    public String getSharedPrefPrayerCache(){
        SharedPreferences prefs = context.getSharedPreferences(Constant.PREFS_CACHE, context.MODE_PRIVATE);
        return prefs.getString("prayer_cache", null);
    }

    public String getSharedPrefPrayerCacheYear(){
        SharedPreferences prefs = context.getSharedPreferences(Constant.PREFS_CACHE, context.MODE_PRIVATE);
        return prefs.getString("prayer_cache_year", null);
    }

    public boolean getSharedPrefPrayerCacheBool(){
        SharedPreferences prefs = context.getSharedPreferences(Constant.PREFS_CACHE, context.MODE_PRIVATE);
        return prefs.getBoolean("prayer_cache_bool", false);
    }

    public void setSharedPrefPrayerNotif(String s, boolean bool){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constant.PREFS_PRAYER, context.MODE_PRIVATE).edit();
        if (s.equals("1")){
            editor.putBoolean("prayer_notif_subuh", bool);
        }else if (s.equals("2")){
            editor.putBoolean("prayer_notif_dzuhur", bool);
        }else if (s.equals("3")){
            editor.putBoolean("prayer_notif_ashar", bool);
        }else if (s.equals("4")){
            editor.putBoolean("prayer_notif_magrib", bool);
        }else if (s.equals("5")){
            editor.putBoolean("prayer_notif_isya", bool);
        }
        editor.apply();
    }

    public void setSharedPrefPrayerMaster(boolean bool){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constant.PREFS_PRAYER, context.MODE_PRIVATE).edit();
        editor.putBoolean("prayer_notif_master", bool);
        editor.apply();
    }
}
