package com.nitmz.morphosis.techfest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nitmz.morphosis.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WinnersFragment extends Fragment {

    List<String> mEvents;

    ListView mListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_winners, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mEvents = new ArrayList<>(Arrays.asList(
                "Maze Solver",
                "Robo Race",
                "Robo War",
                "Stock Bridge",
                "Quiz",
                "Arcadia",
                "Cube Fever",
                "Scooby Dooby Doo",
                "Virtual Placement Drive",
                "Power of Speech",
                "Code Warrior",
                "Cypher",
                "Tech Talk",
                "Science Expo",
                "S-Marketing",
                "Abhyudaya",
                "Manthan"));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                mEvents
        );

        mListView = (ListView) view.findViewById(R.id.win_list);
        mListView.setAdapter(arrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle elementPosition = new Bundle();
                HashMap<String,String> map = new HashMap<String, String>();
                map.put("event", mEvents.get(position).toLowerCase().trim());
                ((TechfestHomeActivity)getActivity()).replaceFragments(WinnerDetailsFragment.class,map);
            }
        });

    }




}
