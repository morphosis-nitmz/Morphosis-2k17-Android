package com.nitmz.morphosis.scoobydoo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class LeaderboardFragment extends Fragment {

    private FirebaseDatabase mDB;
    private DatabaseReference mUsersRef;
    private DatabaseReference mScoreRef;
    private Query mTopScoreQuery;

    private ArrayList<String> names;
    private ArrayList<String> pURL;
    private ArrayList<String> scores;

    private ValueEventListener listener;

    ListView mLeaderboard;

    public LeaderboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLeaderboard = (ListView) view.findViewById(R.id.leaderboard_list);

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
                        getContext(),
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
