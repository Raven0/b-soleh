package com.birutekno.bsoleh.fragment;

import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.bsoleh.R;
import com.birutekno.bsoleh.api.PrayerApi;
import com.birutekno.bsoleh.decorators.HighlightWeekendsDecorator;
import com.birutekno.bsoleh.decorators.MySelectorDecorator;
import com.birutekno.bsoleh.decorators.OneDayDecorator;
import com.birutekno.bsoleh.model.DataPrayer;
import com.birutekno.bsoleh.model.Hijri;
import com.birutekno.bsoleh.model.PrayerObject;
import com.birutekno.bsoleh.model.Timings;
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
import java.util.Random;

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

    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    double latitude;
    double longitude;
    String currentLocation;
    private Random r = new Random(1);

    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YYYY");
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

    @BindView(R.id.swipeRefresh) SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.tvAddress) TextView tvAddress;

    @BindView(R.id.progressView) ProgressView progressView;

    @BindView(R.id.calendarView) MaterialCalendarView widget;

    @BindView(R.id.tvSelectedDate) TextView tvSelectedDate;

    @BindView(R.id.tvHijriDate) TextView tvHijriDate;

    @BindView(R.id.tvSubuhPrayer) TextView tvSubuhPrayer;

    @BindView(R.id.tvDzuhurPrayer) TextView tvDzuhurPrayer;

    @BindView(R.id.tvAsrPrayer) TextView tvAsrPrayer;

    @BindView(R.id.tvMagribPrayer) TextView tvMagribPrayer;

    @BindView(R.id.tvIshaPrayer) TextView tvIshaPrayer;

    public ScheduleFragment() {

    }

    public static ScheduleFragment newInstance() {
        ScheduleFragment fragment = new ScheduleFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        if (sharedPreference.getSharedPrefLocation() == null){
            OpenDialog(view);
        }else {
            tvAddress.setText(sharedPreference.getSharedPrefLocation());
        }

        if (checkPlayServices()){
            buildGoogleApiClient();
        }else {
            getLocation();
        }

        initWidget();
        loadPrayerSetting();

        swipeRefreshLayout.setOnRefreshListener(this);

        //Meter progress Initialization
        //ranged from 0.0f to 1f
        float floatone = 0.68f;
        progressView.setProgress(floatone);
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
        tuning = tuneSubuh + "," + tuneDzuhur + "," + tuneAshar + "," + tuneMagrib + "," + tuneIsya;

        Date todayDate = Calendar.getInstance().getTime();
        String todayString = formatter.format(todayDate);
        date = todayString;
        tvSelectedDate.setText(date);
    }

    @OnClick(R.id.progressView)
    public void progressView(){
        float progress = r.nextFloat();
        progressView.setProgress(progress);
    }

    @OnClick(R.id.tvAddress)
    public void tvAddress(){
        if (checkPlayServices()){
            buildGoogleApiClient();
        }else {
            getLocation();
        }
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        //If you change a decorate, you need to invalidate decorators
        oneDayDecorator.setDate(date.getDate());
        String calendarDate = String.valueOf(date.getDate());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateConvert = format.parse(calendarDate);
            toastUtil.makeToast(formatter.format(dateConvert),"",false);
            tvSelectedDate.setText(formatter.format(dateConvert));
            getPrayerTime(formatter.format(dateConvert), currentLocation, method, tuning, school, lat);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //DD-MM-YYYY
        widget.invalidateDecorators();
    }

    public void OpenDialog(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("We need to get your location");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
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

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location requests here
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });


    }

    private void getLocation() {
        try
        {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            getAddress();
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
    }

    public Address getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude,longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void getAddress() {

        Address locationAddress = getAddress(latitude,longitude);

        if(locationAddress!=null) {
            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);

            String TAG = "READTHIS";
            Log.d(TAG, "getAddress: Latitude = " + latitude);
            Log.d(TAG, "getAddress: Longitude = " + longitude);
            Log.d(TAG, "getAddress: getPhone = " + locationAddress.getPhone());
            Log.d(TAG, "getAddress: getLocality = " + locationAddress.getLocality());
            Log.d(TAG, "getAddress: getAddressLine = " + locationAddress.getAddressLine(0));
            Log.d(TAG, "getAddress: getAdminArea = " + locationAddress.getAdminArea());
            Log.d(TAG, "getAddress: getCountryCode = " + locationAddress.getCountryCode());
            Log.d(TAG, "getAddress: getCountryName = " + locationAddress.getCountryName());
            Log.d(TAG, "getAddress: getFeatureName = " + locationAddress.getFeatureName());
            Log.d(TAG, "getAddress: getLocale = " + locationAddress.getLocale());
            Log.d(TAG, "getAddress: getPostalCode = " + locationAddress.getPostalCode());
            Log.d(TAG, "getAddress: getPremises = " + locationAddress.getPremises());
            Log.d(TAG, "getAddress: getSubAdminArea = " + locationAddress.getSubAdminArea());
            Log.d(TAG, "getAddress: getSubLocality = " + locationAddress.getSubLocality());
            Log.d(TAG, "getAddress: getSubThoroughfare = " + locationAddress.getSubThoroughfare());
            Log.d(TAG, "getAddress: getThoroughfare = " + locationAddress.getThoroughfare());
            Log.d(TAG, "getAddress: getUrl = " + locationAddress.getUrl());

            if(!TextUtils.isEmpty(address)) {
                currentLocation=address;
                if (!TextUtils.isEmpty(address1)){
                    currentLocation+="\n"+address1;
                }
                tvAddress.setText(currentLocation);
                sharedPreference.setSharedPrefLocation(currentLocation);
                sharedPreference.setSharedPrefLatlng(latitude,longitude);

                getPrayerTime(date, currentLocation, method, tuning, school, lat);
            }
        }
    }

    private void getPrayerTime(String date, String address, String method, String tune, String school, String lat){
        Call<PrayerObject> result = PrayerApi.getAPIService().getTimingByAddress(date, address, method, tune, school, lat);
        result.enqueue(new Callback<PrayerObject>() {
            @Override
            public void onResponse(Call<PrayerObject> call, retrofit2.Response<PrayerObject> response) {
                try {
                    if(response.body()!=null) {
                        String status = response.body().getStatus();
                        if (status.equals("OK")) {
                            DataPrayer dataPrayer = response.body().getDataPrayer();
                            Timings timings = dataPrayer.getTimings();
                            com.birutekno.bsoleh.model.Date dateData = dataPrayer.getDate();
                            tvSubuhPrayer.setText(timings.getFajr());
                            tvDzuhurPrayer.setText(timings.getDhuhr());
                            tvAsrPrayer.setText(timings.getAsr());
                            tvMagribPrayer.setText(timings.getMaghrib());
                            tvIshaPrayer.setText(timings.getIsha());

                            Hijri hijri = dateData.getHijri();
                            String day = hijri.getDay();
                            com.birutekno.bsoleh.model.Month month = hijri.getMonth();
                            String monthName = month.getEn();
                            String year = hijri.getYear();

                            tvHijriDate.setText(day + " " + monthName + " " + year + "H");

                            toastUtil.makeToast("Success","success",true);
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

    @Override
    public void onRefresh() {
        if (checkPlayServices()){
            buildGoogleApiClient();
            swipeRefreshLayout.setRefreshing(false);
            toastUtil.makeToast("Data & Location Refreshed", "success", true);
        }else {
            getLocation();
            swipeRefreshLayout.setRefreshing(false);
            toastUtil.makeToast("Data & Location Refreshed", "success", true);
        }
    }
}
