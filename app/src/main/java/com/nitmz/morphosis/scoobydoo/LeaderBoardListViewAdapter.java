package com.nitmz.morphosis.scoobydoo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nitmz.morphosis.R;

import java.util.ArrayList;


public class LeaderBoardListViewAdapter extends ArrayAdapter<LeaderBoardUserObject> {

    private ArrayList<LeaderBoardUserObject> mDataset;
    private Context mContext;

    private static class ViewHolder {
        TextView score;
        TextView username;
        ImageView userPhoto;
    }

    public LeaderBoardListViewAdapter(ArrayList<LeaderBoardUserObject> data, Context context) {
        super(context, R.layout.leaderboard_listview_template, data);
        this.mDataset = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LeaderBoardUserObject entry = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.leaderboard_listview_template, parent, false);
            viewHolder.score = (TextView) convertView.findViewById(R.id.leaderboard_user_score);
            viewHolder.username = (TextView) convertView.findViewById(R.id.leaderboard_user_name);
            viewHolder.userPhoto = (ImageView) convertView.findViewById(R.id.leaderboard_user_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.score.setText(Integer.toString(entry.getScore()));
        viewHolder.username.setText(entry.getUsername());
        Glide.with(getContext())
                .load(entry.getPurl())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.ic_account_circle_white_48dp)
                .placeholder(R.drawable.ic_account_circle_white_48dp)
                .dontAnimate()
                .into(viewHolder.userPhoto);

        return convertView;
    }
}
