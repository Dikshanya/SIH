package com.example.agroracletry;

import android.app.Activity;

import android.content.res.AssetFileDescriptor;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Fetch_Details  extends Activity {



    TextView temp_txt,humid_txt,rain_txt,predict_crop,predict_soil;
    ScrollView report;
    TextView cityName;
    Button fetch_but,predict_btn;
    Interpreter tflite;
    float tmp;
    float hmd;
    float rain;
    String pH;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fetch_details);

        temp_txt=findViewById(R.id.temp_txt);
        humid_txt=findViewById(R.id.humid_txt);
        rain_txt=findViewById(R.id.rain_txt);
        predict_crop=findViewById(R.id.predict_crops_txt);
        predict_soil=findViewById(R.id.predict_soil_txt);
        pH=getIntent().getStringExtra("pH");
        fetch_but= findViewById(R.id.fetch_parameters);
        predict_btn= findViewById(R.id.predict);
        fetch_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityName = findViewById(R.id.editTextCityname);
                String cName = cityName.getText().toString();
                findweather(cName);


            }
        });

        predict_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float [][]inputNumber= new float [1][4];
                //float [][] inputNumber= {{(float) 25.587,(float) 53.567,(float)6.506,(float) 156.966},{(float) 27.536,(float) 89.929,(float)6.619,(float) 45.485}};
                       // {(float) 24.485,(float) 83.206,(float)6.1325,(float) 192.231}};
                inputNumber[0][0]= tmp;
                inputNumber[0][1]= hmd;
                inputNumber[0][2]=Float.parseFloat(pH);
                inputNumber[0][3]=(float)192.231;

                float ph = (float) inputNumber[0][2];
                String soil_type="";
                if(ph>=3.5 && ph<=4.0)
                    soil_type="Strongly acidic ! Not suitable for any crop";
                else if(ph>=4.5 && ph<6.0)
                    soil_type="Moderately acidic";
                else if(ph>=6.0 && ph<6.5)
                    soil_type="Slightly acidic";
                else if(ph>=6.5 && ph<7.5)
                    soil_type="Neutral";
                else if(ph>=7.5 && ph<8.5)
                    soil_type="Slightly alakaline";
                else if(ph>=8.5 && ph<=9.0)
                    soil_type="Strongly alkaline";

                float [][] prediction=doInference(inputNumber);
                float [] max=new float[3];
                String [] crops={"rice","wheat","mungbean","Tea","millet","maize","lentil","jute","cofee","cotton",
                        "ground nut","peas","rubber","sugarcane","tobacco","kidney beans","moth beans","coconut","blackgram","adzuki beans",
                        "pigeon peas","chick peas","banana","grapes","apple","mango","muskmelon","orange","papaya","pomegranate","watermelon"};
                int [] index = new int [3];
                for(int j=0;j<1;j++) {
                    max[j] = prediction[j][0];
                    index[j]=0;
                    for (int i = 1; i < 30; i++)
                        if (prediction[j][i] > max[j]) {
                            max[j] = prediction[j][i];
                            index[j]=i;
                        }
                }
                report = findViewById(R.id.report);
                report.setVisibility(View.VISIBLE);
                predict_soil.setText("Your soil is "+soil_type+".");
                predict_crop.setText("Suitable crop for your soil is "+crops[index[0]]);//+','+crops[index[1]]+','+crops[index[2]]);
            }
        });
        try{
            tflite = new Interpreter(loadModelFile());
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public  float [][] doInference(float [][] inputVal){

        float[][] outputVal = new float[1][30];

        tflite.run(inputVal,outputVal);

        //float inferredvalue = outputVal[0][10];
        return outputVal;
    }
    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor= this.getAssets().openFd("model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declaredLength);
    }

    public void findweather(String cName){
       /*String url = "https://openweathermap.org/data/2.5/weather?q="+cName+",India&appid=439d4b804bc8187953eb36d2a8c26a02";

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main_object = response.getJSONObject("main");
                    //JSONObject rain_object = response.getJSONObject("rain");
                    //JSONArray array = response.getJSONArray("weather");
                    //JSONObject object = array.getJSONObject(0);
                    String temp = String.valueOf(main_object.getDouble("temp"));
                    String humidity = String.valueOf(main_object.getDouble("humidity"));
                    //String rain = String.valueOf(rain_object.getDouble("rain"));
                    /*
                    //to convert to celsius
                    float t= Float.parseFloat(temp);

                    tmp= (float) ((t-(float)32)/1.8000);
                    */
                    /*
                    tmp=Float.parseFloat(temp);
                    hmd=Float.parseFloat(humidity);
                    temp_txt.setText(Float.toString(tmp));
                    humid_txt.setText(humidity);
                    //rain_txt.setText(rain);


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

        */
        OkHttpClient client2 = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType2 = MediaType.parse("application/json");
        String city="{\n    \"location\": \""+cName+"\"\n}";
        RequestBody body2 = RequestBody.create(mediaType2, city );
        Request request2 = new Request.Builder()
//                .url("http://192.168.68.110:5000/get_weather")
                .url("http://fe0cada1781b.ngrok.io/get_weather") //use this link , this is a new link. Will be valid for 7 hours
                .method("POST", body2)
                .addHeader("Content-Type", "application/json")
                .build();

        client2.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse2 = response.body().string();


                    Fetch_Details.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String[] splited = myResponse2. split("\\s+");
                            hmd=Float.parseFloat(splited[0]);
                            tmp=Float.parseFloat(splited[1]);
                            rain=Float.parseFloat(splited[2]);
                            temp_txt.setText(Float.toString(tmp));
                            humid_txt.setText(Float.toString(hmd));
                            rain_txt.setText(Float.toString(rain));
                            //temp_txt.setText(myResponse2);
                        }
                    });
                }
            }
        });


    }







}
