package com.birutekno.bsoleh.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.bsoleh.R;
import com.birutekno.bsoleh.api.PrayerApi;
import com.birutekno.bsoleh.constant.Constant;
import com.birutekno.bsoleh.decorators.HighlightWeekendsDecorator;
import com.birutekno.bsoleh.decorators.MySelectorDecorator;
import com.birutekno.bsoleh.decorators.OneDayDecorator;
import com.birutekno.bsoleh.model.DataPrayer;
import com.birutekno.bsoleh.model.Hijri;
import com.birutekno.bsoleh.model.PrayerObject;
import com.birutekno.bsoleh.model.Timings;
import com.birutekno.bsoleh.model._1;
import com.birutekno.bsoleh.model._10;
import com.birutekno.bsoleh.model._11;
import com.birutekno.bsoleh.model._12;
import com.birutekno.bsoleh.model._2;
import com.birutekno.bsoleh.model._3;
import com.birutekno.bsoleh.model._4;
import com.birutekno.bsoleh.model._5;
import com.birutekno.bsoleh.model._6;
import com.birutekno.bsoleh.model._7;
import com.birutekno.bsoleh.model._8;
import com.birutekno.bsoleh.model._9;
import com.birutekno.bsoleh.util.DataCache;
import com.birutekno.bsoleh.util.MyReceiver;
import com.birutekno.bsoleh.util.SharedPreference;
import com.birutekno.bsoleh.util.ToastUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.progress.progressview.ProgressView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.LocalDate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;


public class ScheduleFragment extends Fragment implements OnDateSelectedListener,SwipeRefreshLayout.OnRefreshListener {

    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;

    SharedPreference sharedPreference;
    ToastUtil toastUtil;
    DataCache dataCache;

    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private double latitude;
    private double longitude;
    private String currentLocation;

    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();

    private SimpleDateFormat clockFormat = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat clockHour = new SimpleDateFormat("HH");
    private SimpleDateFormat clockMinutes = new SimpleDateFormat("mm");
    private SimpleDateFormat clockDetailFormat = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat formatterShow = new SimpleDateFormat("dd MMMM yyyy");

    String date;
    String method;
    String tuning;
    String tuneSubuh;
    String tuneDzuhur;
    String tuneAshar;
    String tuneMagrib;
    String tuneIsya;
    String school;
    String lat;

    boolean masterNotifBool;
    boolean subuhBool;
    boolean dzuhurBool;
    boolean asharBool;
    boolean magribBool;
    boolean isyaBool;

    @BindView(R.id.swipeRefresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rlOverview) RelativeLayout rlOverview;
    @BindView(R.id.pbOverview) ProgressBar pbOverview;
    @BindView(R.id.tvAddress) TextView tvAddress;
    @BindView(R.id.progressView) ProgressView progressView;
    @BindView(R.id.tvCurrentPrayerName) TextView tvCurrentPrayerName;
    @BindView(R.id.tvCurrentPrayerTime) TextView tvCurrentPrayerTime;
    @BindView(R.id.tvCountdownPrayerTime) TextView tvCountdownPrayerTime;
    @BindView(R.id.calendarView) MaterialCalendarView widget;
    @BindView(R.id.tvCurrentCity) TextView tvCurrentCity;
    @BindView(R.id.tvSelectedDate) TextView tvSelectedDate;
    @BindView(R.id.tvHijriDate) TextView tvHijriDate;
    @BindView(R.id.tvSubuhPrayer) TextView tvSubuhPrayer;
    @BindView(R.id.swSubuh) Switch swSubuh;
    @BindView(R.id.tvDzuhurPrayer) TextView tvDzuhurPrayer;
    @BindView(R.id.swDzuhur) Switch swDzuhur;
    @BindView(R.id.tvAsrPrayer) TextView tvAsrPrayer;
    @BindView(R.id.swAshar) Switch swAshar;
    @BindView(R.id.tvMagribPrayer) TextView tvMagribPrayer;
    @BindView(R.id.swMagrib) Switch swMagrib;
    @BindView(R.id.tvIshaPrayer) TextView tvIshaPrayer;
    @BindView(R.id.swIsya) Switch swIsya;

    public ScheduleFragment() {

    }

