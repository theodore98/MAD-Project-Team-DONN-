package com.example.rmsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    TextView Signup, RecoverPassword;
    EditText emailLogin, passwordLogin;
    Button submitLogin;
    //Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;
    //Progress Dialog
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ActionBar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(("Login"));
        //Enable Back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        //map variables
        Signup = findViewById(R.id.txtSignup);
        RecoverPassword = findViewById(R.id.recoverPassword);
        emailLogin = findViewById(R.id.loginEmail);
        passwordLogin = findViewById(R.id.loginPassword);
        submitLogin = findViewById(R.id.btnLogin);

        submitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Input details
                String email = emailLogin.getText().toString().trim();
                String password = passwordLogin.getText().toString().trim();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    //Invalid email, show error
                    emailLogin.setError("Invalid Email");
                    emailLogin.setFocusable(true);
                }
                else{
                    //valid Email
                    loginUser(email,password);
                }

            }
        });

        //onclick method for signup
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Signup.class);
                startActivity(intent);
                finish();
            }
        });

        //onclick for Forgot Password
        RecoverPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecoverPassword();
            }
        });
        
        //Initialize progress dialog
        progressDialog = new ProgressDialog(this);

    }

    private void showRecoverPassword() {
        //Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");

        //Set linear layout
        LinearLayout linearLayout = new LinearLayout(this);

        //Dialog views
        final EditText emailEt = new EditText(this);
        emailEt.setHint("Email Address");
        emailEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        //Set the min width to fit letters regardless of length
        emailEt.setMinEms(16);

        linearLayout.addView(emailEt);
        linearLayout.setPadding(10,10,10,10);

        builder.setView(linearLayout);

        //Recover button
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Input Email
                String email = emailEt.getText().toString().trim();
                beginRecovery(email);
            }
        });

        //Cancel Button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        //show dialog
        builder.create().show();

    }

    //Recover method
    private void beginRecovery(String email) {
        //Display Progress Dialog
        progressDialog.setMessage("Sending Email");
        progressDialog.show();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Operation Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                //Retrieve and display correct error message
                Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void loginUser(String email, String password) {
        //Display Progress Dialog
        progressDialog.setMessage("Logging In..");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Dismiss progress dialog
                            progressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Get user email and uid from auth
                            String email = user.getEmail();
                            String uid = user.getUid();
                            //Use hashmap to store user details in DB when registering
                            HashMap<Object, String> hashMap = new HashMap<>();
                            //Insert information into hashmap
                            hashMap.put("email", email);
                            hashMap.put("uid",uid);
                            hashMap.put("username", ""); //options in edit profile
                            hashMap.put("contact","");
                            hashMap.put("image","");

                            //Firebase Database Instance
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //Store customer
                            DatabaseReference dbRef = database.getReference("Customers");
                            //Insert hashmap data into database
                            dbRef.child(uid).setValue(hashMap);
                            //User logged in
                            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                            finish();
                        } else {
                            //Dismiss progress dialog
                            progressDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Dismiss progress dialog
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
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