package com.nitmz.morphosis.scoobydoo;

import android.content.Intent;
import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;
import com.nitmz.morphosis.R;

public class ScoobyIntroActivity extends IntroActivity {

    Boolean home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setButtonCtaTintMode(BUTTON_CTA_TINT_MODE_TEXT);

        home = getIntent().getBooleanExtra("home",false);

        addSlide(new SimpleSlide.Builder()
                .title("This game is a part of Morphosis, The Annual Technical fest of NIT Mizoram ")
                .image(R.drawable.logo_morphosis)
                .backgroundDark(R.color.colorPrimaryDarkScooby)
                .background(R.color.colorPrimaryScooby)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("You will be greeted with an image which contains all the clues. Just relate all of them and Boom!!" +
                        " you got the answer.  "+" There will be 25 questions ,the one who cracks them first will be the winner")
                .image(R.drawable.find)
                .background(R.color.colorPrimaryScooby)
                .backgroundDark(R.color.colorPrimaryDarkScooby)
                .scrollable(false)
                .build());


        addSlide(new SimpleSlide.Builder()
                .title("You Can Pinch the 'Question Image' to Zoom it ")
                .image(R.drawable.pinch_zoom_in)
                .backgroundDark(R.color.colorPrimaryDarkScooby)
                .background(R.color.colorPrimaryScooby)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("Top players are available on the 'Leaderboard', you can see their current ranks ,of course" +
                        "yours too ...")
                .image(R.drawable.ic_leaderboard_black_100px)
                .backgroundDark(R.color.colorPrimaryDarkScooby)
                .background(R.color.colorPrimaryScooby)
                .scrollable(false)
                .build());





    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!home) {
            Intent intent = new Intent(ScoobyIntroActivity.this, ScoobyDooBNavHome.class);
            startActivity(intent);
        }
    }
}
