package com.birutekno.bsoleh.api;

import com.birutekno.bsoleh.constant.Constant;

public class PrayerApi {
    public static PrayerInterface getAPIService(){
        return RetrofitClient.getClient(Constant.BASE_URL_API).create(PrayerInterface.class);
    }
}
