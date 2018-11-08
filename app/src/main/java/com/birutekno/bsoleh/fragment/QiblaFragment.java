package com.birutekno.bsoleh.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.bsoleh.R;
import com.birutekno.bsoleh.util.SharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;


public class QiblaFragment extends Fragment implements SensorEventListener {

    SharedPreference sharedPreference;
    private float currentDegree = 0f;
    private float currentDegreeNeedle = 0f;
    private SensorManager sensorManager;
    Location userLoc=new Location("service Provider");

    @BindView(R.id.ivCompass)
    ImageView ivCompass;

//    @BindView(R.id.ivBCompass)
//    ImageView ivBCompass;

    @BindView(R.id.tvBearing)
    TextView tvBearing;

    public QiblaFragment() {

    }

    public static QiblaFragment newInstance() {
        QiblaFragment fragment = new QiblaFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qibla, container, false);
        initViews(view);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initViews(View view){
        ButterKnife.bind(this,view);

        sharedPreference = new SharedPreference(view.getContext());
        SharedPreferences preferences = sharedPreference.getSharedPrefLatlng();
        Toast.makeText(getContext(), "LONG" + String.valueOf(preferences.getLong("longitude", 0)), Toast.LENGTH_SHORT).show();
        userLoc.setLongitude(Double.longBitsToDouble(preferences.getLong("longitude", 0)));
        userLoc.setLatitude(Double.longBitsToDouble(preferences.getLong("latitude", 0)));
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float degree = Math.round(sensorEvent.values[0]);

        Location destinationLoc = new Location("service Provider");
        destinationLoc.setLatitude(21.422487); //kaaba latitude setting
        destinationLoc.setLongitude(39.826206); //kaaba longitude setting

        //bearTo = The angle from true north to the destination location from the point we're your currently standing.(asal image k N se destination taak angle )
        float bearTo = userLoc.bearingTo(destinationLoc);
        //head = The angle that you've rotated your phone from true north. (jaise image lagi hai wo true north per hai ab phone jitne rotate yani jitna image ka n change hai us ka angle hai ye)
        float head = degree;

        GeomagneticField geoField = new GeomagneticField(Double.valueOf(userLoc.getLatitude()).floatValue(), Double
                .valueOf(userLoc.getLongitude()).floatValue(),
                Double.valueOf(userLoc.getAltitude()).floatValue(),
                System.currentTimeMillis());
        head -= geoField.getDeclination();
        if (bearTo < 0) {
            bearTo = bearTo + 360;
        }
        //This is where we choose to point it
        float direction = bearTo - head;
        if (direction < 0) {
            direction = direction + 360;
        }
        tvBearing.setText("Heading: " + Float.toString(degree) + " degrees.\nQibla : " + Float.toString(bearTo) + " degrees.\nCurrent Location : "+sharedPreference.getSharedPrefLocation());
        RotateAnimation raQibla = new RotateAnimation(currentDegreeNeedle, direction, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        raQibla.setDuration(210);
        raQibla.setFillAfter(true);
        ivCompass.startAnimation(raQibla);
        currentDegreeNeedle = direction;

        //Regular North Pointing arrow
//        RotateAnimation ra = new RotateAnimation(currentDegree, -degree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        ra.setDuration(210);
//        ra.setFillAfter(true);
//        ivCompass.startAnimation(ra);
//        currentDegree = -degree;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
