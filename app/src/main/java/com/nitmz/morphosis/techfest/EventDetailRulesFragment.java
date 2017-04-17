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

public class EventDetailRulesFragment extends Fragment {

    int mEventPosition;

    TechfestData techfestData;

    WebView[] mEventRulesWebView;
    RelativeLayout mRelativeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_detail_rules, container, false);

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        mEventPosition = bundle.getInt("position");

        techfestData = new TechfestData();
        mEventRulesWebView = new WebView[techfestData.getRulesPage(mEventPosition) + 1];

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        if (mEventPosition == 7 || mEventPosition == 14) {
            mRelativeLayout = (RelativeLayout) view.findViewById(R.id.no_rules_event_text);
            mRelativeLayout.setVisibility(View.VISIBLE);
        } else {
            for (int i = 0; i < techfestData.getRulesPage(mEventPosition); i++) {
                String path = "file:///android_asset/events_rules/" + mEventPosition + "/" + i + ".png";

                if (i == 0) {
                    mEventRulesWebView[i] = (WebView) view.findViewById(R.id.event_detail_rules_web_view_0);
                    mEventRulesWebView[i].setVisibility(View.VISIBLE);
                    mEventRulesWebView[i].loadDataWithBaseURL(null, "<html><head><style>img " +
                            "{margin-top:auto;margin-bottom:auto}</style></head><body><img src=\"" +
                            path + "\"></body></html>", "html/css", "utf-8", null);
                    mEventRulesWebView[i].getSettings().setUseWideViewPort(true);
                    mEventRulesWebView[i].getSettings().setLoadWithOverviewMode(true);
                } else if (i == 1) {
                    mEventRulesWebView[i] = (WebView) view.findViewById(R.id.event_detail_rules_web_view_1);
                    mEventRulesWebView[i].setVisibility(View.VISIBLE);
                    mEventRulesWebView[i].loadDataWithBaseURL(null, "<html><head><style>img " +
                            "{margin-top:auto;margin-bottom:auto}</style></head><body><img src=\"" +
                            path + "\"></body></html>", "html/css", "utf-8", null);
                    mEventRulesWebView[i].getSettings().setUseWideViewPort(true);
                    mEventRulesWebView[i].getSettings().setLoadWithOverviewMode(true);
                } else if (i == 2) {
                    mEventRulesWebView[i] = (WebView) view.findViewById(R.id.event_detail_rules_web_view_2);
                    mEventRulesWebView[i].setVisibility(View.VISIBLE);
                    mEventRulesWebView[i].loadDataWithBaseURL(null, "<html><head><style>img " +
                            "{margin-top:auto;margin-bottom:auto}</style></head><body><img src=\"" +
                            path + "\"></body></html>", "html/css", "utf-8", null);
                    mEventRulesWebView[i].getSettings().setUseWideViewPort(true);
                    mEventRulesWebView[i].getSettings().setLoadWithOverviewMode(true);
                } else if (i == 3) {
                    mEventRulesWebView[i] = (WebView) view.findViewById(R.id.event_detail_rules_web_view_3);
                    mEventRulesWebView[i].setVisibility(View.VISIBLE);
                    mEventRulesWebView[i].loadDataWithBaseURL(null, "<html><head><style>img " +
                            "{margin-top:auto;margin-bottom:auto}</style></head><body><img src=\"" +
                            path + "\"></body></html>", "html/css", "utf-8", null);
                    mEventRulesWebView[i].getSettings().setUseWideViewPort(true);
                    mEventRulesWebView[i].getSettings().setLoadWithOverviewMode(true);
                } else if (i == 4) {
                    mEventRulesWebView[i] = (WebView) view.findViewById(R.id.event_detail_rules_web_view_4);
                    mEventRulesWebView[i].setVisibility(View.VISIBLE);
                    mEventRulesWebView[i].loadDataWithBaseURL(null, "<html><head><style>img " +
                            "{margin-top:auto;margin-bottom:auto}</style></head><body><img src=\"" +
                            path + "\"></body></html>", "html/css", "utf-8", null);
                    mEventRulesWebView[i].getSettings().setUseWideViewPort(true);
                    mEventRulesWebView[i].getSettings().setLoadWithOverviewMode(true);
                }
            }
        }
    }
}