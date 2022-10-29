package com.example.empressnotes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

import com.example.empressnotes.R;

public class FlashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);
        getSupportActionBar().hide();

        VideoView videoview = (VideoView) findViewById(R.id.logoVideo);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.empress);
        videoview.setVideoURI(uri);
        videoview.start();

        final Intent i = new Intent(FlashScreen.this, MainActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                finish();
            }
        }, 3000);
    }
}

