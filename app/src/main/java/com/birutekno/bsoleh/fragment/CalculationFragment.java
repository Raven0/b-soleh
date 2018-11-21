package com.birutekno.bsoleh.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.birutekno.bsoleh.R;
import com.birutekno.bsoleh.constant.Constant;
import com.birutekno.bsoleh.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CalculationFragment extends Fragment {

    ToastUtil toastUtil;

    @BindView(R.id.nsZakatMaal)
    NestedScrollView nsZakatMaal;

    @BindView(R.id.nsZakatFitrah)
    NestedScrollView nsZakatFitrah;

    @BindView(R.id.llZakatMaal)
    LinearLayout llZakatMaal;

    @BindView(R.id.llZakatFitrah)
    LinearLayout llZakatFitrah;

    @BindView(R.id.etBeras)
    EditText etBeras;

    @BindView(R.id.etSoulValue)
    EditText etSoulValue;

    @BindView(R.id.etSoulQty)
    EditText etSoulQty;

    @BindView(R.id.etFitrahMoney)
    EditText etFitrahMoney;

    @BindView(R.id.etFitrahWeight)
    EditText etFitrahWeight;

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
        toastUtil = new ToastUtil(view.getContext());
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

    @OnClick(R.id.btnCalculateFitrah)
    public  void btnCalculateFitrah(){
        calculateFitrah();
    }

    private void calculateFitrah(){
        float priceBeras;
        float soulValue;
        int soulQty;
        float fitrahMoney;
        float fitrahWeight;

        priceBeras = Float.parseFloat(etBeras.getText().toString().replace(",",""));
        soulValue = priceBeras * Constant.RICE_KG;
        etSoulValue.setText(String.valueOf(soulValue));
        soulQty = Integer.parseInt(etSoulQty.getText().toString().replace(",",""));
        fitrahMoney = soulValue * soulQty;
        fitrahWeight = soulValue / Constant.RICE_KG;
        etFitrahMoney.setText(String.valueOf(fitrahMoney));
        etFitrahWeight.setText(String.valueOf(fitrahWeight));
    }
}
