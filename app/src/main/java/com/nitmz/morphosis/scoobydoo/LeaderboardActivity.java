package com.nitmz.morphosis.scoobydoo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    private FirebaseDatabase mDB;
    private DatabaseReference mUsersRef;
    private DatabaseReference mScoreRef;
    private Query mTopScoreQuery;

    private ArrayList<String> names;
    private ArrayList<String> pURL;
    private ArrayList<String> scores;

    private ValueEventListener listener;

    ListView mLeaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        mLeaderboard = (ListView) findViewById(R.id.leaderboard_list);

        names = new ArrayList<>();
        pURL = new ArrayList<>();
        scores = new ArrayList<>();

        mDB = FirebaseDatabase.getInstance();
        mScoreRef = mDB.getReference("score");
        mTopScoreQuery = mScoreRef.orderByValue();

        mTopScoreQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    String uid = child.getKey();
                    mUsersRef = mDB.getReference("users/" + uid);
                    userDetails();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void userDetails() {
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userName = dataSnapshot.child("name").getValue(String.class);

                ArrayList<String> temp = new ArrayList<>();
                temp.add(userName);
                temp.addAll(names);
                names = temp;

                //String photoURL = dataSnapshot.child("pURL").getValue(String.class);
                //pURL.add(photoURL);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        LeaderboardActivity.this,
                        android.R.layout.simple_list_item_1,
                        names);
                mLeaderboard.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        if(listener != null) {
            mUsersRef.addValueEventListener(listener);
        }
    }
}
