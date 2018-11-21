package com.birutekno.bsoleh.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.birutekno.bsoleh.constant.Constant;
import com.birutekno.bsoleh.model.DataPrayer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class DataCache {

    SharedPreference sharedPreference;
    String year;
    boolean prefBool;
    boolean returnBool;

    public DataCache(Context context) {
        sharedPreference = new SharedPreference(context);
    }

    public void setPrayerCache(DataPrayer dataPrayer, String year){
        Gson gson = new Gson();
        String json = gson.toJson(dataPrayer);
        sharedPreference.setSharedPrefPrayerCache(json, year, true);
    }

    public DataPrayer getPrayerCache(){
        Gson gson = new Gson();
        String json = sharedPreference.getSharedPrefPrayerCache();
        Type type = new TypeToken<DataPrayer>(){}.getType();
        DataPrayer dataPrayer = gson.fromJson(json, type);
        return dataPrayer;
    }

    public boolean getPrayerBool(String year){
        if (prefBool && year.equals(this.year)){
            Log.d(Constant.TAG, "onPreExecute: 3 TRUE");
            return true;
        }else {
            Log.d(Constant.TAG, "onPreExecute: 3 FALSE");
            return false;
        }
    }

    public class AsyncCaller extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params) {
            Log.d(Constant.TAG, "onPreExecute: 1");
            prefBool = sharedPreference.getSharedPrefPrayerCacheBool();
            year = sharedPreference.getSharedPrefPrayerCacheYear();
            Log.d(Constant.TAG, "onPreExecute: 2");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

}
