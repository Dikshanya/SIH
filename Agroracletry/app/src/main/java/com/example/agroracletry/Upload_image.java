package com.example.agroracletry;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;


public class Upload_image extends AppCompatActivity {

    private Button test_button;
    ImageView mImageView;
    Button mChooseBtn;
    TextView s_text,rgb_txt;
    Button get_parameters;
    float red;
    float blue;
    float green;
    String pH;


    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_image);
        s_text=findViewById(R.id.success_text);
        rgb_txt=findViewById(R.id.rgb_text);
        test_button=findViewById(R.id.results);
        get_parameters=findViewById(R.id.get_parameters);

        get_parameters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFetchDetails();

            }
        });
        test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSoilReport();

            }
        });

        mImageView=findViewById(R.id.soil_img);
        mChooseBtn=findViewById(R.id.choose_img);

        mChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_CODE);
                    }
                    else{

                        pickImageFromGallery();


                    }
                }
                else{
                    pickImageFromGallery();


                }



            }
        });


    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        startActivityForResult(intent, IMAGE_PICK_CODE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        switch (requestCode){
            case PERMISSION_CODE: {
                if(grantResults.length >0 && grantResults[0]==
                PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }
                else {
                    Toast.makeText(this,"Permission Denied..!",Toast.LENGTH_SHORT).show();
                }
            }
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {



        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            mImageView.setImageURI(data.getData());

            Uri imageUri =data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                int redColors = 0;
                int greenColors = 0;
                int blueColors = 0;
                int pixelCount = 0;

                for (int y = 0; y < bitmap.getHeight(); y++)
                {
                    for (int x = 0; x < bitmap.getWidth(); x++)
                    {
                        int c = bitmap.getPixel(x, y);
                        if(Color.red(c)==255 && Color.green(c)==255 && Color.blue(c)==255)
                            continue;
                        else
                        {
                            pixelCount++;
                            redColors += Color.red(c);
                            greenColors += Color.green(c);
                            blueColors += Color.blue(c);
                        }
                    }
                }
// calculate average of bitmap r,g,b values
                red = ((float)redColors/(float)pixelCount);
                green = ((float)greenColors/(float)pixelCount);
                blue = ((float)blueColors/(float)pixelCount);

               rgb_txt.setText(Float.toString(red)+','+Float.toString(blue)+','+Float.toString(green));

            } catch (IOException e) {
                e.printStackTrace();
            }










        }
    }

    public void openSoilReport(){
        //Intent intent =new Intent(this,Soil_Report.class);
        //startActivity(intent);
        //////Code to connect to pH model
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        String rgb_content="{\n    \"rgb\": ["+red + ","+ green +","+blue+"] \n}";
        //String rgb_content="{\n    \"rgb\": [Chennai] \n}";;
        RequestBody body = RequestBody.create(mediaType, rgb_content ); //These are the RGB input values
        Request request = new Request.Builder().url(" http://fe0cada1781b.ngrok.io/get_ph")//.url("http://463dbe79dc82.ngrok.io/get_ph ")
                //This is my ip and port. You can either replace with yours and run or 									run the below line (using my server)
//        .url(" http://2da0c30ad11f.ngrok.io/get_ph") .url("http://192.168.68.110:5000/get_ph")  //This is the url I got using ngrok where it hosts my server to everyone
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    pH=myResponse;

                    Upload_image.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rgb_txt.setText("It is "+myResponse);
                            s_text.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });




    }

    public void openFetchDetails(){
        Intent intent = new Intent(this,Fetch_Details.class);
        intent.putExtra("pH",pH);
        startActivity(intent);
    }
}
