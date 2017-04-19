package com.nitmz.morphosis.scoobydoo;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitmz.morphosis.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class LeaderboardFragment extends Fragment {

    private FirebaseDatabase mDB;
    private DatabaseReference mUsersRef;
    private DatabaseReference mScoreRef;


    private ArrayList<LeaderBoardUserObject> mUserObjects;

    private ValueEventListener mListener;
    private ValueEventListener  mMainListener;

    ListView mLeaderboard;
    LeaderBoardListViewAdapter mAdapter;

    ProgressDialog pd;

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

        mUserObjects = new ArrayList<>();
        pd = new ProgressDialog(getContext());
        pd.setMessage("Waiting for the Top Players ....");
        pd.show();
        //pd.setCancelable(false);
        //pd.setCanceledOnTouchOutside(false);

        mLeaderboard = (ListView) view.findViewById(R.id.leaderboard_list);

        mAdapter = new LeaderBoardListViewAdapter(mUserObjects, getContext());
        mLeaderboard.setAdapter(mAdapter);

        mDB = FirebaseDatabase.getInstance();
        mScoreRef = mDB.getReference("score");
        //mTopScoreQuery = mScoreRef.orderByValue();

        mMainListener =new ValueEventListener() {
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
        };

        mScoreRef.addListenerForSingleValueEvent(mMainListener);
        //mTopScoreQuery.addListenerForSingleValueEvent(mMainListener);
    }

    private void userDetails() {
        mListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userName = dataSnapshot.child("name").getValue(String.class);
                userName = toTitleCase(userName.toLowerCase().trim());
                String photoURL = dataSnapshot.child("pURL").getValue(String.class);
                String userScore = dataSnapshot.child("score").getValue(String.class);
                String crank = dataSnapshot.child("crank").getValue(String.class);

                LeaderBoardUserObject user = new LeaderBoardUserObject();
                user.setUsername(userName);
                user.setScore(Integer.parseInt(userScore));
                user.setPurl(photoURL);
                user.setCrank(Integer.parseInt(crank));

                mUserObjects.add(user);


                Collections.sort(mUserObjects,new LeaderBoardUserObject());




                /*ArrayList<LeaderBoardUserObject> userObjects_temp = new ArrayList<>();
                userObjects_temp.add(user);
                userObjects_temp.addAll(mUserObjects);
                mUserObjects = userObjects_temp;*/

                if (isAdded()) {


                    mAdapter = new LeaderBoardListViewAdapter(mUserObjects, getContext());
                    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            LeaderBoardUserObject userObject = mUserObjects.get(position);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("name", userObject.getUsername());
                            map.put("score", Integer.toString(userObject.getScore()));
                            map.put("purl", userObject.getPurl());
                            map.put("rank",Integer.toString(position+1));
                            ((ScoobyDooBNavHome) getActivity()).
                                    replaceFragments(LeaderBoardUserDetailsFragment.class, map);
                        }
                    };
                    mLeaderboard.setOnItemClickListener(listener);
                    mLeaderboard.setAdapter(mAdapter);
                    pd.cancel();

                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        if(mListener != null) {
            //mUserObjects.clear();
            mUsersRef.addListenerForSingleValueEvent(mListener);
        }
    }

    // Converts a string into Title Case
    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }
            titleCase.append(c);
        }

        return titleCase.toString();
    }


    @Override
    public void onPause() {
        super.onPause();

        if(mListener!=null)
        {
            mUsersRef.removeEventListener(mListener);
            mScoreRef.removeEventListener(mMainListener);

        }
    }

}
