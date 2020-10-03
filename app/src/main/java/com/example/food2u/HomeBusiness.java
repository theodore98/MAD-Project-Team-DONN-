package com.example.food2u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HomeBusiness extends AppCompatActivity {

    TextView emailDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_business);

        Intent intent=getIntent();
        String email=intent.getStringExtra("email");
        emailDisplay=(TextView)findViewById(R.id.emailDisp);
        emailDisplay.setText(email);



    }
}