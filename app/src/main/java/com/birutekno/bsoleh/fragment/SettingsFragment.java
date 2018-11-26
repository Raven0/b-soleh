package com.birutekno.bsoleh.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.birutekno.bsoleh.PrayerSettingsActivity;
import com.birutekno.bsoleh.R;
import com.birutekno.bsoleh.util.SharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingsFragment extends Fragment{

    SharedPreference sharedPreference;

    private AudioManager audioManager = null;

    @BindView(R.id.swPermission)
    Switch swPermission;

    @BindView(R.id.sbSound)
    SeekBar sbSound;

    @BindView(R.id.tvSound)
    TextView tvSound;

    public SettingsFragment() {

    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initViews(view);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initViews(View view){
        ButterKnife.bind(this, view);

        sharedPreference = new SharedPreference(view.getContext());

        SharedPreferences preferences = sharedPreference.getSharedPrefPrayer();
        swPermission.setChecked(preferences.getBoolean("prayer_notif_master", false));

        initControls();
        sbSound.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_RING));
        sbSound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_RING, i, 0);
                tvSound.setText("" + i + "%"); // here in textView the percent will be shown
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_RING));
            }
        });
    }

    private void initControls()
    {
        try
        {
            audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
            sbSound.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
            sbSound.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_RING));


            sbSound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.rlPrayerSettings)
    public void rlPrayerSettings(){
        startActivity(new Intent(getContext(), PrayerSettingsActivity.class));
    }

    @OnClick(R.id.swPermission)
    public void swPermission(){
        SharedPreferences preferences = sharedPreference.getSharedPrefPrayer();

        if (!preferences.getBoolean("prayer_notif_master", false)){
            sharedPreference.setSharedPrefPrayerMaster(true);
        }else {
            sharedPreference.setSharedPrefPrayerMaster(false);
        }
    }

    @OnClick(R.id.rlAbout)
    public void rlAbout(){
//        startActivity(new Intent(getContext(), AlarmActivity.class));
    }
}
