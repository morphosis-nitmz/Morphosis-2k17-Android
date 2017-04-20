package com.nitmz.morphosis.techfest;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nitmz.morphosis.R;


public class NewsDetailsFragment extends Fragment {


    TextView title;
    TextView body;
    public NewsDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("News");

        title = (TextView) view.findViewById(R.id.news_title_details);
        body = (TextView) view.findViewById(R.id.news_body_details);

        title.setText(getArguments().getString("title"));
        body.setText(getArguments().getString("body"));
    }
}
