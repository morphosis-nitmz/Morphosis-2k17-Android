package com.nitmz.morphosis.techfest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nitmz.morphosis.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DevelopersActivity extends AppCompatActivity {

    List<String> mWebTeam;

    ListView mWebTeamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);

        mWebTeam = new ArrayList<>(Arrays.asList(
                "Akash Singh",
                "Saurav Saha",
                "Sunil Kumar",
                "Jereemi Bentham",
                "Sunday Lalbiaknia",
                "Shantanu Acharya",
                "Anki Reddy Narayana Reddy"
        ));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                mWebTeam);

        mWebTeamList = (ListView) findViewById(R.id.web_team_list_view);
        mWebTeamList.setAdapter(adapter);
    }
}
