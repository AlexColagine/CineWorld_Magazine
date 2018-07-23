package com.alex.android.cineworld.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.example.android.cineworld.R;

/**
 * Created by Alessandro on 20/03/2018.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread(new Runnable() {
            @Override
            public void run() {
                setupProgress();
                startActivity();
            }
        }).start();
    }

    private void setupProgress(){
        for (int i = 0; i < 100; i++){
            try {
                Thread.sleep(15);
                ProgressBar mProgress = (ProgressBar) findViewById(R.id.progressBar);
                mProgress.setProgress(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void startActivity(){
        Intent launchApp = new Intent(this , MainActivity.class);
        startActivity(launchApp);
        finish();
    }
}
