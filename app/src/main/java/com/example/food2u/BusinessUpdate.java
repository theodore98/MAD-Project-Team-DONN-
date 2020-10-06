package com.example.food2u;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BusinessUpdate extends AppCompatActivity {
    Button updateNew;

    EditText nameNew,addressNew,mobileNew,ownerNew,passwordNew,bankNew,acnumberNew;
    TextView emailNew;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_update);

        nameNew=findViewById(R.id.nameUpdate);
        addressNew=findViewById(R.id.addressUpdate);


    }
}