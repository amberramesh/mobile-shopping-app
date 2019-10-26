/*
* Mobile Shopping Application
* Written by Amber Ramesh
 */

package com.example.moonc.mobileshoppingapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1500);
                    if (CurrentUser.getCurrentUser() == null)
                        startActivity(new Intent(Splash.this, Login.class));
                    else
                        startActivity(new Intent(Splash.this, Catalog.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();
    }
}
