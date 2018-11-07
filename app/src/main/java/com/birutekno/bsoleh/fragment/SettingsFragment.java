package com.birutekno.bsoleh.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.birutekno.bsoleh.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SettingsFragment extends Fragment{

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

        String initProgress = String.valueOf(sbSound.getProgress());
        tvSound.setText(""+initProgress+"%");
        sbSound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar bar) {
                int value = bar.getProgress(); // the value of the seekBar progress
            }

            public void onStartTrackingTouch(SeekBar bar) {
            }

            public void onProgressChanged(SeekBar bar, int paramInt, boolean paramBoolean) {
                tvSound.setText("" + paramInt + "%"); // here in textView the percent will be shown
            }
        });
    }
}
