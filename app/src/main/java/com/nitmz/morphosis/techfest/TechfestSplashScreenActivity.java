package com.nitmz.morphosis.techfest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.nitmz.morphosis.LoginActivity;
import com.nitmz.morphosis.R;


public class TechfestSplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;

    SharedPreferences status;

    TextView mMorphosisTitle;
    TextView mMorphosisSubTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scooby_doo_splash_screen);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            //window.setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        mMorphosisTitle = (TextView) findViewById(R.id.about_title_splash_screen);
        mMorphosisSubTitle = (TextView) findViewById(R.id.about_sub_title_splash_screen);

        Typewriter writer = new Typewriter(this);
        setContentView(writer);

        //Add a character every 150ms
        writer.setCharacterDelay(SPLASH_TIME_OUT);
        writer.animateText("Yello");
        checkLoginStatus();

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLoginStatus();
            }
        }, SPLASH_TIME_OUT); */
    }

    private void checkLoginStatus() {
        status = getSharedPreferences("login_status", Context.MODE_PRIVATE);
        boolean  logIn = status.getBoolean("in", false);
        if (logIn) {
            Intent intent = new Intent(TechfestSplashScreenActivity.this, TechfestHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(TechfestSplashScreenActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra("launch", 2);
            startActivity(intent);
        }
        finish();
    }
}
