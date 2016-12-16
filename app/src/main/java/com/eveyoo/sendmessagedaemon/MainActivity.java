package com.eveyoo.sendmessagedaemon;

import android.Manifest;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.eveyoo.sendmessagedaemon.utils.Constans;
import com.eveyoo.sendmessagedaemon.utils.PhoneNumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity ";
    @InjectView(R.id.phone)
    TextInputEditText phone;
    @InjectView(R.id.content)
    TextInputEditText content;
    @InjectView(R.id.send)
    Button send;
    @InjectView(R.id.tell)
    Button tell;
    private String phoneNumber;
    private String contentText;
    private ArrayList<String> phoneNumberList;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("短信发送中,发送结果统计中...");


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageProcessing();
            }
        });

        tell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallHandling();

            }
        });
    }


    /**
     * 短信处理
     */
    private void MessageProcessing() {
        phoneNumber = phone.getText().toString().trim();
        contentText = content.getText().toString().trim();
        XLog.d(TAG + "MessageProcessing photoText=" + phoneNumber);
        XLog.d(TAG + "MessageProcessing contextText=" + contentText);


        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(MainActivity.this, "手机号不能为空", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(contentText)) {
            Toast.makeText(MainActivity.this, "发送内容不能为空", Toast.LENGTH_SHORT).show();
        } else {


            if (PhoneNumberUtils.PhoneNumberInspection(phoneNumber)) {
                Toast.makeText(this, "输入的格式正确！", Toast.LENGTH_SHORT).show();
                phoneNumberList = PhoneNumberUtils.dealWithString(phoneNumber);
                Constans.antipateNumbers = phoneNumberList.size();

                for (String strPhoneNumber : phoneNumberList) {

                    Intent sentIntent = new Intent("SENDMESSAGESUCCESS");
                    sentIntent.putExtra("phoneNumber", strPhoneNumber);
                    PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent, 0);

                    Intent acceptIntent = new Intent("ACCEPTMESSAGESUCCESS");
                    acceptIntent.putExtra("phoneNumber", strPhoneNumber);
                    PendingIntent acceptPI = PendingIntent.getBroadcast(this, 0, acceptIntent, 0);

                    SmsManager smsManager = SmsManager.getDefault();
                    if (contentText.length() > 70) {
                        List<String> contents = smsManager.divideMessage(contentText);
                        for (String sms : contents) {
                            smsManager.sendTextMessage(strPhoneNumber, null, sms, sentPI, acceptPI);
                        }
                    } else {
                        smsManager.sendTextMessage(strPhoneNumber, null, contentText, sentPI, acceptPI);
                    }

                    progressDialog.show();
                    //建议在这里 加上发送等待延时5-10s，在跳转
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            startActivity(new Intent(MainActivity.this, DetailResultActivity.class));
                        }
                    }, 6 * 1000);

                    /*Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                        }
                    }, 6 * 1000);*/


                }

            } else {
                Toast.makeText(this, "格式错误！", Toast.LENGTH_SHORT).show();
            }


        }
    }


    /**
     * 电话处理
     */
    private void CallHandling() {

        phoneNumber = phone.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(MainActivity.this, "手机号不能为空", Toast.LENGTH_LONG).show();
        } else {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                XLog.d("MainActivity.onClick checkSelfPermission");
                return;
            }
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        }

    }


}
