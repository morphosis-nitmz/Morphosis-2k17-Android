package com.nitmz.morphosis.techfest;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.nitmz.morphosis.R;

public class EventDetailAboutFragment extends Fragment {

    WebView mEventAboutWebView;

    private TechfestData techfestData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_detail_about, container, false);
        mEventAboutWebView = (WebView) rootView.findViewById(R.id.event_detail_about_web_view);

        techfestData = new TechfestData();

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        int position = bundle.getInt("position");

        return rootView;
    }
}