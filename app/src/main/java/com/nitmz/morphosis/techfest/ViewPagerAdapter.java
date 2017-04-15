package com.nitmz.morphosis.techfest;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nitmz.morphosis.R;

import java.util.ArrayList;


public class ViewPagerAdapter  extends PagerAdapter{

    private ArrayList<Integer> images;
    private LayoutInflater inflater;
    private Context context;

    public ViewPagerAdapter( Context context,ArrayList<Integer> images) {
        this.images = images;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.home_viewpager_temp, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.viewpager_image);

        imageView.setImageResource(images.get(position));

        view.addView(imageLayout, 0);

        return imageLayout;
    }
}

