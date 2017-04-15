package com.nitmz.morphosis.scoobydoo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nitmz.morphosis.LoginActivity;
import com.nitmz.morphosis.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ScoobyDooHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = ScoobyDooHomeActivity.class.getSimpleName();

    SharedPreferences status;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private StorageReference mStorageReference;
    private FirebaseDatabase mDB;
    private DatabaseReference mUsersRef;
    private DatabaseReference mScoreRef;

    DatabaseReference mStatusRef;

    private ValueEventListener mListener;

    private EditText mAnswerField;
    NavigationView mNavigationView;
    TextView mTitle;
    ProgressDialog mDialog;
    private View mFragView;
    private View mHomeView;

    Data mData;

    String mQuestionTitle;
    int mQuestionNumber;
    Button mAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scooby_doo_home);

        status = getSharedPreferences("login_status", Context.MODE_PRIVATE);
        mDB = FirebaseDatabase.getInstance();
        mStatusRef = mDB.getReference("gameStarted");

        checkGameStatus();

        if(!status.getBoolean("first",false))
        {
            introDisplay();
            status.edit().putBoolean("first",true).apply();
        }

        // Initialize fragment views
        mFragView =findViewById(R.id.frag_view_scooby_home);
        mHomeView =findViewById(R.id.scooby_home_view);

        mDialog =new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(mAuth.getCurrentUser() == null){
                    Intent intent = new Intent(ScoobyDooHomeActivity.this,LoginActivity.class);
                    intent.putExtra("launch",2);
                    intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };

        mAuth.addAuthStateListener(mAuthListener);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_scooby);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_scooby);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view_scooby);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setCheckedItem(R.id.nav_home_scooby);
        mAnswerField = (EditText) findViewById(R.id.answer_text);
        mTitle = (TextView) findViewById(R.id.title_view);
        mAnswerButton = (Button) findViewById(R.id.answer_button);
        mAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        // Initialize data for question loading
        mDB = FirebaseDatabase.getInstance();
        mUsersRef = mDB.getReference("users");
        mScoreRef = mDB.getReference("score");
        mData = new Data();
        mQuestionNumber = 1;
        loadQuestion();
    }

    private void loadQuestion() {
        mQuestionTitle = "Question" + mQuestionNumber;
        mTitle.setText(mQuestionTitle);

        ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
        ImageView imageView3 = (ImageView) findViewById(R.id.imageView3);
        ImageView imageView4 = (ImageView) findViewById(R.id.imageView4);

        mStorageReference = FirebaseStorage.getInstance().getReference();
        StorageReference pathReference1 = mStorageReference.child("Question" + mQuestionNumber + "/1.jpg");
        StorageReference pathReference2 = mStorageReference.child("Question" + mQuestionNumber + "/2.jpg");
        StorageReference pathReference3 = mStorageReference.child("Question" + mQuestionNumber + "/3.jpg");
        StorageReference pathReference4 = mStorageReference.child("Question" + mQuestionNumber + "/5.jpg");

        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(pathReference1)
                .into(imageView1);
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(pathReference2)
                .into(imageView2);
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(pathReference3)
                .into(imageView3);
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(pathReference4)
                .into(imageView4);
    }

    private void checkAnswer() {
        String answer = mAnswerField.getText().toString().toLowerCase();
        if(mData.checkAnswer(answer, mQuestionNumber)) {
            if (mQuestionNumber < 10) {
                mUsersRef.child(mAuth.getCurrentUser().getUid()).child("score").
                        setValue("0" + mQuestionNumber);
                mScoreRef.child(mAuth.getCurrentUser().getUid()).setValue("0" + mQuestionNumber);
            } else {
                mUsersRef.child(mAuth.getCurrentUser().getUid()).child("score").
                        setValue(mQuestionNumber);
                mScoreRef.child(mAuth.getCurrentUser().getUid()).setValue(mQuestionNumber);
            }
            mQuestionNumber++;
            loadQuestion();
        }
        else {
            Toast.makeText(ScoobyDooHomeActivity.this, "Incorrect", Toast.LENGTH_LONG).show();
        }
        mAnswerField.setText("");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_scooby);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.scooby_doo_home, menu);
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

        if (id == R.id.nav_home_scooby) {
            Intent intent = new Intent(this, ScoobyDooHomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile_scooby) {
            replaceFragments(UserProfileFragment.class, false);
        } else if (id == R.id.nav_leaderboard_scooby) {
            replaceFragments(LeaderboardFragment.class, false);
        } else if (id == R.id.nav_developer_scooby) {

        } else if (id == R.id.nav_logout_scooby) {
            status = getSharedPreferences("login_status", Context.MODE_PRIVATE);
            status.edit().putBoolean("in", false).apply();
            status.edit().putBoolean("first",false).apply();
            mAuth.removeAuthStateListener(mAuthListener);
            mAuth.signOut();
            Intent intent = new Intent(ScoobyDooHomeActivity.this, LoginActivity.class);
            intent.putExtra("launch", 2);
            intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            startActivity(intent);
        } else if (id == R.id.nav_share_scooby) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey checkout my app at https://play.com");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_scooby);
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
            fragmentManager.beginTransaction().replace(R.id.frag_view_scooby_home, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            fragmentManager.beginTransaction().replace(R.id.frag_view_scooby_home, fragment)
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
        fragmentManager.beginTransaction().replace(R.id.frag_view_scooby_home, fragment)
                .addToBackStack(null)
                .commit();
    }


    private void introDisplay() {
        Intent intent = new Intent(ScoobyDooHomeActivity.this, IntroActivity.class);
        startActivity(intent);
    }

    private void checkGameStatus(){

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String gameStarted = dataSnapshot.getValue().toString();
                if(!gameStarted.equals("1"))
                {
                    Intent intent = new Intent(ScoobyDooHomeActivity.this, GameStatusActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mStatusRef.addValueEventListener(listener);
    }

}