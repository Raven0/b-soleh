package com.birutekno.bsoleh.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, MyNewIntentService.class);
        intent1.putExtra("prayer", intent.getStringExtra("prayer"));
        intent1.putExtra("time", intent.getLongExtra("time", 0));
        context.startService(intent1);
    }
}
