package com.nitmz.morphosis.techfest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.nitmz.morphosis.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EventListFragment extends Fragment {

    List<String> mEvents;

    Button mMazeSolver;
    Button mRoboRace;
    Button mRoboWar;
    ImageView mStockBridge;
    ImageView mQuiz;
    ImageView mArcadia;
    ImageView mCubeFever;
    ImageView mScoobyDoobyDoo;
    ImageView mPowerOfSpeech;
    ImageView mCodeWarrior;
    ImageView mCypher;
    ImageView mTechTalk;
    ImageView mScienceExpo;
    ImageView mAbhyudaya;
    ImageView mManthan;

    public EventListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Events");

        mEvents = new ArrayList<>(Arrays.asList(
                "Maze Solver", // 0
                "Robo Race", // 1
                "Robo War", // 2
                "Stock Bridge", // 3
                "Quiz", // 4
                "Arcadia", // 5
                "Cube Fever", // 6
                "Scooby Dooby Doo", // 7
                "Power of Speech", // 8
                "Code Warrior", // 9
                "Cypher", // 10
                "Tech Talk", // 11
                "Science Expo", // 12
                "Abhyudaya", // 13
                "Manthan")); // 14

        mMazeSolver = (Button) view.findViewById(R.id.button_maze_solver);
        mMazeSolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                intent.putExtra("position", 0);
                getActivity().startActivity(intent);
            }
        });

        mRoboRace = (Button) view.findViewById(R.id.button_robo_race);
        mRoboRace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                intent.putExtra("position", 1);
                startActivity(intent);
            }
        });

        mRoboWar = (Button) view.findViewById(R.id.button_robo_war);
        mRoboWar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                intent.putExtra("position", 2);
                startActivity(intent);
            }
        });

        mStockBridge = (ImageView) view.findViewById(R.id.banner_stock_bridge);
        mStockBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                intent.putExtra("position", 3);
                startActivity(intent);
            }
        });

        mQuiz = (ImageView) view.findViewById(R.id.banner_quiz);
        mQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                intent.putExtra("position", 4);
                startActivity(intent);
            }
        });

        mArcadia = (ImageView) view.findViewById(R.id.banner_arcadia);
        mArcadia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                intent.putExtra("position", 5);
                startActivity(intent);
            }
        });

        mCubeFever = (ImageView) view.findViewById(R.id.banner_cube_fever);
        mCubeFever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                intent.putExtra("position", 6);
                startActivity(intent);
            }
        });

        mScoobyDoobyDoo = (ImageView) view.findViewById(R.id.banner_scooby_dooby_doo);
        mScoobyDoobyDoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                intent.putExtra("position", 7);
                startActivity(intent);
            }
        });

        mPowerOfSpeech = (ImageView) view.findViewById(R.id.banner_power_of_speech);
        mPowerOfSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                intent.putExtra("position", 8);
                startActivity(intent);
            }
        });

        mCodeWarrior = (ImageView) view.findViewById(R.id.banner_code_warrior);
        mCodeWarrior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                intent.putExtra("position", 9);
                startActivity(intent);
            }
        });

        mCypher = (ImageView) view.findViewById(R.id.banner_cypher);
        mCypher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                intent.putExtra("position", 10);
                startActivity(intent);
            }
        });

        mTechTalk = (ImageView) view.findViewById(R.id.banner_tech_talk);
        mTechTalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                intent.putExtra("position", 11);
                startActivity(intent);
            }
        });

        mScienceExpo = (ImageView) view.findViewById(R.id.banner_science_expo);
        mScienceExpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                intent.putExtra("position", 12);
                startActivity(intent);
            }
        });

        mAbhyudaya = (ImageView) view.findViewById(R.id.banner_abhyudaya);
        mAbhyudaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                intent.putExtra("position", 13);
                startActivity(intent);
            }
        });

        mManthan = (ImageView) view.findViewById(R.id.banner_manthan);
        mManthan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                intent.putExtra("position", 14);
                startActivity(intent);
            }
        });
    }
}