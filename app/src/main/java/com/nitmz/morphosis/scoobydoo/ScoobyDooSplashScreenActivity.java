package com.nitmz.morphosis.scoobydoo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.nitmz.morphosis.LoginActivity;
import com.nitmz.morphosis.R;

public class ScoobyDooSplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 100;

    SharedPreferences status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scooby_doo_splash_screen);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            //window.setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLoginStatus();
            }
        }, SPLASH_TIME_OUT);
    }

    private void checkLoginStatus() {
        status = getSharedPreferences("login_status", Context.MODE_PRIVATE);
        boolean  logIn = status.getBoolean("in", false);
        if (logIn) {
            Intent intent = new Intent(ScoobyDooSplashScreenActivity.this, ScoobyDooHomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(ScoobyDooSplashScreenActivity.this, LoginActivity.class);
            intent.putExtra("launch", 2);
            startActivity(intent);
        }
        finish();
    }
}
