package com.example.agroracletry;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.Locale;

public class Get_pH extends AppCompatActivity {
    Button next;
    EditText ph_value;
    private ViewPager phSlideViewPager;
    private LinearLayout phDotLayout;

    private phSliderAdapter phsliderAdapter;
    private TextView[] phDots;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.get_ph_details);
        next = (Button) findViewById(R.id.ph_next);
        ph_value = findViewById(R.id.ph_value);

       next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 =new Intent(Get_pH.this,Fetch_Details.class);
                String pH = ph_value.getText().toString();
                intent1.putExtra("pH",pH);
                startActivity(intent1);

            }
        });
        phSlideViewPager= (ViewPager) findViewById(R.id.slideviewpager_ph);
        phDotLayout= (LinearLayout) findViewById(R.id.dots_layout);

        phsliderAdapter= new phSliderAdapter(this);
        phSlideViewPager.setAdapter(phsliderAdapter);
        addDotsIndicator(0);
        phSlideViewPager.addOnPageChangeListener(viewListener);
    }
    public void openUpload_image(){
        Intent intent =new Intent(this,Upload_image.class);
        startActivity(intent);
    }


    public void addDotsIndicator(int position){
        phDots = new TextView[5];
        phDotLayout.removeAllViews();

        for(int i=0; i<phDots.length; i++){
            phDots[i]=new TextView(this);
            phDots[i].setText(Html.fromHtml("&#8226"));
            phDots[i].setTextSize(35);
            phDots[i].setTextColor(getResources().getColor(R.color.colorPrimary));
            phDotLayout.addView(phDots[i]);
        }
        if(phDots.length>0){
            phDots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        //save data to shared preferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_lang", lang);
        editor.apply();

    }

    //load languages saved in shared preferences
    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_lang", "");
        setLocale(language);
    }
}
