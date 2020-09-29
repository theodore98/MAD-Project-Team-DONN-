package com.example.rmsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView Signup;
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
            }
        });

        //Initialize progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging In..");

    }

    private void loginUser(String email, String password) {
        //Display Progress Dialog
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