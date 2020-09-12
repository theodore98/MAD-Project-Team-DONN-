package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button btn;
    Button bt1;
    ImageButton bt2;

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myintent = new Intent(MainActivity.this,newfood.class);
                startActivity(myintent);
            }
        });

        bt1 = findViewById(R.id.button5);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent1 = new Intent (MainActivity.this,Edit.class);
                startActivity(intent1);
            }
        });

       bt2 = findViewById(R.id.imageButton);
       bt2.setLongClickable(true);
        bt2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Intent I = new Intent(MainActivity.this,Delete.class);
                startActivity(I);
                return true;
            }
        });



    }
}