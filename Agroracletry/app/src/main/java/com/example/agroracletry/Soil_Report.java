package com.example.agroracletry;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Locale;

public class Soil_Report extends Activity {

    TextView predict_txt;
    Button fetch_but;
    Interpreter tflite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.soil_report);

        predict_txt=findViewById(R.id.cropnameText);
        fetch_but= findViewById(R.id.button);

        fetch_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float [][] inputNumber= {{(float) 25.587,(float) 53.567,(float)6.506,(float) 156.966},{(float) 27.536,(float) 89.929,(float)6.619,(float) 45.485},
                        {(float) 24.485,(float) 83.206,(float)6.1325,(float) 192.231}};

                float [][] prediction=doInference(inputNumber);
                float [] max=new float[3];
                String [] crops={"rice","wheat","mungbean","Tea","millet","maize","lentil","jute","cofee","cotton",
                        "ground nut","peas","rubber","sugarcane","tobacco","kidney beans","moth beans","coconut","blackgram","adzuki beans",
                        "pigeon peas","chick peas","banana","grapes","apple","mango","muskmelon","orange","papaya","pomegranate","watermelon"};

                int [] index = new int [3];
                for(int j=0;j<3;j++) {
                    max[j] = prediction[j][0];
                    index[j]=0;
                    for (int i = 1; i < 30; i++)
                        if (prediction[j][i] > max[j]) {
                            max[j] = prediction[j][i];
                            index[j]=i;
                        }

                }
                predict_txt.setText(crops[index[0]]+','+crops[index[1]]+','+crops[index[2]]);
            }
        });
        try{
            tflite = new Interpreter(loadModelFile());
        }catch (Exception ex){
            ex.printStackTrace();
        }
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

    public  float [][] doInference(float [][] inputVal){

        float[][] outputVal = new float[3][30];

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
}