    public static ScheduleFragment newInstance() {
        ScheduleFragment fragment = new ScheduleFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initViews(View view){
        ButterKnife.bind(this, view);

        sharedPreference = new SharedPreference(getContext());
        toastUtil = new ToastUtil(getContext());
        dataCache = new DataCache(getContext());
        swipeRefreshLayout.setOnRefreshListener(this);

        initWidget();
        loadPrayerSetting();

        if (sharedPreference.getSharedPrefLocation() == null){
            OpenDialog(view);
        }else {
            if (date != null){
                String year = date.substring(6,10);
                if (dataCache.getPrayerBool(year)){
                    tvAddress.setText(sharedPreference.getSharedPrefLocation());
                    tvCurrentCity.setText(sharedPreference.getSharedPrefCity());
                    getPrayerTimeCache(date);
                }else {
                    try {
                        getLocation();
                    }catch (Exception ex){
                        buildGoogleApiClient();
                    }
                }
            }else {
                OpenDialog(view);
            }
        }

    }

    private void initWidget(){
        widget.setOnDateChangedListener(this);
        widget.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        final LocalDate instance = LocalDate.now();
        widget.setSelectedDate(instance);
        widget.addDecorators(
                new MySelectorDecorator(getActivity()),
                new HighlightWeekendsDecorator(),
                oneDayDecorator
        );
        widget.state().edit()
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();

        rlOverview.setVisibility(View.GONE);
        pbOverview.setVisibility(View.VISIBLE);
    }

    private void loadPrayerSetting(){
        SharedPreferences preferences = sharedPreference.getSharedPrefPrayer();
        method = preferences.getString("method", "2");
        tuneSubuh = preferences.getString("subuh", "0");
        tuneDzuhur = preferences.getString("dzuhur", "0");
        tuneAshar = preferences.getString("asr", "0");
        tuneMagrib = preferences.getString("magrib", "0");
        tuneIsya = preferences.getString("isya", "0");
        school = preferences.getString("school", "0");
        lat = preferences.getString("lat", "1");

        masterNotifBool = preferences.getBoolean("prayer_notif_master", false);
        subuhBool = preferences.getBoolean("prayer_notif_subuh", false);
        dzuhurBool = preferences.getBoolean("prayer_notif_dzuhur", false);
        asharBool = preferences.getBoolean("prayer_notif_ashar", false);
        magribBool = preferences.getBoolean("prayer_notif_magrib", false);
        isyaBool = preferences.getBoolean("prayer_notif_isya", false);

        swSubuh.setChecked(subuhBool);
        swDzuhur.setChecked(dzuhurBool);
        swAshar.setChecked(asharBool);
        swMagrib.setChecked(magribBool);
        swIsya.setChecked(isyaBool);

        //Imsak,Fajr,Sunrise,Zhuhr,Asr,Sunset,Maghrib,Isha,Midnight
        tuning = "0," + tuneSubuh + "," + "0," + tuneDzuhur + "," + tuneAshar + "," + "0," +tuneMagrib + "," + tuneIsya + "0,";

        Date todayDate = Calendar.getInstance().getTime();
        String todayString = formatterShow.format(todayDate);
        date = formatter.format(todayDate);
        tvSelectedDate.setText(todayString);
        dataCache.new AsyncCaller().execute();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        //If you change a decorate, you need to invalidate decorators
        oneDayDecorator.setDate(date.getDate());
        String calendarDate = String.valueOf(date.getDate());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateConvert = format.parse(calendarDate);
            tvSelectedDate.setText(formatterShow.format(dateConvert));
            String year = formatter.format(dateConvert).substring(6,10);
            if (dataCache.getPrayerBool(year)){
                Log.d(Constant.TAG, "CACHE: 1");
                getPrayerTimeCache(formatter.format(dateConvert));
            }else {
                Log.d(Constant.TAG, "onDateSelected: 1");
                getPrayerTime(formatter.format(dateConvert));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //DD-MM-YYYY
        widget.invalidateDecorators();
    }

    public void OpenDialog(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("We need to get your location");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (checkPlayServices()){
                    buildGoogleApiClient();
                }else {
                    getLocation();
                }
            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toastUtil.makeToast("if you want to get prayer time, Click the Addres bar above.", "", false);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(getContext());

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(getActivity(),resultCode, PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getContext(), "This device is not supported.", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
            return false;
        }
        return true;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API).build();

        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        Log.d(Constant.TAG, "initViews: BEF");
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location requests here
                        Log.d(Constant.TAG, "initViews: SUCCESS");
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.d(Constant.TAG, "initViews: REQUIRED");
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.d(Constant.TAG, "initViews: UNAVAILABLE");
                        break;
                }
            }
        });


    }

    private void getLocation() {
        try
        {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation == null){
                buildGoogleApiClient();
            }else {
                latitude = mLastLocation.getLatitude();
                longitude = mLastLocation.getLongitude();
                getAddress();
            }
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
    }

    public Address getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        if (getContext() != null){
            geocoder = new Geocoder(getContext(), Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(latitude,longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                return addresses.get(0);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    public void getAddress() {

        Address locationAddress = getAddress(latitude,longitude);

        if(locationAddress!=null) {
            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);

            if(!TextUtils.isEmpty(address)) {
                currentLocation=address;
                if (!TextUtils.isEmpty(address1)){
                    currentLocation+="\n"+address1;
                }
                tvAddress.setText(currentLocation);
                tvCurrentCity.setText(locationAddress.getLocality());
                sharedPreference.setSharedPrefLocation(currentLocation);
                sharedPreference.setSharedPrefCity(locationAddress.getLocality());
                sharedPreference.setSharedPrefLatlng(latitude,longitude);
                String year = date.substring(6,10);
                if (dataCache.getPrayerBool(year)){
                    getPrayerTimeCache(date);
                }else {
                    getPrayerTime(date);
                }
            }
        }
    }

    private void getPrayerTime(String date){
        final String month = date.substring(3,5);
        final String day = date.substring(0,2);
        final String year = date.substring(6,10);
        Call<PrayerObject> result = PrayerApi.getAPIService().getCalendarByAddress(currentLocation, year, true, method, tuning, school, lat, "1");
        result.enqueue(new Callback<PrayerObject>() {
            @Override
            public void onResponse(Call<PrayerObject> call, retrofit2.Response<PrayerObject> response) {
                try {
                    if(response.body()!=null) {
                        String status = response.body().getStatus();
                        if (status.equals("OK")) {
                            DataPrayer dataPrayer = response.body().getDataPrayer();
                            dataCache.setPrayerCache(dataPrayer, year);
                            com.birutekno.bsoleh.model.Date dateData = getDate(dataPrayer, day, month);
                            Timings timings = getTimings(dataPrayer, day, month);

                            setPrayerTime(timings, dateData);

                            toastUtil.makeToast("Success","success",true);
                            dataCache.new AsyncCaller().execute();
                        }else{
                            toastUtil.makeToast("Failed to fetch json","error",false);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PrayerObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getPrayerTimeCache(String date){
        Log.d(Constant.TAG, "THIS 1");
        try {
            final String month = date.substring(3,5);
            final String day = date.substring(0,2);

            DataPrayer dataPrayer = dataCache.getPrayerCache();
            com.birutekno.bsoleh.model.Date dateData = getDate(dataPrayer, day, month);
            Timings timings = getTimings(dataPrayer, day, month);

            Log.d(Constant.TAG, "THIS 2");
            setPrayerTime(timings, dateData);
        }catch (Exception ex){
            Log.d(Constant.TAG, "onDateSelected: 3");
            getPrayerTime(date);
        }
    }

    private Timings getTimings(DataPrayer dataPrayer, String day, String month){
        Timings timings = new Timings();
        if (month.equals("01")){
            _1[] monthModel = dataPrayer.get_1();
            timings = monthModel[Integer.parseInt(day)-1].getTimings();
        }else if (month.equals("02")){
            _2[] monthModel = dataPrayer.get_2();
            timings = monthModel[Integer.parseInt(day)-1].getTimings();
        }else if (month.equals("03")){
            _3[] monthModel = dataPrayer.get_3();
            timings = monthModel[Integer.parseInt(day)-1].getTimings();
        }else if (month.equals("04")){
            _4[] monthModel = dataPrayer.get_4();
            timings = monthModel[Integer.parseInt(day)-1].getTimings();
        }else if (month.equals("05")){
            _5[] monthModel = dataPrayer.get_5();
            timings = monthModel[Integer.parseInt(day)-1].getTimings();
        }else if (month.equals("06")){
            _6[] monthModel = dataPrayer.get_6();
            timings = monthModel[Integer.parseInt(day)-1].getTimings();
        }else if (month.equals("07")){
            _7[] monthModel = dataPrayer.get_7();
            timings = monthModel[Integer.parseInt(day)-1].getTimings();
        }else if (month.equals("08")){
            _8[] monthModel = dataPrayer.get_8();
            timings = monthModel[Integer.parseInt(day)-1].getTimings();
        }else if (month.equals("09")){
            _9[] monthModel = dataPrayer.get_9();
            timings = monthModel[Integer.parseInt(day)-1].getTimings();
        }else if (month.equals("10")){
            _10[] monthModel = dataPrayer.get_10();
            timings = monthModel[Integer.parseInt(day)-1].getTimings();
        }else if (month.equals("11")){
            _11[] monthModel = dataPrayer.get_11();
            timings = monthModel[Integer.parseInt(day)-1].getTimings();
        }else if (month.equals("12")){
            _12[] monthModel = dataPrayer.get_12();
            timings = monthModel[Integer.parseInt(day)-1].getTimings();
        }

        return timings;
    }

    private com.birutekno.bsoleh.model.Date getDate(DataPrayer dataPrayer, String day, String month){
        com.birutekno.bsoleh.model.Date dateData = new com.birutekno.bsoleh.model.Date();
        if (month.equals("01")){
            _1[] monthModel = dataPrayer.get_1();
            dateData = monthModel[Integer.parseInt(day)-1].getDate();
        }else if (month.equals("02")){
            _2[] monthModel = dataPrayer.get_2();
            dateData = monthModel[Integer.parseInt(day)-1].getDate();
        }else if (month.equals("03")){
            _3[] monthModel = dataPrayer.get_3();
            dateData = monthModel[Integer.parseInt(day)-1].getDate();
        }else if (month.equals("04")){
            _4[] monthModel = dataPrayer.get_4();
            dateData = monthModel[Integer.parseInt(day)-1].getDate();
        }else if (month.equals("05")){
            _5[] monthModel = dataPrayer.get_5();
            dateData = monthModel[Integer.parseInt(day)-1].getDate();
        }else if (month.equals("06")){
            _6[] monthModel = dataPrayer.get_6();
            dateData = monthModel[Integer.parseInt(day)-1].getDate();
        }else if (month.equals("07")){
            _7[] monthModel = dataPrayer.get_7();
            dateData = monthModel[Integer.parseInt(day)-1].getDate();
        }else if (month.equals("08")){
            _8[] monthModel = dataPrayer.get_8();
            dateData = monthModel[Integer.parseInt(day)-1].getDate();
        }else if (month.equals("09")){
            _9[] monthModel = dataPrayer.get_9();
            dateData = monthModel[Integer.parseInt(day)-1].getDate();
        }else if (month.equals("10")){
            _10[] monthModel = dataPrayer.get_10();
            dateData = monthModel[Integer.parseInt(day)-1].getDate();
        }else if (month.equals("11")){
            _11[] monthModel = dataPrayer.get_11();
            dateData = monthModel[Integer.parseInt(day)-1].getDate();
        }else if (month.equals("12")){
            _12[] monthModel = dataPrayer.get_12();
            dateData = monthModel[Integer.parseInt(day)-1].getDate();
        }

        return dateData;
    }

    private void setPrayerTime(Timings timings, com.birutekno.bsoleh.model.Date dateData){

        rlOverview.setVisibility(View.VISIBLE);
        pbOverview.setVisibility(View.GONE);

        tvSubuhPrayer.setText(timings.getFajr());
        tvDzuhurPrayer.setText(timings.getDhuhr());
        tvAsrPrayer.setText(timings.getAsr());
        tvMagribPrayer.setText(timings.getMaghrib());
        tvIshaPrayer.setText(timings.getIsha());

        Hijri hijri = dateData.getHijri();
        com.birutekno.bsoleh.model.Month month = hijri.getMonth();
        String day = hijri.getDay();
        String monthName = month.getEn();
        String year = hijri.getYear();
        tvHijriDate.setText(day + " " + monthName + " " + year + "H");

        realtimeClock();
        setPrayerReminder();
    }

    private void realtimeClock(){
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        if (getActivity() != null){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    long date = System.currentTimeMillis();
                                    long difference = System.currentTimeMillis();
                                    long differenceProgress = System.currentTimeMillis();
                                    String clockString = clockDetailFormat.format(date);
                                    String diff;

                                    try {
                                        Date currentTime = clockDetailFormat.parse(clockString);
                                        Date subuhTime = clockFormat.parse(tvSubuhPrayer.getText().toString());
                                        Date dzuhurTime = clockFormat.parse(tvDzuhurPrayer.getText().toString());
                                        Date asharTime = clockFormat.parse(tvAsrPrayer.getText().toString());
                                        Date magribTime = clockFormat.parse(tvMagribPrayer.getText().toString());
                                        Date isyaTime = clockFormat.parse(tvIshaPrayer.getText().toString());

                                        if (currentTime.compareTo(subuhTime) < 0){
                                            tvCurrentPrayerName.setText("Subuh");
                                            tvCurrentPrayerTime.setText(tvSubuhPrayer.getText().toString());
                                            difference = currentTime.getTime() - subuhTime.getTime();
                                            differenceProgress = isyaTime.getTime() - subuhTime.getTime();
                                        }else if (currentTime.compareTo(dzuhurTime) < 0){
                                            tvCurrentPrayerName.setText("Dzuhur");
                                            tvCurrentPrayerTime.setText(tvDzuhurPrayer.getText().toString());
                                            difference = currentTime.getTime() - dzuhurTime.getTime();
                                            differenceProgress = subuhTime.getTime() - dzuhurTime.getTime();
                                        }else if (currentTime.compareTo(asharTime) < 0){
                                            tvCurrentPrayerName.setText("Ashar");
                                            tvCurrentPrayerTime.setText(tvAsrPrayer.getText().toString());
                                            difference = currentTime.getTime() - asharTime.getTime();
                                            differenceProgress = dzuhurTime.getTime() - asharTime.getTime();
                                        }else if (currentTime.compareTo(magribTime) < 0){
                                            tvCurrentPrayerName.setText("Magrib");
                                            tvCurrentPrayerTime.setText(tvMagribPrayer.getText().toString());
                                            difference = currentTime.getTime() - magribTime.getTime();
                                            differenceProgress = asharTime.getTime() - magribTime.getTime();
                                        }else if (currentTime.compareTo(isyaTime) < 0){
                                            tvCurrentPrayerName.setText("Isya");
                                            tvCurrentPrayerTime.setText(tvIshaPrayer.getText().toString());
                                            difference = currentTime.getTime() - isyaTime.getTime();
                                            differenceProgress = magribTime.getTime() - isyaTime.getTime();
                                        }
                                        int sec = (int) (difference / 1000) % 60 ;
                                        int min = (int) ((difference  / (1000*60)) % 60);
                                        int hrs = (int) ((difference  / (1000*60*60)) % 24);
                                        hrs = (hrs< 0 ? -hrs: hrs);
                                        min = (min< 0 ? -min: min);
                                        sec = (sec< 0 ? -sec: sec);
                                        diff = hrs+":"+min+":"+sec;

                                        long diffInSec = TimeUnit.MILLISECONDS.toSeconds(difference);
                                        long diffinSecProgress = TimeUnit.MILLISECONDS.toSeconds(differenceProgress);
                                        diffInSec = (diffInSec<0 ? -diffInSec: diffInSec);
                                        diffinSecProgress = (diffinSecProgress<0 ? -diffinSecProgress: diffinSecProgress);

                                        float diffSqr = (float) Math.pow(diffinSecProgress, 2);

//                                        Log.i(Constant.TAG, "run: " + diffInSec + " | " + diffinSecProgress + " test : " + diffInSec * diffinSecProgress / diffSqr);
//
//                                        if (diffInSec == 5500){
//                                            alarmIntent.putExtra("prayer", tvCurrentPrayerName.getText().toString());
//                                            pendingIntent = PendingIntent.getBroadcast(getContext(), Constant.ALARM_REQUEST_CODE, alarmIntent, 0);
//                                            Log.d(Constant.TAG, "alarm" + tvCurrentPrayerName.getText().toString() + " has been armed for : " + 30);
//                                            triggerAlarmManager(30);
//                                        }

                                        progressView.setProgress(diffInSec * diffinSecProgress / diffSqr);
                                        tvCountdownPrayerTime.setText(diff);

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }

    private void setPrayerReminder(){
        Intent notifyIntent = new Intent(getContext(), MyReceiver.class);

        String subuh = tvSubuhPrayer.getText().toString();
        notifyIntent.putExtra("prayer", "Subuh");
        triggerAlarmManager(subuh, notifyIntent,1);

        String dzuhur = tvDzuhurPrayer.getText().toString();
        notifyIntent.putExtra("prayer", "Dzuhur");
        triggerAlarmManager(dzuhur, notifyIntent, 2);

        String ashar = tvAsrPrayer.getText().toString();
        notifyIntent.putExtra("prayer", "Ashar");
        triggerAlarmManager(ashar, notifyIntent, 3);

        String magrib = tvMagribPrayer.getText().toString();
        notifyIntent.putExtra("prayer", "Magrib");
        triggerAlarmManager(magrib, notifyIntent, 4);

        String isya = tvIshaPrayer.getText().toString();
        notifyIntent.putExtra("prayer", "Isya");
        triggerAlarmManager(isya, notifyIntent, 5);
    }

    private void triggerAlarmManager(String hour, Intent notifyIntent, int requestCode) {
        String[] parts = hour.split(":");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(parts[1]));
        cal.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), requestCode, notifyIntent.putExtra("time", cal.getTimeInMillis()), PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Log.d(Constant.TAG, "triggerAlarmManager: " +  notifyIntent.getStringExtra("prayer") + " : " + cal.getTimeInMillis());
    }

    @OnClick(R.id.tvAddress)
    public void tvAddress(){
        try {
            getLocation();
        }catch (Exception ex){
            buildGoogleApiClient();
        }
    }

    @OnClick(R.id.swSubuh)
    public void swSubuh(){
        if (!subuhBool){
            subuhBool = true;
            sharedPreference.setSharedPrefPrayerNotif("1", subuhBool);
            setPrayerReminder();
        }else {
            subuhBool = false;
            sharedPreference.setSharedPrefPrayerNotif("1", subuhBool);
            setPrayerReminder();
        }
    }

    @OnClick(R.id.swDzuhur)
    public void swDzuhur(){
        if (!dzuhurBool){
            dzuhurBool= true;
            sharedPreference.setSharedPrefPrayerNotif("2", dzuhurBool);
            setPrayerReminder();
        }else {
            dzuhurBool = false;
            sharedPreference.setSharedPrefPrayerNotif("2", dzuhurBool);
            setPrayerReminder();
        }
    }

    @OnClick(R.id.swAshar)
    public void swAshar(){
        if (!asharBool){
            asharBool = true;
            sharedPreference.setSharedPrefPrayerNotif("3", asharBool);
            setPrayerReminder();
        }else {
            asharBool = false;
            sharedPreference.setSharedPrefPrayerNotif("3", asharBool);
            setPrayerReminder();
        }
    }

    @OnClick(R.id.swMagrib)
    public void swMagrib(){
        if (!magribBool){
            magribBool = true;
            sharedPreference.setSharedPrefPrayerNotif("4", magribBool);
            setPrayerReminder();
        }else {
            magribBool = false;
            sharedPreference.setSharedPrefPrayerNotif("4", magribBool);
            setPrayerReminder();
        }
    }

    @OnClick(R.id.swIsya)
    public void swIsya(){
        if (!isyaBool){
            isyaBool = true;
            sharedPreference.setSharedPrefPrayerNotif("5", isyaBool);
            setPrayerReminder();
        }else {
            isyaBool = false;
            sharedPreference.setSharedPrefPrayerNotif("5", isyaBool);
            setPrayerReminder();
        }
    }

    @Override
    public void onRefresh() {
        try {
            getLocation();
        }catch (Exception ex){
            buildGoogleApiClient();
        }
        swipeRefreshLayout.setRefreshing(false);
        toastUtil.makeToast("Data & Location Refreshed", "success", true);
    }
}
