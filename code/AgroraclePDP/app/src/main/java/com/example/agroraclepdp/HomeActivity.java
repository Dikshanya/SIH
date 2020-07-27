package com.example.agroraclepdp;

//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    Button tutorials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tutorials = (Button)findViewById(R.id.tutorial);
        tutorials.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // use Intent
                Intent intent1= new Intent(HomeActivity.this, TutorialActivity.class);
                startActivity(intent1);
            }
        });
    }


}
