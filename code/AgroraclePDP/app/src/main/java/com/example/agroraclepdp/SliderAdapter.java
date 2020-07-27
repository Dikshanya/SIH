package com.example.agroraclepdp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }
    public int[] slide_images = {
            R.drawable.step_one,
            R.drawable.step_two,
            R.drawable.step_three,
            R.drawable.step_four

    };

    public String[] slide_headings = {
            "Step 1:",
            "Step 2:",
            "Step 3:",
            "Step 4:",

    };

    public String[] slide_desc ={
            "Locating 5 points in your field/garden.",
            "Dig a V-shaped pit 2-4 inches deep.",
            "Mix the samples thoroughly.",
            "Remove all foreign particles like "+
                    "roots,pebbles and gravels."

    };
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view== (RelativeLayout) object;
    }
    public Object instantiateItem(ViewGroup container , int position){
        layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.steps_item,container,false);

        ImageView slideImageview = (ImageView) view.findViewById(R.id.steps);
        TextView slideHeading = (TextView) view.findViewById(R.id.step_title);
        TextView slideDescription = (TextView) view.findViewById(R.id.desc1);

        slideImageview.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_desc[position]);

        container.addView(view);
        return view;
    }

    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((RelativeLayout) object);
    }
}