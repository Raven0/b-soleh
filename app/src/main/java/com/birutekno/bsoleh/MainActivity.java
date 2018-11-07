package com.birutekno.bsoleh;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.birutekno.bsoleh.fragment.CalculationFragment;
import com.birutekno.bsoleh.fragment.ScheduleFragment;
import com.birutekno.bsoleh.fragment.SettingsFragment;
import com.birutekno.bsoleh.util.ActivityUtils;
import com.birutekno.bsoleh.util.SharedPreference;
import com.birutekno.bsoleh.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    SharedPreference sharedPreference = new SharedPreference(this);
    ToastUtil toastUtil = new ToastUtil(this);

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
        ButterKnife.bind(this);
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment   = null;
                switch (item.getItemId()) {
                    case R.id.nav_schedule:
                        fragment = new ScheduleFragment();
                        break;
                    case R.id.nav_qibla:
                        fragment = new SettingsFragment();
                        break;
                    case R.id.nav_calculation:
                        fragment = new CalculationFragment();
                        break;
                    case R.id.nav_setting:
                        fragment = new SettingsFragment();
                        break;
                }
                ActivityUtils.replaceFragment(getSupportFragmentManager(), fragment, R.id.frame);
                return true;
            }
        };
        //Default Page
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        ActivityUtils.replaceFragment(getSupportFragmentManager(), new ScheduleFragment(), R.id.frame);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
