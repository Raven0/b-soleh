package com.birutekno.bsoleh;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.bsoleh.constant.Constant;
import com.birutekno.bsoleh.util.SharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrayerSettingsActivity extends AppCompatActivity {

    SharedPreference sharedPreference = new SharedPreference(this);
    String method;
    String methodName;
    String tuneSubuh;
    String tuneDzuhur;
    String tuneAshar;
    String tuneMagrib;
    String tuneIsya;
    String school;
    String schoolName;
    String lat;
    String latName;

    @BindView(R.id.tvMethod)
    TextView tvMethod;

    @BindView(R.id.tvTune)
    TextView tvTune;

    @BindView(R.id.tvSchool)
    TextView tvSchool;

    @BindView(R.id.tvLat)
    TextView tvLat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_settings);
        initViews();
    }

    private void initViews(){
        ButterKnife.bind(this);
        SharedPreferences preferences = sharedPreference.getSharedPrefPrayer();
        method = preferences.getString("method", "2");
        methodName = preferences.getString("methodName", "3. Muslim World League");
        tuneSubuh = preferences.getString("subuh", "0");
        tuneDzuhur = preferences.getString("dzuhur", "0");
        tuneAshar = preferences.getString("asr", "0");
        tuneMagrib = preferences.getString("magrib", "0");
        tuneIsya = preferences.getString("isya", "0");
        school = preferences.getString("school", "0");
        schoolName = preferences.getString("schoolName", "1. Standard(Shafi, Maliki, Hanbali)");
        lat = preferences.getString("lat", "1");
        latName = preferences.getString("latName", "1. Middle of the Night");

        String tuning = tuneSubuh + "," + tuneDzuhur + "," + tuneAshar + "," + tuneMagrib + "," + tuneIsya;

        tvMethod.setText(methodName);
        tvTune.setText(tuning);
        tvSchool.setText(schoolName);
        tvLat.setText(latName);
    }

    public void conventionDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Calculation Method").setItems(Constant.METHOD_NAME, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // The 'which' argument contains the index position
                // of the selected item
                Toast.makeText(PrayerSettingsActivity.this, Constant.METHOD_NAME[which], Toast.LENGTH_SHORT).show();
                sharedPreference.setSharedPrefPrayer(Constant.METHOD_ID[which], Constant.METHOD_NAME[which], school, schoolName, lat, latName);
                finish();
                startActivity(getIntent());
            }
        });
        builder.create();
        builder.show();
    }

    public void tuneDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View Viewlayout = inflater.inflate(R.layout.dialog_tuning, (ViewGroup) findViewById(R.id.llDialogTuning));

        final String[] tunes = new String[5];

        final TextView tvSubuh = (TextView)Viewlayout.findViewById(R.id.tvSubuhValue);
        final TextView tvDzuhur = (TextView)Viewlayout.findViewById(R.id.tvDzuhurValue);
        final TextView tvAsr = (TextView)Viewlayout.findViewById(R.id.tvAsharValue);
        final TextView tvMagrib = (TextView)Viewlayout.findViewById(R.id.tvMagribValue);
        final TextView tvIsya = (TextView)Viewlayout.findViewById(R.id.tvIshaaValue);

        tvSubuh.setText(tuneSubuh + " min");
        tvDzuhur.setText(tuneDzuhur + " min");
        tvAsr.setText(tuneAshar + " min");
        tvMagrib.setText(tuneMagrib + " min");
        tvIsya.setText(tuneIsya + " min");

        tunes[0] = tuneSubuh;
        tunes[1] = tuneDzuhur;
        tunes[2] = tuneAshar;
        tunes[3] = tuneMagrib;
        tunes[4] = tuneIsya;

        builder.setTitle("Prayer Time Manual Correction");
        builder.setView(Viewlayout);

        SeekBar sbSubuh = (SeekBar) Viewlayout.findViewById(R.id.sbSubuh);
        sbSubuh.setMax(120);
        sbSubuh.setProgress(Integer.parseInt(tuneSubuh)+60);
        sbSubuh.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                float ratio = (float)progress / (float)seekBar.getMax();
                int minutes = Math.round(ratio * 120 - 60);
                tvSubuh.setText(String.valueOf(minutes) + " min");
                tunes[0]=String.valueOf(minutes);
            }

            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }
        });

        SeekBar sbDzuhur = (SeekBar) Viewlayout.findViewById(R.id.sbDzuhur);
        sbDzuhur.setMax(120);
        sbDzuhur.setProgress(Integer.parseInt(tuneDzuhur)+60);
        sbDzuhur.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                float ratio = (float)progress / (float)seekBar.getMax();
                int minutes = Math.round(ratio * 120 - 60);
                tvDzuhur.setText(String.valueOf(minutes) + " min");
                tunes[1]=String.valueOf(minutes);
            }

            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }
        });

        SeekBar sbAshar = (SeekBar) Viewlayout.findViewById(R.id.sbAshar);
        sbAshar.setMax(120);
        sbAshar.setProgress(Integer.parseInt(tuneAshar)+60);
        sbAshar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                float ratio = (float)progress / (float)seekBar.getMax();
                int minutes = Math.round(ratio * 120 - 60);
                tvAsr.setText(String.valueOf(minutes) + " min");
                tunes[2]=String.valueOf(minutes);
            }

            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }
        });

        SeekBar sbMagrib = (SeekBar) Viewlayout.findViewById(R.id.sbMagrib);
        sbMagrib.setMax(120);
        sbMagrib.setProgress(Integer.parseInt(tuneMagrib)+60);
        sbMagrib.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                float ratio = (float)progress / (float)seekBar.getMax();
                int minutes = Math.round(ratio * 120 - 60);
                tvMagrib.setText(String.valueOf(minutes) + " min");
                tunes[3]=String.valueOf(minutes);
            }

            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }
        });

        SeekBar sbIshaa = (SeekBar) Viewlayout.findViewById(R.id.sbIshaa);
        sbIshaa.setMax(120);
        sbIshaa.setProgress(Integer.parseInt(tuneIsya)+60);
        sbIshaa.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                float ratio = (float)progress / (float)seekBar.getMax();
                int minutes = Math.round(ratio * 120 - 60);
                tvIsya.setText(String.valueOf(minutes) + " min");
                tunes[4]=String.valueOf(minutes);
            }

            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String tuning = tunes[0] + "," + tunes[1] + "," + tunes[2] + "," + tunes[3] + "," + tunes[4];
                Toast.makeText(PrayerSettingsActivity.this, tuning, Toast.LENGTH_SHORT).show();
                sharedPreference.setSharedPrefTuning(tunes[0], tunes[1], tunes[2], tunes[3], tunes[4]);
                finish();
                startActivity(getIntent());
                dialog.dismiss();
            }
        });

        builder.create();
        builder.show();
    }

    public void asrDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Calculation Method").setItems(Constant.SCHOOL_NAME, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // The 'which' argument contains the index position
                // of the selected item
                Toast.makeText(PrayerSettingsActivity.this, Constant.SCHOOL_NAME[which], Toast.LENGTH_SHORT).show();
                sharedPreference.setSharedPrefPrayer(method, methodName, Constant.SCHOOL_ID[which], Constant.SCHOOL_NAME[which], lat, latName);
                finish();
                startActivity(getIntent());

            }
        });
        builder.create();
        builder.show();
    }

    public void latDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Calculation Method").setItems(Constant.LAT_NAME, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // The 'which' argument contains the index position
                // of the selected item
                Toast.makeText(PrayerSettingsActivity.this, Constant.LAT_NAME[which], Toast.LENGTH_SHORT).show();
                sharedPreference.setSharedPrefPrayer(method, methodName, school, schoolName, Constant.LAT_ID[which], Constant.LAT_NAME[which]);
                finish();
                startActivity(getIntent());
            }
        });
        builder.create();
        builder.show();
    }

    @OnClick(R.id.rlConventionSettings)
    public void rlConventionSettings(){
        //method
        conventionDialog();
    }

    @OnClick(R.id.rlCorrectionSettings)
    public void rlCorrectionSettings(){
        //tune
        tuneDialog();
    }

    @OnClick(R.id.rlAsrSettings)
    public void rlAsrSettings(){
        //school
        asrDialog();
    }

    @OnClick(R.id.rlLatitudeSettings)
    public void rlLatitudeSettings(){
        //latitudeAdjustmentMethod
        latDialog();
    }
}
