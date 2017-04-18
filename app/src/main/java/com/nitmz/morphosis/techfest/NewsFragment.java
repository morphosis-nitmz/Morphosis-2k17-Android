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

public class NewsFragment extends Fragment {

    ListView news_list;
    FirebaseDatabase mDB;
    DatabaseReference mRef;
    ArrayList<String> Title;
    //ArrayList<String> Body;
    ProgressDialog pd;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        news_list = (ListView) view.findViewById(R.id.news_list_view);
        mDB = FirebaseDatabase.getInstance();
        mRef=mDB.getReference("news");
        Title = new ArrayList<>();
        //Body = new ArrayList<>();
        pd = new ProgressDialog(getContext());
        pd.setMessage("Fetching Latest News ...");
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.show();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot notification:dataSnapshot.getChildren()) {
                    String title = notification.child("title").getValue().toString();
                    String body = notification.child("body").getValue().toString();

                   Title.add(title+"\n"+"\n"+body);

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>
                        (getContext(),android.R.layout.simple_list_item_1, Title);
                news_list.setAdapter(adapter);

                /*news_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        HashMap<String,String> map = new HashMap<>();
                        map.put("title",Names.get(position));
                        map.put("body",Body.get(position));

                        ((TechfestHomeActivity)getActivity()).
                                replaceFragments(NewsDetailsFragment.class,map);

                    }
                });*/
                pd.cancel();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mRef.addValueEventListener(listener);

    }
}
