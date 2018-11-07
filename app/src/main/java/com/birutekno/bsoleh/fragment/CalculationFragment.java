package com.birutekno.bsoleh.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.birutekno.bsoleh.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CalculationFragment extends Fragment {

    @BindView(R.id.nsZakatMaal)
    NestedScrollView nsZakatMaal;

    @BindView(R.id.nsZakatFitrah)
    NestedScrollView nsZakatFitrah;

    @BindView(R.id.llZakatMaal)
    LinearLayout llZakatMaal;

    @BindView(R.id.llZakatFitrah)
    LinearLayout llZakatFitrah;

    public CalculationFragment() {

    }

    public static CalculationFragment newInstance() {
        CalculationFragment fragment = new CalculationFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculations, container, false);
        initViews(view);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initViews(View view){
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.llZakatMaal)
    public void llZakatMaal(){
        llZakatMaal.setBackgroundResource(R.color.colorPrimaryDark);
        llZakatFitrah.setBackgroundResource(R.color.colorPrimary);
        nsZakatMaal.setVisibility(View.VISIBLE);
        nsZakatFitrah.setVisibility(View.GONE);
    }

    @OnClick(R.id.llZakatFitrah)
    public void llZakatFitrah(){
        llZakatMaal.setBackgroundResource(R.color.colorPrimary);
        llZakatFitrah.setBackgroundResource(R.color.colorPrimaryDark);
        nsZakatMaal.setVisibility(View.GONE);
        nsZakatFitrah.setVisibility(View.VISIBLE);
    }

}
