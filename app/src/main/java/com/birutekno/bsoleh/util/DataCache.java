package com.birutekno.bsoleh.util;

import android.content.Context;

import com.birutekno.bsoleh.model.DataPrayer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class DataCache {

    SharedPreference sharedPreference;

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

}
