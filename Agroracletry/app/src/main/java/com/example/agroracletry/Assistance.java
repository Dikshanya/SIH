package com.example.agroracletry;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Assistance extends AppCompatActivity {

    ListView listView;

    int[] assistance_images = {R.drawable.allcrop, R.drawable.finance, R.drawable.weatheralert, R.drawable.insurance};
    String [] comments ={"KNOW ALL CROP INFO:","FARMER WELFARE:","FARMER WEATHER ALERT:","FARMER FINANCIAL AID:"};
    String[] assistance_links = {"https://farmer.gov.in/","http://agricoop.gov.in/programmesandschemes/credit","https://mausam.imd.gov.in/imd_latest/contents/meteorological-agriculture-services.php","http://agricoop.gov.in/divisiontype/rainfed-farming-system/programmes-schemes-new-initiatives"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.assistance_page);
        //finding listview
        listView = findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter((ListAdapter) customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg0, int i, long l) {

                Intent viewIntent = new Intent("android.intent.action.VIEW",
                                Uri.parse(assistance_links[i]));
                startActivity(viewIntent);

            }
        });




    }

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


    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return assistance_images.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.row_assistance,null);
            //getting view in row_data

            ImageView image = view1.findViewById(R.id.images);
            TextView comment = view1.findViewById(R.id.comments);


            image.setImageResource(assistance_images[i]);
            comment.setText(comments[i]);
            return view1;
        }
    }
}
