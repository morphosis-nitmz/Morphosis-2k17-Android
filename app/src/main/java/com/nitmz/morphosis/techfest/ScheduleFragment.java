package com.nitmz.morphosis.techfest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.nitmz.morphosis.R;

public class ScheduleFragment extends Fragment {

    WebView mSchedule;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mSchedule = (WebView) view.findViewById(R.id.schedule_morphosis);
        mSchedule.loadDataWithBaseURL(null, "<html><head><style>img " +
                "{margin-top:auto;margin-bottom:auto}</style></head><body><img src=\"" +
                "file:///android_asset/schedule.png" + "\"></body></html>", "html/css", "utf-8", null);
        mSchedule.getSettings().setUseWideViewPort(true);
        mSchedule.getSettings().setLoadWithOverviewMode(true);
    }
}
