package com.example.daffodil.medidictionary;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //it is launcher activity,will run first on app start

        //seconnd to delay the thread
        int secondsDelayed = 1;
        //it is a thread to delay for 1 sec to go to main activity
        new Handler().postDelayed(new Runnable() {
            public void run() {

                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();

            }
        }, secondsDelayed * 1000);
    }
}