package com.nitmz.morphosis.scoobydoo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitmz.morphosis.R;


public class UserProfileFragment extends Fragment {

    FirebaseAuth mAuth;
    private FirebaseDatabase mDB;
    private DatabaseReference mScoreRef;

    CircleImageView mUserImage;
    TextView mUserScore;
    TextView mUserName;
    TextView mUserRank;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_leader_board_user_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO: App crashes when profile is opened after opening my profile from leaderboard list.

        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance();
        mScoreRef = mDB.getReference("score/" + mAuth.getCurrentUser().getUid());

        mUserImage = (CircleImageView)view.findViewById(R.id.user_image_leaderboard_details);
        mUserName = (TextView)view.findViewById(R.id.user_name_ip);
        mUserScore = (TextView)view.findViewById(R.id.user_score_ip);
        mUserRank = (TextView)view.findViewById(R.id.user_rank_ip);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String score = dataSnapshot.getValue().toString();
                mUserScore.setText(score);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mScoreRef.addValueEventListener(listener);

        Glide.with(getContext())
                .load(mAuth.getCurrentUser().getPhotoUrl())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .fitCenter()
                .error(R.drawable.ic_account_circle_white_48dp)
                .placeholder(R.drawable.ic_account_circle_white_48dp)
                .dontAnimate()
                .into(mUserImage);

        mUserName.setText(toTitleCase(mAuth.getCurrentUser().getDisplayName().toLowerCase().trim()));
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

}
