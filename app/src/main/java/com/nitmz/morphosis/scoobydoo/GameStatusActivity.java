package com.nitmz.morphosis.scoobydoo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitmz.morphosis.R;

public class GameStatusActivity extends AppCompatActivity {

    TextView mTopText;
    TextView mCenterText;
    TextView mBottomText;

    boolean isAlive;
    //public static Activity gsa;

    FirebaseDatabase mDB;
    DatabaseReference mStatusTextRef;
    DatabaseReference mStatusRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_status);

        mDB = FirebaseDatabase.getInstance();
        mStatusTextRef = mDB.getReference("gameStatusText");
        mStatusRef = mDB.getReference("gameStartedN");

        // trick for finishing one activity from another activity
        //  make a static ref of Activity class and assign context to it
        // finish it when  necessary

        //gsa = this;

        // use this in the second activity to kill first one
        /*int gsaAlive = getIntent().getIntExtra("string",0);
        if((gsaAlive==1))
            GameStatusActivity.gsa.finish();*/


        checkGameStatus();

        mTopText = (TextView) findViewById(R.id.game_status_top_text);
        mCenterText = (TextView) findViewById(R.id.game_status_center_text);
        mBottomText = (TextView) findViewById(R.id.game_status_bottom_text);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Top = dataSnapshot.child("Top").getValue().toString();
                String Center = dataSnapshot.child("Center").getValue().toString();
                String Bottom = dataSnapshot.child("Bottom").getValue().toString();

                if (!Top.equals("0")) {
                    mTopText.setVisibility(View.VISIBLE);
                    mTopText.setText(Top);
                } else {
                    mTopText.setVisibility(View.GONE);
                }
                if (!Center.equals("0")) {
                    mCenterText.setVisibility(View.VISIBLE);
                    mCenterText.setText(Center);
                } else {
                    mCenterText.setVisibility(View.GONE);
                }
                if (!Bottom.equals("0")) {
                    mBottomText.setVisibility(View.VISIBLE);
                    mBottomText.setText(Bottom);
                } else {
                    mBottomText.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mStatusTextRef.addListenerForSingleValueEvent(listener);
    }

    private void checkGameStatus(){

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String gameStarted = dataSnapshot.getValue().toString();
                if(!gameStarted.equals("0"))
                {
                    if(isAlive) {
                        Intent intent = new Intent(GameStatusActivity.this, ScoobyDooBNavHome.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mStatusRef.addValueEventListener(listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isAlive = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isAlive = true;

    }
}
