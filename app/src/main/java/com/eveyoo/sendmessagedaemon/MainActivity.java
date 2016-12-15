package com.eveyoo.sendmessagedaemon;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


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





        contentText = tell.getText().toString().trim();
        System.out.println("MainActivity.onCreate photoText=" + phoneNumber);
        System.out.println("MainActivity.onCreate contextText=" + contentText);


        Intent sentIntent = new Intent("SENDMESSAGESUCCESS");
        sentIntent.putExtra("phoneNumber", phoneNumber);
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent, 0);

        Intent acceptIntent = new Intent("ACCEPTMESSAGESUCCESS");
        acceptIntent.putExtra("phoneNumber", phoneNumber);
        PendingIntent acceptPI = PendingIntent.getBroadcast(this, 0, acceptIntent, 0);
        
        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(MainActivity.this, "手机号不能为空", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(contentText)) {
            Toast.makeText(MainActivity.this, " contextText not null", Toast.LENGTH_LONG).show();
        } else {
            SmsManager smsManager = SmsManager.getDefault();
            if (contentText.length() > 70) {
                List<String> contents = smsManager.divideMessage(contentText);
                for (String sms : contents) {
                    smsManager.sendTextMessage(phoneNumber, null, sms, sentPI, acceptPI);
                }
            } else {
                smsManager.sendTextMessage(phoneNumber, null, contentText, sentPI, acceptPI);
            }
            Toast.makeText(MainActivity.this, "发送内容不能为空", Toast.LENGTH_SHORT).show();
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
                System.out.println("MainActivity.onClick checkSelfPermission");
                return;
            }
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        }

    }


}
