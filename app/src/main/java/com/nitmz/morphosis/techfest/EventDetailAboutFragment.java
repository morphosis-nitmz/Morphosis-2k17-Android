package com.nitmz.morphosis.techfest;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nitmz.morphosis.R;

public class EventDetailAboutFragment extends Fragment {

    TextView mAboutText;

    private TechfestData techfestData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_detail_about, container, false);
        mAboutText = (TextView) rootView.findViewById(R.id.event_detail_about_text);

        techfestData = new TechfestData();

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        int position = bundle.getInt("position");
        mAboutText.setText(techfestData.getAboutEvent(position));

        return rootView;
    }
}