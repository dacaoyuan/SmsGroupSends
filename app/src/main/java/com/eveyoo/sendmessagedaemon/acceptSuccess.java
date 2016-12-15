package com.eveyoo.sendmessagedaemon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2016/12/14.
 */

public class acceptSuccess extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("BroadcastReceiver acceptSuccess.onReceive");

        String acceptPhone = intent.getStringExtra("phoneNumber");
        System.out.println("acceptSuccess.onReceive acceptPhone=" + acceptPhone);


    }
}
