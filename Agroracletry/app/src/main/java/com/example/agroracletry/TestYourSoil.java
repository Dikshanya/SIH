package com.example.agroracletry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TestYourSoil extends AppCompatActivity {


    RadioGroup radioGroup;
    Button next;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_your_soil);

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup_yes_no);

        next=findViewById(R.id.next);

        next.setVisibility(View.VISIBLE);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickbuttonMethod(view);
            }
        });

    }


   /* public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButtonYes:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radioButtonNo:
                if (checked){
                    Intent intent1 =new Intent(TestYourSoil.this,Tutorial_steps.class);
                    startActivity(intent1);
                    break;


                }
        }
    }*/

    public void onclickbuttonMethod(View v){
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if(selectedId==-1){
            Toast.makeText(TestYourSoil.this,"Nothing selected", Toast.LENGTH_SHORT).show();
        }
        else{
            switch(selectedId) {
                case R.id.radioButtonYes: {
                    Intent intent1 = new Intent(TestYourSoil.this, Get_pH.class);
                    startActivity(intent1);
                    break;
                }
                case R.id.radioButtonNo: {
                    Intent intent1 = new Intent(TestYourSoil.this, Tutorial_steps.class);
                    startActivity(intent1);
                    break;
                }



            }

        }

    }
}
