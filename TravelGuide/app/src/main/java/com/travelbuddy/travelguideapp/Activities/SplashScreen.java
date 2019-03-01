package com.travelbuddy.travelguideapp.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.travelbuddy.travelguideapp.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {




                Intent i = new Intent(SplashScreen.this,IntroTutorialActivity.class);
                startActivity(i);
                finish();

            }
        },3000);
    }
}
