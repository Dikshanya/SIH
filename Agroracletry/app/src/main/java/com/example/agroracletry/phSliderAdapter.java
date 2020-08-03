package com.example.agroracletry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class phSliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public phSliderAdapter(Context context){
        this.context = context;
    }
    public int[] slide_images = {
            R.drawable.ph_step1,
            R.drawable.ph_step2,
            R.drawable.ph_step3,
            R.drawable.ph_step4,
            R.drawable.ph_step5

    };

    public String[] slide_headings = {
            "Step 1:",
            "Step 2:",
            "Step 3:",
            "Step 4:",
            "Step 5:"

    };

    public String[] slide_desc ={
            "Use a trowel to dig a small hole in the area you wish to test."+
                    "This does not need to be deep; about the length of a trowel head.",
            "Break up the soil in the bottom of the hole and remove any larger pieces,"+
                    "which may contain stones and leaves.",
            "Pour some rain or bottled water into the hole and mix it with "+"" +
                    "the soil using the trowel until you have a sludgy consistency.",
            "Wipe the pH meter probe with a cloth or kitchen towel and then place "+
                    "the tip of the probe into the muddy hole, so that it is submerged (approximately 5cm deep) and wait sixty seconds or so for the test to be effective.",
            "When the needle or screen has settled, check the scale and take the value as your reading."

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
