package com.nitmz.morphosis.scoobydoo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitmz.morphosis.LoginActivity;
import com.nitmz.morphosis.R;

public class ScoobyDooSplashScreenActivity extends AppCompatActivity {

    FirebaseDatabase mDB;
    DatabaseReference mStatusRef;

    SharedPreferences status;
    ValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scooby_doo_splash_screen);


        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            //window.setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryScooby));

        }


        mDB = FirebaseDatabase.getInstance();
        mStatusRef = mDB.getReference("gameStartedN");

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            //window.setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryScooby));
        }


        checkLoginStatus();

    }

    private void checkLoginStatus() {
        status = getSharedPreferences("login_status", Context.MODE_PRIVATE);
        boolean  logIn = status.getBoolean("in", false);
        if (logIn) {
            checkGameStatus();
        } else {
            Intent intent = new Intent(ScoobyDooSplashScreenActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("launch", 2);
            startActivity(intent);
            finish();
        }
    }

    private void checkGameStatus() {
       listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String gameStarted = dataSnapshot.getValue().toString();
                if(gameStarted.equals("1")) {
                    Intent intent = new Intent(ScoobyDooSplashScreenActivity.this,ScoobyDooBNavHome.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(ScoobyDooSplashScreenActivity.this, GameStatusActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            mStatusRef.removeEventListener(listener);
        } catch (Exception e)
        {

        }



    }
}
