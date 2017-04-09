package com.nitmz.morphosis.techfest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nitmz.morphosis.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EventListFragment extends Fragment {

    List<String> mEvents;

    ListView mListView;

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

        mEvents = new ArrayList<>(Arrays.asList(
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
                "S-Marketing"));

        mListView = (ListView) view.findViewById(R.id.events_list);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                mEvents
        );

        mListView.setAdapter(arrayAdapter);
    }
}
