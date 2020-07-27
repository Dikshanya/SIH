package com.example.agroraclepdp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Upload_image extends AppCompatActivity {

    private Button test_button;
    ImageView mImageView;
    Button mChooseBtn;
    TextView s_text;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_image);
        s_text=findViewById(R.id.success_text);
        test_button=findViewById(R.id.results);
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
            s_text.setVisibility(View.VISIBLE);
        }
    }

    public void openSoilReport(){
        Intent intent =new Intent(this,Soil_Report.class);
        startActivity(intent);
    }
}