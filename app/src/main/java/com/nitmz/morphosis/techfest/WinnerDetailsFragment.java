package com.nitmz.morphosis.techfest;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitmz.morphosis.R;

import java.util.ArrayList;


public class WinnerDetailsFragment extends Fragment {

    ListView win_details;
    FirebaseDatabase mDB;
    DatabaseReference mRef;
    ArrayList<String> Names;
    ProgressDialog pd;


    public WinnerDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_winner_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        win_details = (ListView) view.findViewById(R.id.win_details_list);
        String event = getArguments().getString("event");
        mDB = FirebaseDatabase.getInstance();
        mRef=mDB.getReference("winners/"+event);
        Names = new ArrayList<>();
        //Body = new ArrayList<>();
        pd = new ProgressDialog(getContext());
        pd.setMessage("Getting Winner(s) details ...");
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.show();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot notification:dataSnapshot.getChildren())
                {
                    String name = notification.getValue().toString();
                    Names.add(name);

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (getContext(),android.R.layout.simple_list_item_1, Names);
                win_details.setAdapter(adapter);
                pd.cancel();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mRef.addValueEventListener(listener);

    }





}
