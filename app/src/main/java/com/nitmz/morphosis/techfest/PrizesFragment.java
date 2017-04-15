package com.nitmz.morphosis.techfest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.nitmz.morphosis.R;

public class PrizesFragment extends Fragment {

    WebView mAboutMorphosis;

    public PrizesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prizes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mAboutMorphosis = (WebView) view.findViewById(R.id.about_morphosis);
        mAboutMorphosis.loadDataWithBaseURL(null, "<html><head><style>img " +
                "{margin-top:auto;margin-bottom:auto}</style></head><body><img src=\"" +
                "file:///android_asset/prizes.png" + "\"></body></html>", "html/css", "utf-8", null);
        mAboutMorphosis.getSettings().setUseWideViewPort(true);
        mAboutMorphosis.getSettings().setLoadWithOverviewMode(true);
    }
}
