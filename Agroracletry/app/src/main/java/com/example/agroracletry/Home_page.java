package com.example.agroracletry;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Home_page extends AppCompatActivity {
    Button tutorials,weather_btn,help_btn;
    TextView askus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.home_page);
        tutorials = (Button)findViewById(R.id.prediction);
        weather_btn = (Button)findViewById(R.id.weather);
        help_btn = (Button)findViewById(R.id.help);
        askus = findViewById(R.id.textview_ask);
        tutorials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTutorial_steps();

            }
        });
        weather_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 =new Intent(Home_page.this,Weather.class);
                startActivity(intent1);
            }
        });
        help_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 =new Intent(Home_page.this,Assistance.class);
                startActivity(intent2);
            }
        });
        askus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 =new Intent(Home_page.this,ContactActivity.class);
                startActivity(intent3);
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

    public void openTutorial_steps(){
        Intent intent =new Intent(this,TestYourSoil.class);
        startActivity(intent);
    }

}
