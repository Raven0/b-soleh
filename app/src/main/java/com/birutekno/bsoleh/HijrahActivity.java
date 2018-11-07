package com.birutekno.bsoleh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.birutekno.bsoleh.util.SharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HijrahActivity extends AppCompatActivity {

    SharedPreference sharedPreference = new SharedPreference(this);

    @BindView(R.id.btnHijrah)
    Button btnHijrah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hijrah);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnHijrah)
    public void btnHijrah(){
        sharedPreference.setSharedPrefIntro();
        startActivity(new Intent(this, MainActivity.class));
    }
}
