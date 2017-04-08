package com.nitmz.morphosis.scoobydoo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.nitmz.morphosis.CircleImageView;
import com.nitmz.morphosis.R;


public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;

    private CircleImageView mProfileImage;
    private TextView mUserName;
    private TextView mRank;
    private TextView mScore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        mProfileImage = (CircleImageView) view.findViewById(R.id.profile_image_view);
        mUserName = (TextView) view.findViewById(R.id.name_text_view);
        mRank = (TextView) view.findViewById(R.id.rank_text_view);
        mScore = (TextView) view.findViewById(R.id.score_text_view);

        Glide.with(this)
                .load(mAuth.getCurrentUser().getPhotoUrl().toString())
                .fitCenter()
                .dontAnimate()
                .into(mProfileImage);

        mUserName.setText(mAuth.getCurrentUser().getDisplayName());
        mUserName.setTextSize(45);
        mUserName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    }
}
