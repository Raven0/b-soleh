package com.birutekno.bsoleh.api;

import com.birutekno.bsoleh.model.PrayerObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PrayerInterface {

    @GET("timingsByAddress")
    Call<PrayerObject> getTimingByAddress(@Query("address") String params);
}
