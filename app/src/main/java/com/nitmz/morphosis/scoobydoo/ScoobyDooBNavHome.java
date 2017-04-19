package com.nitmz.morphosis.scoobydoo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.github.chrisbanes.photoview.PhotoView;
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

public class ScoobyDooBNavHome extends AppCompatActivity {


    SharedPreferences status;

    ProgressDialog pd;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private StorageReference mStorageReference;
    private FirebaseDatabase mDB;
    private DatabaseReference mUsersRef;
    private DatabaseReference mScoreRef;

    DatabaseReference mStatusRef;
    DatabaseReference mCurrentQuestionRef;
    DatabaseReference mHintRef;
    DatabaseReference mSolutionRef;
    DatabaseReference mSolvedRank;
    DatabaseReference mUserRankRef;
    DatabaseReference mCalcScoreRef;
    DatabaseReference mGetCRank;



    private EditText mAnswerField;
    TextView mTitle;
    TextView mHint;
    TextInputLayout mAnswer;
    ProgressDialog mDialog;
    private View mFragView;
    private View mHomeView;

    int totalq = 25;

    int crank = 99999;


    String mQuestionTitle;
    int mQuestionNumber;
    Button mAnswerButton;
    BottomNavigationView navigation;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mHomeView.setVisibility(View.VISIBLE);
                    mFragView.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_dashboard:
                    replaceFragments(UserProfileFragment.class, false);
                    return true;
                case R.id.navigation_leaderboard:
                    replaceFragments(LeaderboardFragment.class, false);
                    return true;
                case R.id.navigation_help:
                    introDisplay();
                    navigation.setSelectedItemId(R.id.navigation_home);
                    return false;
            }
            return false;
        }

    };

    public void logout(){
        mAuth.removeAuthStateListener(mAuthListener);
        status = getSharedPreferences("login_status", Context.MODE_PRIVATE);
        status.edit().putBoolean("in", false).apply();
        status.edit().putBoolean("first",false).apply();
        mAuth.signOut();
        Intent intent = new Intent(ScoobyDooBNavHome.this, LoginActivity.class);
        intent.putExtra("launch", 2);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void share(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey checkout my app at https://play.com");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scooby_doo_bnav_home);

        status = getSharedPreferences("login_status", Context.MODE_PRIVATE);
        mDB = FirebaseDatabase.getInstance();
        mStatusRef = mDB.getReference("gameStarted");


        pd = new ProgressDialog(this);
        pd.setMessage("Loading Puzzle ....");
        pd.show();
        pd.setCanceledOnTouchOutside(false);



        checkGameStatus();

        if(!status.getBoolean("first",false)) {
            introDisplay();
            status.edit().putBoolean("first",true).apply();
        }

        // Initialize fragment views
        mFragView =findViewById(R.id.frag_view_scooby_home);
        mHomeView =findViewById(R.id.scooby_home_view);

        mAnswer = (TextInputLayout)findViewById(R.id.answer_in);

        navigation = (BottomNavigationView) findViewById(R.id.bnavigation_home);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mDialog =new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(mAuth.getCurrentUser() == null){
                    Intent intent = new Intent(ScoobyDooBNavHome.this,LoginActivity.class);
                    intent.putExtra("launch", 2);
                    intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };

        mAuth.addAuthStateListener(mAuthListener);

        mCurrentQuestionRef = mDB.getReference("users/"+mAuth.getCurrentUser().getUid()+"/score");

        mAnswerField = (EditText) findViewById(R.id.answer_text);
        mTitle = (TextView) findViewById(R.id.title_view);
        mHint = (TextView) findViewById(R.id.hint_view);
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
        mQuestionNumber = 1;
        checkCurrentQuestion();

    }

    private void checkCurrentQuestion(){

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String currentQuestion = dataSnapshot.getValue().toString();
                if(!currentQuestion.equals(null))
                {
                    if(Integer.parseInt(currentQuestion) >= totalq)
                    {
                        mAnswerField.setVisibility(View.INVISIBLE);
                        mAnswerButton.setVisibility(View.INVISIBLE);
                        mHint.setVisibility(View.INVISIBLE);
                        mTitle.setVisibility(View.INVISIBLE);
                        mAnswer.setVisibility(View.INVISIBLE);

                        mQuestionNumber = 26;
                        PhotoView questionImageView = (PhotoView) findViewById(R.id.question_image_view);

                        mStorageReference = FirebaseStorage.getInstance().getReference();
                        StorageReference pathReference = mStorageReference.child(mQuestionNumber+".jpg");

                        Glide.with(ScoobyDooBNavHome.this)
                                .using(new FirebaseImageLoader())
                                .load(pathReference)
                                .dontAnimate()
                                .placeholder(R.drawable.lo)
                                .into(questionImageView);

                        pd.cancel();


                    } else {

                        mAnswerField.setVisibility(View.VISIBLE);
                        mAnswerButton.setVisibility(View.VISIBLE);
                        mHint.setVisibility(View.VISIBLE);
                        mTitle.setVisibility(View.VISIBLE);
                        mAnswer.setVisibility(View.VISIBLE);
                        mQuestionNumber = Integer.parseInt(currentQuestion)+1;

                        loadQuestion();
                        loadHint();

                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mCurrentQuestionRef.addValueEventListener(listener);

    }


    private void loadQuestion() {
        mQuestionTitle = "Question" + mQuestionNumber;
        mTitle.setText(mQuestionTitle);

        PhotoView questionImageView = (PhotoView) findViewById(R.id.question_image_view);

        mStorageReference = FirebaseStorage.getInstance().getReference();
        StorageReference pathReference = mStorageReference.child(mQuestionNumber+".jpg");

        Glide.with(ScoobyDooBNavHome.this)
                .using(new FirebaseImageLoader())
                .load(pathReference)
                .dontAnimate()
                .into(questionImageView);

        pd.cancel();
    }

    private void loadHint()
    {
        mHintRef = mDB.getReference("hints/"+mQuestionNumber);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String hint = dataSnapshot.getValue().toString();
                if(!hint.equals("0"))
                mHint.setText(hint);
                else {
                    mHint.setText("");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mHintRef.addValueEventListener(listener);


    }


    private void checkAnswer() {

        pd.show();

        final String answer = mAnswerField.getText().toString().trim().toLowerCase();

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        mSolutionRef = mDB.getReference("solutions/"+mQuestionNumber);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String canswer = dataSnapshot.getValue().toString().trim().toLowerCase();

                if(answer.equals(canswer)){

                    /*if (mQuestionNumber < 10) {
                        mUsersRef.child(mAuth.getCurrentUser().getUid()).child("score").
                                setValue("0" + mQuestionNumber);
                        mScoreRef.child(mAuth.getCurrentUser().getUid()).setValue("0" + mQuestionNumber);
                    } else {
                        mUsersRef.child(mAuth.getCurrentUser().getUid()).child("score").
                                setValue(mQuestionNumber);
                        mScoreRef.child(mAuth.getCurrentUser().getUid()).setValue(mQuestionNumber);
                    }*/

                    /*mQuestionNumber++;
                    checkCurrentQuestion();
                    mAnswerField.setText("");*/

                    checkRank();

                } else {
                    pd.cancel();
                    Toast.makeText(ScoobyDooBNavHome.this, "Incorrect, Try again :-)", Toast.LENGTH_LONG).show();
                }
                mAnswerField.setText("");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mSolutionRef.addListenerForSingleValueEvent(listener);


    }

    private void checkRank() {

        mSolvedRank = mDB.getReference("solved/"+mQuestionNumber);
        mUserRankRef = mDB.getReference("users/"+mAuth.getCurrentUser().getUid()+"/rank/"+mQuestionNumber);


        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String rank = dataSnapshot.getValue().toString();

                    int prank = Integer.parseInt(rank) + 1;

                    mSolvedRank.setValue(Integer.toString(prank));

                    mUserRankRef.setValue(Integer.toString(prank));

                    calcCRank(0);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mSolvedRank.addListenerForSingleValueEvent(listener);
    }

    public void getCRank()
    {
        mGetCRank = mDB.getReference("users/"+mAuth.getCurrentUser().getUid()+"/crank");

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String crank = dataSnapshot.getValue().toString();
                calcCRank(Integer.parseInt(crank));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mGetCRank.addListenerForSingleValueEvent(listener);

    }


    public void calcCRank(int Crank)
    {
        mCalcScoreRef = mDB.getReference("users/"+mAuth.getCurrentUser().getUid()+"/rank");
        crank = Crank;

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child:dataSnapshot.getChildren())
                {
                    crank = crank + Integer.parseInt(child.getValue().toString());
                }

                mDB.getReference("users/"+mAuth.getCurrentUser().getUid()+"/crank").setValue(Integer.toString(crank));

                crank=99999;

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
                checkCurrentQuestion();
                mAnswerField.setText("");
                pd.cancel();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mCalcScoreRef.addListenerForSingleValueEvent(listener);


    }






    @Override
    public void onBackPressed() {

            if(getSupportFragmentManager().getBackStackEntryCount()==0)
            {
                if(mHomeView.getVisibility()==View.GONE)
                {

                    mHomeView.setVisibility(View.VISIBLE);
                    mFragView.setVisibility(View.GONE);
                    setTitle("Scooby Doo");
                    getSupportFragmentManager().beginTransaction().
                            remove(getSupportFragmentManager().
                                    findFragmentById(R.id.frag_view_scooby_home)).commit();
                    return;
                }
            }
            super.onBackPressed();
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

        Intent intent = new Intent(ScoobyDooBNavHome.this, ScoobyIntroActivity.class);
        intent.putExtra("home",true);
        startActivity(intent);

    }

    private void checkGameStatus(){

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String gameStarted = dataSnapshot.getValue().toString();
                if(!gameStarted.equals("1"))
                {
                    Intent intent = new Intent(ScoobyDooBNavHome.this, GameStatusActivity.class);
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
