package com.birutekno.bsoleh;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.birutekno.bsoleh.fragment.CalculationFragment;
import com.birutekno.bsoleh.fragment.QiblaFragment;
import com.birutekno.bsoleh.fragment.ScheduleFragment;
import com.birutekno.bsoleh.fragment.SettingsFragment;
import com.birutekno.bsoleh.util.ActivityUtils;
import com.birutekno.bsoleh.util.PermissionUtils;
import com.birutekno.bsoleh.util.SharedPreference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, PermissionUtils.PermissionResultCallback{

    SharedPreference sharedPreference = new SharedPreference(this);
    PermissionUtils permissionUtils;
    ArrayList<String> permissions=new ArrayList<>();
    boolean isPermissionGranted;


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
                        fragment = new QiblaFragment();
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

        //Permission
        if (!sharedPreference.getSharedPrefGooglePermission()){
            permissionUtils = new PermissionUtils(MainActivity.this);
            permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            permissionUtils.check_permission(permissions,"Need GPS permission for getting your location",1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // redirects to utils
        permissionUtils.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    public void PermissionGranted(int request_code) {
        sharedPreference.setSharedPrefGooglePermission();
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {

    }

    @Override
    public void PermissionDenied(int request_code) {

    }

    @Override
    public void NeverAskAgain(int request_code) {

    }
}
