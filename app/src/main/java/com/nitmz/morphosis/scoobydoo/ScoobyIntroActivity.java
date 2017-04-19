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
                .title("hai")
                .image(R.drawable.intro1)
                .background(R.color.swipe_refresh_green)
                .backgroundDark(R.color.cardview_dark_background)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("hai")
                .image(R.drawable.intro2)
                .backgroundDark(R.color.cardview_dark_background)
                .background(R.color.swipe_refresh_green)
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
