package com.nitmz.morphosis.techfest;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.nitmz.morphosis.R;

public class EventDetailAboutFragment extends Fragment {

    int mEventPosition;

    TechfestData techfestData;

    WebView[] mEventAboutWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_detail_about, container, false);

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        mEventPosition = bundle.getInt("position");

        techfestData = new TechfestData();
        mEventAboutWebView = new WebView[techfestData.getAboutEvent(mEventPosition) + 1];

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        for (int i = 0; i <= techfestData.getAboutEvent(mEventPosition); i++) {
            String path = "file:///android_asset/events_about/" + mEventPosition + "/" + i + ".png";

            if (i == 0) {
                mEventAboutWebView[i] = (WebView) view.findViewById(R.id.event_detail_about_web_view_0);
                mEventAboutWebView[i].setVisibility(View.VISIBLE);
                mEventAboutWebView[i].loadDataWithBaseURL(null, "<html><head><style>img " +
                        "{margin-top:auto;margin-bottom:auto}</style></head><body><img src=\"" +
                        path + "\"></body></html>", "html/css", "utf-8", null);
                mEventAboutWebView[i].getSettings().setUseWideViewPort(true);
                mEventAboutWebView[i].getSettings().setLoadWithOverviewMode(true);
            } else if (i == 1) {
                mEventAboutWebView[i] = (WebView) view.findViewById(R.id.event_detail_about_web_view_1);
                mEventAboutWebView[i].setVisibility(View.VISIBLE);
                mEventAboutWebView[i].loadDataWithBaseURL(null, "<html><head><style>img " +
                        "{margin-top:auto;margin-bottom:auto}</style></head><body><img src=\"" +
                        path + "\"></body></html>", "html/css", "utf-8", null);
                mEventAboutWebView[i].getSettings().setUseWideViewPort(true);
                mEventAboutWebView[i].getSettings().setLoadWithOverviewMode(true);
            } else if (i == 2) {
                mEventAboutWebView[i] = (WebView) view.findViewById(R.id.event_detail_about_web_view_2);
                mEventAboutWebView[i].setVisibility(View.VISIBLE);
                mEventAboutWebView[i].loadDataWithBaseURL(null, "<html><head><style>img " +
                        "{margin-top:auto;margin-bottom:auto}</style></head><body><img src=\"" +
                        path + "\"></body></html>", "html/css", "utf-8", null);
                mEventAboutWebView[i].getSettings().setUseWideViewPort(true);
                mEventAboutWebView[i].getSettings().setLoadWithOverviewMode(true);
            } else if (i == 3) {
                mEventAboutWebView[i] = (WebView) view.findViewById(R.id.event_detail_about_web_view_3);
                mEventAboutWebView[i].setVisibility(View.VISIBLE);
                mEventAboutWebView[i].loadDataWithBaseURL(null, "<html><head><style>img " +
                        "{margin-top:auto;margin-bottom:auto}</style></head><body><img src=\"" +
                        path + "\"></body></html>", "html/css", "utf-8", null);
                mEventAboutWebView[i].getSettings().setUseWideViewPort(true);
                mEventAboutWebView[i].getSettings().setLoadWithOverviewMode(true);
            } else if (i == 4) {
                mEventAboutWebView[i] = (WebView) view.findViewById(R.id.event_detail_about_web_view_4);
                mEventAboutWebView[i].setVisibility(View.VISIBLE);
                mEventAboutWebView[i].loadDataWithBaseURL(null, "<html><head><style>img " +
                        "{margin-top:auto;margin-bottom:auto}</style></head><body><img src=\"" +
                        path + "\"></body></html>", "html/css", "utf-8", null);
                mEventAboutWebView[i].getSettings().setUseWideViewPort(true);
                mEventAboutWebView[i].getSettings().setLoadWithOverviewMode(true);
            } else if (i == 5) {
                mEventAboutWebView[i] = (WebView) view.findViewById(R.id.event_detail_about_web_view_5);
                mEventAboutWebView[i].setVisibility(View.VISIBLE);
                mEventAboutWebView[i].loadDataWithBaseURL(null, "<html><head><style>img " +
                        "{margin-top:auto;margin-bottom:auto}</style></head><body><img src=\"" +
                        path + "\"></body></html>", "html/css", "utf-8", null);
                mEventAboutWebView[i].getSettings().setUseWideViewPort(true);
                mEventAboutWebView[i].getSettings().setLoadWithOverviewMode(true);
            }
        }
    }
}