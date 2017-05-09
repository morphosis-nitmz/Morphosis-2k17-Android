package com.nitmz.morphosis.techfest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.nitmz.morphosis.LoginActivity;
import com.nitmz.morphosis.R;
import com.nitmz.morphosis.scoobydoo.ScoobyDooSplashScreenActivity;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class TechfestHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences status;
    FirebaseAuth mAuth;

    private View mFragView;
    private View mHomeView;

    private ViewPager mViewPager;

    CardView mEvents;
    CardView mSchedule;
    CardView mPrizes;
    CardView mWebsite;
    CardView mNews;
    CardView mWinners;
    CardView mAbout;
    CardView mTechnicalSociety;
    CardView mDevelopers;

    Button mScoobyButton;
    Button mStockBridgeButton;
    Button mGetTickets;
    Button mManthanResultsButton;
    Button mCampusAmbassador;
    Button mSponsors;

    private static int currentPage = 0;
    private static int total_pages = 0;

    private static final Integer[] images = {R.drawable.f,R.drawable.sponsor5, R.drawable.a, R.drawable.b, R.drawable.c,
        R.drawable.d, R.drawable.e};
    private ArrayList<Integer> ImagesArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_techfest_home);

        setTitle("Home");

        // Initialize fragment views
        mFragView =findViewById(R.id.frag_view_techfest_home);
        mHomeView =findViewById(R.id.techfest_home_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_techfest);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_techfest);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_techfest);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home_techfest);

        init();




        // CardView listeners

        mEvents = (CardView) findViewById(R.id.card_view_event);
        mEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragments(EventListFragment.class, false);
            }
        });

        mSchedule = (CardView) findViewById(R.id.card_view_schedule);
        mSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragments(ScheduleFragment.class, false);
            }
        });

        mPrizes = (CardView) findViewById(R.id.card_view_prizes);
        mPrizes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragments(PrizesFragment.class, false);
            }
        });

        mWebsite = (CardView) findViewById(R.id.card_view_website);
        mWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TechfestHomeActivity.this, MorphosisWebsiteWebViewActivity.class);
                startActivity(intent);
            }
        });

        mNews = (CardView) findViewById(R.id.card_view_news);
        mNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragments(NewsFragment.class, false);
            }
        });

        mWinners = (CardView) findViewById(R.id.card_view_winners);
        mWinners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragments(WinnersFragment.class, false);
            }
        });

        mAbout = (CardView) findViewById(R.id.card_view_about);
        mAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TechfestHomeActivity.this, AboutMorphosisActivity.class);
                startActivity(intent);
            }
        });

        mTechnicalSociety = (CardView) findViewById(R.id.card_view_technical_society);
        mTechnicalSociety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragments(TechnicalSocietyFragment.class, false);
            }
        });

        mDevelopers = (CardView) findViewById(R.id.card_view_developers);
        mDevelopers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TechfestHomeActivity.this, DevelopersActivity.class);
                startActivity(intent);
            }
        });




        // obg listeners

        mScoobyButton = (Button) findViewById(R.id.obg_scooby_dooby_doo);
        mScoobyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TechfestHomeActivity.this, ScoobyDooSplashScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        mSponsors = (Button) findViewById(R.id.obg_sponsors);
        mSponsors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TechfestHomeActivity.this, SponsorsActivity.class);
                startActivity(intent);

            }
        });

        mStockBridgeButton = (Button) findViewById(R.id.obg_stock_bridge);
        mStockBridgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TechfestHomeActivity.this, ObgWebViewActivity.class);
                intent.putExtra("url", "Stock Bridge");
                startActivity(intent);
            }
        });

        mGetTickets = (Button) findViewById(R.id.obg_get_tickets);
        mGetTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TechfestHomeActivity.this, ObgWebViewActivity.class);
                intent.putExtra("url", "Get Tickets");
                startActivity(intent);
            }
        });

        mCampusAmbassador = (Button) findViewById(R.id.obg_campus_ambassador);
        mCampusAmbassador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TechfestHomeActivity.this, ObgWebViewActivity.class);
                intent.putExtra("url", "Campus Ambassador");
                startActivity(intent);
            }
        });

        mManthanResultsButton = (Button) findViewById(R.id.obg_manthan_results);
        mManthanResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TechfestHomeActivity.this, ObgWebViewActivity.class);
                intent.putExtra("url", "Manthan Answer Keys");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_techfest);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if(getSupportFragmentManager().getBackStackEntryCount()==0)
            {
                if(mHomeView.getVisibility()==View.GONE)
                {

                    mHomeView.setVisibility(View.VISIBLE);
                    mFragView.setVisibility(View.GONE);
                    setTitle("Morphosis");
                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_techfest);
                    navigationView.setCheckedItem(R.id.nav_home_techfest);
                    getSupportFragmentManager().beginTransaction().
                            remove(getSupportFragmentManager().
                                    findFragmentById(R.id.frag_view_techfest_home)).commit();
                    return;
                }
            }
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home_techfest) {
            Intent intent = new Intent(this, TechfestHomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about_morphosis_techfest) {
            Intent intent = new Intent(TechfestHomeActivity.this, AboutMorphosisActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout_techfest) {
            status = getSharedPreferences("login_status", Context.MODE_PRIVATE);
            status.edit().putBoolean("in", false).apply();
            mAuth.signOut();
            Intent intent = new Intent(TechfestHomeActivity.this, LoginActivity.class);
            intent.putExtra("launch", 1);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_share_techfest) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Checkout the official app for Technical Fest of NIT Mizoram:\n https://play.google.com/store/apps/details?id=com.nitmz.morphosis&hl=en");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_techfest);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragments(Class fragmentClass, HashMap<String,String> map) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();

        if(map!=null) {
            Set<Map.Entry<String, String>> set = map.entrySet();
            for(Map.Entry<String, String> data : set) {
                bundle.putString(data.getKey(),data.getValue());
            }
            fragment.setArguments(bundle);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frag_view_techfest_home, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void replaceFragments(Class fragmentClass, Boolean addToBackStack) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            // Check if Fragment Manager contains any existing fragment. If yes, remove it.
            if(!getSupportFragmentManager().beginTransaction().isEmpty()) {
                getSupportFragmentManager().beginTransaction().
                        remove(getSupportFragmentManager().findFragmentById(R.id.frag_view_scooby_home)).
                        commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mHomeView.setVisibility(View.GONE);
        mFragView.setVisibility(View.VISIBLE);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();

        /*
         * If addToBackStack is set to false, then by pressing the back button in a fragment other
         * than Home, will redirect the view back to home view.
         * Else, back button will trace the previously opened fragments.
         */
        if(addToBackStack) {
            fragmentManager.beginTransaction().replace(R.id.frag_view_techfest_home, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            fragmentManager.beginTransaction().replace(R.id.frag_view_techfest_home, fragment)
                    .commit();
        }
    }

    private void init() {
        for (int i = 0; i < images.length; i++) {
            ImagesArray.add(images[i]);
        }

        mViewPager = (ViewPager) findViewById(R.id.home_viewpager);
        mViewPager.setAdapter(new ViewPagerAdapter(TechfestHomeActivity.this, ImagesArray));

        total_pages = images.length;

        PageIndicatorView pageIndicatorView = (PageIndicatorView) findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setViewPager(mViewPager);
        //pageIndicatorView.setSelectedColor(R.color.highlighted);
        //pageIndicatorView.setUnselectedColor(R.color.nothighlighted);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == total_pages) {
                    currentPage = 0;
                }

                if(mHomeView.getVisibility() == View.VISIBLE) {
                    mViewPager.setCurrentItem(currentPage++, true);
                }
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, 6000);


    }

}
