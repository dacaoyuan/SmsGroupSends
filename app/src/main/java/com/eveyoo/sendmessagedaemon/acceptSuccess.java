package com.eveyoo.sendmessagedaemon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.elvishew.xlog.XLog;
import com.eveyoo.sendmessagedaemon.utils.Constans;
import com.eveyoo.sendmessagedaemon.utils.PhoneNumberUtils;

import java.util.Observable;

/**
 * Created by Administrator on 2016/12/14.
 */

public class acceptSuccess extends BroadcastReceiver {
    private static final String TAG = "acceptSuccess  ";

    @Override
    public void onReceive(Context context, Intent intent) {
        XLog.d(TAG + "BroadcastReceiver acceptSuccess.onReceive intent.getAction=" + intent.getAction());
        String acceptPhone = intent.getStringExtra("phoneNumber") + ",";
        XLog.d(TAG + "BroadcastReceiver acceptSuccess.onReceive acceptPhone=" + acceptPhone);

        //方案一：存放到Arraylist中
        //方案二：存放到shprs中

        PhoneNumberUtils.savaPhoneNumber(context, acceptPhone);


        int numbers = 1;
        numbers = numbers + Constans.acceptSuccessNumbers;
        Constans.acceptSuccessNumbers = numbers;

        Intent intent1 = new Intent("action_detailResultReceiver");
        context.sendBroadcast(intent1);

        // XLog.d(TAG + "antipate Numbers=" + Constans.antipateNumbers);
        XLog.d(TAG + "Success numbers=" + numbers);

    }
}
