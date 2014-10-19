package com.home_work_week0.cpt2mab.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


        Button showColorPreviewerBasicButton = (Button) findViewById(R.id.color_previewer_basic_button);
        Button showColorPreviewerRealTimeButton = (Button) findViewById(R.id.color_previewer_real_time_button);
        Button showVideoPlayerButton = (Button) findViewById(R.id.video_player);
        Button showFlagsEasyButton = (Button) findViewById(R.id.flags_easy);
        Button showFlagsMediumButton = (Button) findViewById(R.id.flags_medium);
        Button showFlagsHardButton = (Button) findViewById(R.id.flags_hard);

        showColorPreviewerBasicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setContentView(R.layout.color_previewer_basic);
                Intent intent;
                intent = new Intent(MyActivity.this,ColorPreviewerBasic.class);
                startActivity(intent);

            }
        });

        showColorPreviewerRealTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(MyActivity.this, ColorPreviewerRealTime.class);
                startActivity(intent);
            }
        });


        showVideoPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(MyActivity.this,VideoPlayer.class);
                startActivity(intent);
            }
        });

        showFlagsEasyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;

                intent = new Intent(MyActivity.this,FlagsEasy.class);
                startActivity(intent);
            }
        });

        showFlagsMediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(MyActivity.this, FlagsMedium.class);
                startActivity(intent);
            }
        });

        showFlagsHardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(MyActivity.this, FlagsHard.class);
                startActivity(intent);
            }
        });


    }


}
