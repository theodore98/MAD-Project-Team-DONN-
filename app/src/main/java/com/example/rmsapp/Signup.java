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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Signup extends AppCompatActivity {
    EditText txtEmail, txtPassword, txtUsername, txtContact;
    TextView redirect;
    Button btnSignup;
    //Progress bar to display while registering
    ProgressDialog progressDialog;

    //Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //ActionBar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(("Signup"));
        //Enable Back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        txtEmail = findViewById(R.id.emailUser);
        txtPassword = findViewById(R.id.signupPassword);
        txtUsername = findViewById(R.id.userName);
        txtContact = findViewById(R.id.contactUser);
        btnSignup = findViewById(R.id.btnCreateAcc);
        redirect = findViewById(R.id.redirectLogin);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating Account...");

        //Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        //Create and insert customer to database on click "Create Account"
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = txtEmail.getText().toString().trim();
                final String password = txtPassword.getText().toString().trim();
                final String username = txtUsername.getText().toString().trim();
                final String contact = txtContact.getText().toString().trim();

                //Validate
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    //set error and focus to email
                    txtEmail.setError("Invalid Email");
                    txtEmail.setFocusable(true);
                } else if (password.length() < 8) {
                    //set error and focus to password
                    txtPassword.setError("Password should be at least 8 characters ");
                    txtPassword.setFocusable(true);
                } else if (TextUtils.isEmpty(txtEmail.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter an Email", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(txtPassword.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter a Password", Toast.LENGTH_SHORT).show();
                else {
                    registerCustomer(email, password, username, contact); //Register Customer
                }
            }
        });
        //Redirect to login page
        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, CustomerLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerCustomer(String email, String password, final String username, final String contact) {
        //Email and Password validation passed, display progress dialog
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, dismiss dialog and start register activity
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Get user email and uid from auth
                            String email = user.getEmail();
                            String uid = user.getUid();

                            //Use hashmap to store user details in DB when registering
                            HashMap<Object, String> hashMap = new HashMap<>();
                            //Insert information into hashmap
                            hashMap.put("email", email);
                            hashMap.put("uid",uid);
                            hashMap.put("username",username); //options in edit profile
                            hashMap.put("contact",contact);
                            hashMap.put("image","");

                            //Firebase Database Instance
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //Store customer
                            DatabaseReference dbRef = database.getReference("Customers");
                            //Insert hashmap data into database
                            dbRef.child(uid).setValue(hashMap);

                            Toast.makeText(Signup.this, "Account Created Successfully \n"+user.getEmail(),Toast.LENGTH_SHORT);
                            startActivity(new Intent(Signup.this, DashboardActivity.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(Signup.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Error, dismiss the progress dialog and get and show the error message
                progressDialog.dismiss();
                Toast.makeText(Signup.this, ""+e.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        //Go to previous activity
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}

