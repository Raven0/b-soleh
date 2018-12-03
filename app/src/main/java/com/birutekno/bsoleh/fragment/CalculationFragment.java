package com.birutekno.bsoleh.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

    @BindView(R.id.etUang)
    EditText etUang;

    @BindView(R.id.etProperti)
    EditText etProperti;

    @BindView(R.id.etJewerly)
    EditText etJewerly;

    @BindView(R.id.etOther)
    EditText etOther;

    @BindView(R.id.etTotal)
    EditText etTotal;

    @BindView(R.id.etGoldperGram)
    EditText etGoldperGram;

    @BindView(R.id.etNishab)
    EditText etNishab;

    @BindView(R.id.etZakatMaal)
    EditText etZakatMaal;

    private long priceBeras;
    private long soulValue;
    private long valueUang;
    private long valueProperti;
    private long valueJewelry;
    private long valueOther;
    private long valueTotal;
    private long valueGold;
    private long nishab;

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

        // Zakat Fitrah
        etBeras.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(etBeras.getText())){
                    priceBeras = 0;
                } else {
                    try {
                        priceBeras = Long.parseLong(etBeras.getText().toString().replace(",","").replace(".",""));
                        soulValue = priceBeras * Constant.RICE_LITRE;
                        etSoulValue.setText(String.valueOf(soulValue));
                    }catch (Exception ex){
                        etBeras.setText("0");
                    }
                }
            }
        });

        // Zakat Maal
        etUang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(etUang.getText())){
                    valueUang = 0;
                } else {
                    try {
                        valueUang = Long.parseLong(etUang.getText().toString().replace(",","").replace(".",""));
                        valueTotal = valueUang + valueProperti + valueJewelry + valueOther;
                        etTotal.setText(String.valueOf(valueTotal));
                    }catch (Exception ex){
                        etUang.setText("0");
                    }
                }
            }
        });

        etProperti.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(etProperti.getText())){
                    valueProperti = 0;
                } else {
                    try {
                        valueProperti = Long.parseLong(etProperti.getText().toString().replace(",","").replace(".",""));
                        valueTotal = valueUang + valueProperti + valueJewelry + valueOther;
                        etTotal.setText(String.valueOf(valueTotal));
                    }catch (Exception ex){
                        etProperti.setText("0");
                    }
                }
            }
        });

        etJewerly.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(etJewerly.getText())){
                    valueJewelry = 0;
                } else {
                    try {
                        valueJewelry = Long.parseLong(etJewerly.getText().toString().replace(",","").replace(".",""));
                        valueTotal = valueUang + valueProperti + valueJewelry + valueOther;
                        etTotal.setText(String.valueOf(valueTotal));
                    }catch (Exception ex){
                        etJewerly.setText("0");
                    }
                }
            }
        });

        etOther.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(etOther.getText())){
                    valueOther = 0;
                } else {
                    try {
                        valueOther = Long.parseLong(etOther.getText().toString().replace(",","").replace(".",""));
                        valueTotal = valueUang + valueProperti + valueJewelry + valueOther;
                        etTotal.setText(String.valueOf(valueTotal));
                    }catch (Exception ex){
                        etOther.setText("0");
                    }
                }
            }
        });

        etGoldperGram.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(etGoldperGram.getText())){
                    valueGold = 0;
                } else {
                    try {
                        valueGold = Long.parseLong(etGoldperGram.getText().toString().replace(",","").replace(".",""));
                        nishab = (long) (valueGold * Constant.NISHAB_CALC);
                        etNishab.setText(String.valueOf(nishab));
                    }catch (Exception ex){
                        etGoldperGram.setText("0");
                    }
                }
            }
        });
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
    public void btnCalculateFitrah(){
        calculateFitrah();
    }

    @OnClick(R.id.btnCalculateMaal)
    public void btnCalculateMaal(){
        calculateMaal();
    }

    private void calculateFitrah(){
        int soulQty;
        float fitrahMoney;
        float fitrahWeight;

        if (TextUtils.isEmpty(etSoulQty.getText())){
            toastUtil.makeToast("Masukkan Jumlah Jiwa!", "warning", false);
        }else {
            soulQty = Integer.parseInt(etSoulQty.getText().toString().replace(",",""));
            fitrahMoney = soulValue * soulQty;
            fitrahWeight = soulValue / Constant.RICE_LITRE;
            etFitrahMoney.setText(String.valueOf(fitrahMoney));
            etFitrahWeight.setText(String.valueOf(fitrahWeight));
        }
    }

    private void calculateMaal(){
        long maalMoney;
        maalMoney = (long) (valueTotal * 0.025);
        etZakatMaal.setText(String.valueOf(maalMoney));
    }
}
