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


    private FirebaseDatabase mDB;
    private DatabaseReference mScoreRef;
    CircleImageView user_image;
    TextView user_score;
    TextView user_name;
    TextView user_rank;
    FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance();
        mScoreRef = mDB.getReference("score/"+mAuth.getCurrentUser().getUid().toString());

        user_image = (CircleImageView)view.findViewById(R.id.user_image_leaderboard_details);
        user_name = (TextView)view.findViewById(R.id.user_name_ip);
        user_score = (TextView)view.findViewById(R.id.user_score_ip);
        user_rank = (TextView)view.findViewById(R.id.user_rank_ip);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String score = dataSnapshot.getValue().toString();
                user_score.setText(score);
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
                .into(user_image);

        user_name.setText(mAuth.getCurrentUser().getDisplayName());








    }



}
