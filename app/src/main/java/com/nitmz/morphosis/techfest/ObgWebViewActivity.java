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

public class ObgWebViewActivity extends AppCompatActivity {

    String[] URLs = {
            "Stock Bridge",
            "Get Tickets",
            "Campus Ambassador",
            "Manthan Answer Keys"
    };

    SwipeRefreshLayout mObgWebViewSwipeRefresh;
    WebView mObgWebView;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obg_web_view);

        String url = "http://www.morphosis2k17.com/";
        String destination = getIntent().getExtras().getString("url");

        setTitle(destination);

        if (destination.equals("Stock Bridge")) {
            url += "events/stockbridge";
        } else if (destination.equals("Get Tickets")) {
            url = "https://www.thecollegefever.com/events/morphosis17";
        } else if (destination.equals("Campus Ambassador")) {
            url += "ca/register.php";
        } else if (destination.equals("Manthan Answer Keys")) {
            url += "events/manthan/answerkey.php";
        }

        mObgWebViewSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.obg_web_view_swipe_refresh);
        swipeRefresh();

        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");

        //sr=(SwipeRefreshLayout)view.findViewById(R.id.srOngoingMatchList);
        mObgWebView = (WebView) findViewById(R.id.obg_web_view);
        mObgWebView.loadUrl(url);
        mObgWebView.getSettings().setUseWideViewPort(true);
        mObgWebView.getSettings().setLoadWithOverviewMode(true);
        mObgWebView.getSettings().setJavaScriptEnabled(true);
        mObgWebView.getSettings().setSupportZoom(true);
        mObgWebView.getSettings().setBuiltInZoomControls(false);
        mObgWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mObgWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mObgWebView.getSettings().setDomStorageEnabled(true);
        mObgWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mObgWebView.setScrollbarFadingEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mObgWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            mObgWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        mObgWebView.setWebViewClient(new WebViewClient() {
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
                mObgWebViewSwipeRefresh.setRefreshing(false);
            }
        });
    }

    void swipeRefresh() {
        mObgWebViewSwipeRefresh.setColorSchemeResources(R.color.swipe_refresh_green,
                R.color.swipe_refresh_blue, R.color.swipe_refresh_orange, R.color.swipe_refresh_red);
        mObgWebViewSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mObgWebView.reload();
            }
        });
    }
}
