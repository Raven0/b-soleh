package com.birutekno.bsoleh;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.birutekno.bsoleh.util.SharedPreference;

public class SplashscreenActivity extends AppCompatActivity {

    SharedPreference sharedPreference = new SharedPreference(this);
    private static int splashInterval = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPreference.getSharedPrefIntro()){
                    startActivity(new Intent(SplashscreenActivity.this, MainActivity.class));
                }else {
                    startActivity(new Intent(SplashscreenActivity.this, HijrahActivity.class));
                }
            }
        }, splashInterval);

    };
}
