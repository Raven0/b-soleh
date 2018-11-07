package com.birutekno.bsoleh.util;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class ToastUtil {
    Context context;

    public ToastUtil(Context context) {
        this.context = context;
    }

    public void makeToast(String message, String type, boolean logo){
        if (type.equals("success")){
            Toasty.success(context, message, Toast.LENGTH_SHORT,logo).show();
        }else if (type.equals("warning")){
            Toasty.warning(context, message, Toast.LENGTH_SHORT,logo).show();
        }else if (type.equals("info")){
            Toasty.info(context, message, Toast.LENGTH_SHORT,logo).show();
        }else if (type.equals("error")){
            Toasty.error(context, message, Toast.LENGTH_SHORT,logo).show();
        }else {
            Toasty.normal(context, message, Toast.LENGTH_SHORT).show();
        }
    }

}
