package com.nitmz.morphosis.techfest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.nitmz.morphosis.LoginActivity;
import com.nitmz.morphosis.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TechfestHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences status;
    FirebaseAuth mAuth;

    private View mFragView;
    private View mHomeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_techfest_home);

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
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_techfest);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.techfest_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home_techfest) {
            Intent intent = new Intent(this, TechfestHomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_events_techfest) {
            replaceFragments(EventListFragment.class, false);
        } else if (id == R.id.nav_winners_techfest) {

        } else if (id == R.id.nav_schedule_techfest) {

        } else if (id == R.id.nav_technical_society_techfest) {

        } else if (id == R.id.nav_developer_techfest) {

        } else if (id == R.id.nav_morphosis_website_techfest) {

        } else if (id == R.id.nav_about_morphosis_techfest) {

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
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey checkout my app at https://play.com");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_techfest);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
}
