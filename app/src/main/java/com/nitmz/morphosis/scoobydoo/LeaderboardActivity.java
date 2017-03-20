package com.nitmz.morphosis.scoobydoo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    ListView mLeaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        mLeaderboard = (ListView) findViewById(R.id.leaderboard_list);

        mDB = FirebaseDatabase.getInstance();
        mUsersRef = mDB.getReference("users");
        mScoreRef = mDB.getReference("score");
        mTopScoreQuery = mScoreRef.orderByValue();

        mTopScoreQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> scores = new ArrayList();
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    String uid = child.getKey();

                    String score = (String) child.getValue();
                    scores.add(score);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        LeaderboardActivity.this,
                        android.R.layout.simple_list_item_1,
                        scores);

                mLeaderboard.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
