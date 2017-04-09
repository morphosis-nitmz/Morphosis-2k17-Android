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

public class TechfestHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences status;
    FirebaseAuth mAuth;

    public View mFragView;
    public View mHomeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_techfest_home);

        // Initialize fragment views
        mFragView =findViewById(R.id.frag_view_home);
        mHomeView =findViewById(R.id.home_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, TechfestHomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_events) {

        } else if (id == R.id.nav_winners) {

        } else if (id == R.id.nav_schedule) {

        } else if (id == R.id.nav_developer) {

        } else if (id == R.id.nav_logout) {
            status = getSharedPreferences("login_status", Context.MODE_PRIVATE);
            status.edit().putBoolean("in", false).apply();
            mAuth.signOut();
            Intent intent = new Intent(TechfestHomeActivity.this, LoginActivity.class);
            intent.putExtra("launch", 1);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey checkout my app at https://play.com");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                        remove(getSupportFragmentManager().findFragmentById(R.id.frag_view_home)).
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
            fragmentManager.beginTransaction().replace(R.id.frag_view_home, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            fragmentManager.beginTransaction().replace(R.id.frag_view_home, fragment)
                    .commit();
        }
    }
}
