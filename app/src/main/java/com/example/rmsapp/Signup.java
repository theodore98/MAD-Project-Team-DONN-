package com.example.rmsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    EditText txtEmail, txtUsername, txtPassword, txtContact;
    TextView redirect;
    Button btnSignup;
    DatabaseReference dbRef;
    Customer cus;

    //Method to clear all user inputs
    private void clearControls() {
        txtEmail.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtContact.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        txtEmail = findViewById(R.id.emailUser);
        txtUsername = findViewById(R.id.Username);
        txtPassword = findViewById(R.id.signupPassword);
        txtContact = findViewById(R.id.signupContact);
        btnSignup = findViewById(R.id.btnCreateAcc);
        redirect = findViewById(R.id.redirectLogin);
        cus = new Customer();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Customer");
                try {
                    if (TextUtils.isEmpty(txtEmail.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter an Email", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtUsername.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Username", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtPassword.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Password", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtContact.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Contact Number", Toast.LENGTH_SHORT).show();
                    else {
                        //Take inputs from user and assign them to instance(cus)
                        cus.setEmail(txtEmail.getText().toString().trim());
                        cus.setUsername((txtUsername.getText().toString().trim()));
                        cus.setPassword(txtPassword.getText().toString().trim());
                        cus.setContact(txtContact.getText().toString().trim());

                        //Insert into the database..
                        dbRef.push().setValue(cus);

                        //Feedback to the user via a Toast
                        Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid Data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}