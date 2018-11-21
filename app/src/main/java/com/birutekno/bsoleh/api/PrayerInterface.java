package com.birutekno.bsoleh.api;

import com.birutekno.bsoleh.model.PrayerObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PrayerInterface {

    //http://api.aladhan.com/v1/timingsByAddress?address=Jalan%20Sharon%20Boulevard%20Raya,Cipamokolan,Rancasari,%20Cipamokolan,%20Rancasari,%20Kota%20Bandung,%20Jawa%20Barat%2040292,%20Indonesia&method=11

    @GET("calendarByAddress")
    Call<PrayerObject> getCalendarByAddress(@Query("address") String address, @Query("year") String year, @Query("annual") boolean annual, @Query("method") String method, @Query("tune") String tune, @Query("school") String school, @Query("latitudeAdjustmentMethod") String latitudeAdjustmentMethod, @Query("adjustment") String adjustment);

    @GET("timingsByAddress")
    Call<PrayerObject> getTimingByAddress(@Query("date_or_timestamp") String date_or_timestamp, @Query("address") String address, @Query("method") String method, @Query("tune") String tune, @Query("school") String school, @Query("latitudeAdjustmentMethod") String latitudeAdjustmentMethod);
}
