package com.eveyoo.sendmessagedaemon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.eveyoo.sendmessagedaemon.utils.Constans;
import com.eveyoo.sendmessagedaemon.utils.PhoneNumberUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DetailResultActivity extends AppCompatActivity {
    private static final String TAG = "DetailResultActivity  ";
    @InjectView(R.id.antipate_numbers)
    TextView antipateNumbers;
    @InjectView(R.id.failure_numbers)
    TextView failureNumbers;
    @InjectView(R.id.failure_phone_number)
    TextView failurePhoneNumber;
    @InjectView(R.id.succeed_numbers)
    TextView succeedNumbers;
    @InjectView(R.id.succeed_phone_number)
    TextView succeedPhoneNumber;

    private IntentFilter intentFilter;
    private DetailResultReceiver resultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_result);
        ButterKnife.inject(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction("action_detailResultReceiver");
        resultReceiver = new DetailResultReceiver();
        registerReceiver(resultReceiver, intentFilter);


        antipateNumbers.setText(Constans.antipateNumbers + "");


        int failure = Constans.antipateNumbers - Constans.acceptSuccessNumbers;
        //failureNumbers.setText(failure + "");

    }

    class DetailResultReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            XLog.d(TAG + "DetailResultReceiver intent.getAction()=" + intent.getAction());
            succeedNumbers.setText(Constans.acceptSuccessNumbers + "");
            succeedPhoneNumber.setText(PhoneNumberUtils.readPhoneNumber(context));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(resultReceiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        PhoneNumberUtils.deletePhoneNumber(this);
        finish();
        return super.onKeyDown(keyCode, event);
    }

}
