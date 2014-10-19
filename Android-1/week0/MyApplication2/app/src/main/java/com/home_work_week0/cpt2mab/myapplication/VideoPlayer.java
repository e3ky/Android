package com.home_work_week0.cpt2mab.myapplication;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;


public class VideoPlayer extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);


        ImageView prev = (ImageView) findViewById(R.id.previous);
        final ImageView play = (ImageView) findViewById(R.id.play_or_pause);
        ImageView next = (ImageView) findViewById(R.id.next);
        final VideoView video = (VideoView) findViewById(R.id.videoView);

        final File videoFile = new File(Environment.getExternalStorageDirectory(),"video/Ronaldo_Dive_Moti.mp4");
        video.setVideoURI(Uri.fromFile(videoFile));

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(video.canSeekBackward()){
                    video.seekTo(video.getCurrentPosition() - 3000);

                }
                //Toast.makeText(VideoPlayer.this, "The file path is:" + videoFile.toString(), Toast.LENGTH_LONG).show();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(video.isPlaying()){
                    play.setImageDrawable(getResources().getDrawable(R.drawable.play));
                    video.pause();

                } else {
                    play.setImageDrawable(getResources().getDrawable(R.drawable.pause));
                    video.start();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(video.canSeekForward()){
                        video.seekTo(video.getCurrentPosition() + 3000);
                }
            }
        });


      //  File baseDirectory = Environment.getExternalStorageDirectory();






       // prev.setOnClickListener();
    }



}
