package com.week5.cpt2mab.multipleactivities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        final EditText content = (EditText) findViewById(R.id.content);
        Button dialButton = (Button) findViewById(R.id.dial_btn);
        Button browseButton = (Button) findViewById(R.id.browse_btn);
        Button setAlarmButton = (Button) findViewById(R.id.set_alarm_btn);

        dialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dial = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + content.getText().toString()));
                startActivity(dial);
            }
        });

        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + content.getText().toString()));
                startActivity(browse);
            }
        });

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alarm = new Intent(AlarmClock.ACTION_SET_ALARM);

                String [] time = content.getText().toString().split(":");
                alarm.putExtra(AlarmClock.EXTRA_HOUR,Integer.parseInt(time[0]));
                alarm.putExtra(AlarmClock.EXTRA_MINUTES,Integer.parseInt(time[1]));

                startActivity(alarm);
            }
        });
    }

}
