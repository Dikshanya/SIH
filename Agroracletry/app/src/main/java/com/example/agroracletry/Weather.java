package com.example.agroracletry;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class Weather extends AppCompatActivity {

    TextView temp_txt,humid_txt,desc_txt,cityname,title_tmp,title_hmd,title_pressure,pressure_txt;
    TextView wind_txt,title_wind;
    ImageView weather_icon;
    Button chk_weather_btn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.weather);

        temp_txt = findViewById(R.id.temp_text);
        humid_txt = findViewById(R.id.humidity_txt);
        pressure_txt = findViewById(R.id.presure_txt);
        wind_txt = findViewById(R.id.wind_txt);
        title_tmp = findViewById(R.id.titletmp);
        title_hmd = findViewById(R.id.titlehumidity);
        title_pressure = findViewById(R.id.titlePressure);
        title_wind = findViewById(R.id.titlewind);
        weather_icon = findViewById(R.id.sun_img);
        desc_txt = findViewById(R.id.desc);
        cityname = findViewById(R.id.Cityname);

        chk_weather_btn = findViewById(R.id.check_weather);

        chk_weather_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cName = cityname.getText().toString();
                fetchweather(cName);
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

    public void fetchweather(String cName){
        String url = "https://openweathermap.org/data/2.5/weather?q="+cName+",India&appid=439d4b804bc8187953eb36d2a8c26a02";

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main_object = response.getJSONObject("main");
                    JSONObject wind_object = response.getJSONObject("wind");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    String temp = String.valueOf(main_object.getDouble("temp"));
                    String humidity = String.valueOf(main_object.getDouble("humidity"));
                    String pressure = String.valueOf(main_object.get("pressure"));
                    String desc = object.getString("description");
                    String wind = String.valueOf(wind_object.getDouble("speed"));
                    /*
                    //to convert to celsius
                    float t= Float.parseFloat(temp);

                    tmp= (float) ((t-(float)32)/1.8000);
                    */
                    weather_icon.setVisibility(View.VISIBLE);
                    title_tmp.setVisibility(View.VISIBLE);
                    title_hmd.setVisibility(View.VISIBLE);
                    title_pressure.setVisibility(View.VISIBLE);
                    title_wind.setVisibility(View.VISIBLE);
                    temp_txt.setVisibility(View.VISIBLE);
                    temp_txt.setText(temp+" \u2103");
                    humid_txt.setVisibility(View.VISIBLE);
                    humid_txt.setText(humidity+" %");
                    desc_txt.setVisibility(View.VISIBLE);
                    desc_txt.setText(desc);
                    pressure_txt.setVisibility(View.VISIBLE);
                    pressure_txt.setText(pressure+" hPa");
                    wind_txt.setVisibility(View.VISIBLE);
                    wind_txt.setText(wind+" km/h");




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);

    }

}
