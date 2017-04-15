package com.nitmz.morphosis.techfest;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nitmz.morphosis.R;

public class TechnicalSocietyFragment extends Fragment {

    SwipeRefreshLayout mTechnicalSocietySwipeRefresh;
    WebView mTechnicalSocietyWebView;
    ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_technical_society, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTechnicalSocietySwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.technical_society_swipe_refresh);
        swipeRefresh();
        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");

        //sr=(SwipeRefreshLayout)view.findViewById(R.id.srOngoingMatchList);
        mTechnicalSocietyWebView = (WebView) view.findViewById(R.id.technical_society_web_view);
        mTechnicalSocietyWebView.loadUrl("http://morphosis2k17.com/people.php");
        mTechnicalSocietyWebView.getSettings().setUseWideViewPort(true);
        mTechnicalSocietyWebView.getSettings().setLoadWithOverviewMode(true);
        mTechnicalSocietyWebView.getSettings().setJavaScriptEnabled(true);
        mTechnicalSocietyWebView.getSettings().setSupportZoom(true);
        mTechnicalSocietyWebView.getSettings().setBuiltInZoomControls(false);
        mTechnicalSocietyWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mTechnicalSocietyWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mTechnicalSocietyWebView.getSettings().setDomStorageEnabled(true);
        mTechnicalSocietyWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mTechnicalSocietyWebView.setScrollbarFadingEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTechnicalSocietyWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            mTechnicalSocietyWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        mTechnicalSocietyWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
                mTechnicalSocietySwipeRefresh.setRefreshing(false);
            }
        });
    }

    void swipeRefresh() {

        mTechnicalSocietySwipeRefresh.setColorSchemeResources(R.color.swipe_refresh_green,
                R.color.swipe_refresh_blue, R.color.swipe_refresh_orange, R.color.swipe_refresh_red);
        mTechnicalSocietySwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTechnicalSocietyWebView.reload();
            }
        });

    }
}
