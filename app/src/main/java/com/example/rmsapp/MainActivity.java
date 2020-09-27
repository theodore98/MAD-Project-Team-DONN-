package com.example.rmsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView Signup;
    EditText emailLogin, passwordLogin;
    Button submitLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //map variables
        Signup = findViewById(R.id.txtSignup);
        emailLogin = findViewById(R.id.loginEmail);
        passwordLogin = findViewById(R.id.loginPassword);
        submitLogin = findViewById(R.id.btnLogin);

        //onclick method for signup
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Signup.class);
                startActivity(intent);
            }
        });

    }
}