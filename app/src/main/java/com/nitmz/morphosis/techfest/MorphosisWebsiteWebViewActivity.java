package com.nitmz.morphosis.techfest;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nitmz.morphosis.R;

public class MorphosisWebsiteWebViewActivity extends AppCompatActivity {

    SwipeRefreshLayout mMorphosisWebsiteSwipeRefresh;
    WebView mMorphosisWebsiteWebView;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morphosis_website_web_view);

        mMorphosisWebsiteSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.morphosis_website_swipe_refresh);
        swipeRefresh();

        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");

        //sr=(SwipeRefreshLayout)view.findViewById(R.id.srOngoingMatchList);
        mMorphosisWebsiteWebView = (WebView) findViewById(R.id.morphosis_website_web_view);
        mMorphosisWebsiteWebView.loadUrl("http://www.morphosis2k17.com");
        mMorphosisWebsiteWebView.getSettings().setUseWideViewPort(true);
        mMorphosisWebsiteWebView.getSettings().setLoadWithOverviewMode(true);
        mMorphosisWebsiteWebView.getSettings().setJavaScriptEnabled(true);
        mMorphosisWebsiteWebView.getSettings().setSupportZoom(true);
        mMorphosisWebsiteWebView.getSettings().setBuiltInZoomControls(false);
        mMorphosisWebsiteWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mMorphosisWebsiteWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mMorphosisWebsiteWebView.getSettings().setDomStorageEnabled(true);
        mMorphosisWebsiteWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mMorphosisWebsiteWebView.setScrollbarFadingEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mMorphosisWebsiteWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            mMorphosisWebsiteWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        mMorphosisWebsiteWebView.setWebViewClient(new WebViewClient() {
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
                mMorphosisWebsiteSwipeRefresh.setRefreshing(false);
            }
        });
    }

    void swipeRefresh() {
        mMorphosisWebsiteSwipeRefresh.setColorSchemeResources(R.color.swipe_refresh_green,
                R.color.swipe_refresh_blue, R.color.swipe_refresh_orange, R.color.swipe_refresh_red);
        mMorphosisWebsiteSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMorphosisWebsiteWebView.reload();
            }
        });

    }
}
