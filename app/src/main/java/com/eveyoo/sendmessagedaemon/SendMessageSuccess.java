package com.eveyoo.sendmessagedaemon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2016/12/14.
 */

public class SendMessageSuccess extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("BroadcastReceiver SendMessageSuccess.onReceive");

        String sendMessagePhone = intent.getStringExtra("phoneNumber");
        System.out.println("acceptSuccess.onReceive acceptPhone=" + sendMessagePhone);
    }
}
