package com.eveyoo.sendmessagedaemon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.elvishew.xlog.XLog;
import com.eveyoo.sendmessagedaemon.utils.Constans;

/**
 * Created by Administrator on 2016/12/14.
 */

public class acceptSuccess extends BroadcastReceiver {
    private static final String TAG = "acceptSuccess";

    @Override
    public void onReceive(Context context, Intent intent) {
        XLog.d(TAG+ "BroadcastReceiver acceptSuccess.onReceive intent.getAction=" + intent.getAction());
        String acceptPhone = intent.getStringExtra("phoneNumber");
        XLog.d(TAG+ "BroadcastReceiver acceptSuccess.onReceive acceptPhone=" + acceptPhone);


        int numbers = 0;
        numbers++;
        Constans.acceptSuccessNumbers = numbers;

        XLog.d(TAG+ "antipate Numbers=" + Constans.antipateNumbers);
        XLog.d(TAG+"Success numbers=" + numbers);

    }
}
