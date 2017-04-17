package com.nitmz.morphosis.techfest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.nitmz.morphosis.R;

public class AboutMorphosisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_morphosis);
        TextView about=(TextView)findViewById(R.id.about_morphosis);
        about.setText(R.string.about);
    }


}
