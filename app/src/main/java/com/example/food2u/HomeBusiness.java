package com.example.food2u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeBusiness extends AppCompatActivity {

    TextView emailDisplay;
    Button foodRedirect,profileRedirect,orderRedirect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_business);

        Intent intent=getIntent();
        final String email=intent.getStringExtra("email");
        emailDisplay=(TextView)findViewById(R.id.emailDisp);
        emailDisplay.setText(email);

        profileRedirect=(Button)findViewById(R.id.profileRedirect);
        profileRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirect=new Intent(HomeBusiness.this,ProfileBusiness.class);
                redirect.putExtra("email",email);
                startActivity(redirect);

            }
        });



    }
}