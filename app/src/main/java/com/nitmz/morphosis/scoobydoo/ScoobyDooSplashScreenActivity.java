package com.nitmz.morphosis.scoobydoo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitmz.morphosis.LoginActivity;
import com.nitmz.morphosis.R;

public class ScoobyDooSplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 500;

    FirebaseDatabase mDB;
    DatabaseReference mStatusRef;

    SharedPreferences status;

    TextView mScoobyTitle;
    TextView mScoobySubTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scooby_doo_splash_screen);

        mDB = FirebaseDatabase.getInstance();
        mStatusRef = mDB.getReference("gameStarted");

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            //window.setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        mScoobyTitle = (TextView) findViewById(R.id.scooby_title_splash_screen);
        mScoobySubTitle = (TextView) findViewById(R.id.scooby_sub_title_splash_screen);

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
            checkGameStatus();
        } else {
            Intent intent = new Intent(ScoobyDooSplashScreenActivity.this, LoginActivity.class);
            intent.putExtra("launch", 2);
            startActivity(intent);
            finish();
        }
    }

    private void checkGameStatus() {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String gameStarted = dataSnapshot.getValue().toString();
                if(gameStarted.equals("1")) {
                    Intent intent = new Intent(ScoobyDooSplashScreenActivity.this,ScoobyDooBNavHome.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(ScoobyDooSplashScreenActivity.this, GameStatusActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mStatusRef.addValueEventListener(listener);
    }
}
