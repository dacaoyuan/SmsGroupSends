package com.eveyoo.sendmessagedaemon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.eveyoo.sendmessagedaemon.utils.Constans;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DetailResultActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_result);
        ButterKnife.inject(this);

        antipateNumbers.setText(Constans.antipateNumbers + "");


    }
}
