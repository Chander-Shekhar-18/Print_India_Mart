package com.zaptas.printindiamart.startingscreen;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.zaptas.printindiamart.MainActivity;
import com.zaptas.printindiamart.R;

public class SplashSreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sreen);
//        getSupportActionBar().hide();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashSreen.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        thread.start();
    }
}
