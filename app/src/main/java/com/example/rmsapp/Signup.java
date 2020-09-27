package com.example.rmsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signup extends AppCompatActivity {
    EditText txtEmail, txtUsername, txtPassword, txtContact;
    TextView redirect;
    Button btnSignup;
    DatabaseReference dbRef;
    Customer cus;
    //Progress bar to display while registering
    ProgressDialog progressDialog;
    int counter;

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
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating Account...");

        //enable back button
       // ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);

        //Database reference
        dbRef = FirebaseDatabase.getInstance().getReference().child("Customer");


        //counter
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    counter = (int)snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Create and insert customer to database on click "Create Account"
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (TextUtils.isEmpty(txtEmail.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter an Email", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtUsername.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Username", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtPassword.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Password", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtContact.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Contact Number", Toast.LENGTH_SHORT).show();
                 /*   else if(Patterns.EMAIL_ADDRESS.matcher((CharSequence) txtEmail).matches()){
                        //set error and focus to email
                        txtEmail.setError("Invalid Email");
                        txtEmail.setFocusable(true);
                    } */
                    else if(txtPassword.length()<8){
                        //set error and focus to password
                        txtPassword.setError("Password should be at least 8 characters ");
                        txtPassword.setFocusable(true);
                    }
                    else if(txtContact.length()!=10){
                        //set error and focus to contact
                        txtContact.setError("Invalid Contact Number Format");
                        txtContact.setFocusable(true);
                    }
                    else {
                        //Take inputs from user and assign them to instance(cus)
                        cus.setEmail(txtEmail.getText().toString().trim());
                        cus.setUsername((txtUsername.getText().toString().trim()));
                        cus.setPassword(txtPassword.getText().toString().trim());
                        cus.setContact(txtContact.getText().toString().trim());
                        //method to set value with counter and insert into database
                        dbRef.child("Cus"+(counter + 1)).setValue(cus);

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