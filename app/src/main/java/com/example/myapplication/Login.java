package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

public class Login extends AppCompatActivity {

    EditText pemail, ppassword;
    Button loginbtn, gotoRegister;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pemail = findViewById(R.id.pemailview);
        ppassword = findViewById(R.id.ppassword);
        loginbtn = findViewById(R.id.loginbtn);
        gotoRegister = findViewById(R.id.gotoregis);
        final Loading loading = new Loading(Login.this);

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();

        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                },30000);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = pemail.getText().toString().trim();
                String pass = ppassword.getText().toString().trim();

                if(email.isEmpty())
                {
                    pemail.setError("Email is required");
                    pemail.requestFocus();

                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    pemail.setError("Password is required!");
                    pemail.requestFocus();
                }
                if(pass.length()<6)
                {
                    ppassword.setError("Must enter 6 char");
                    ppassword.requestFocus();
                    return;
                }

                fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            loading.startLoadingDialog();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                }
                            },30000);
                            startActivity(new Intent(Login.this, Profile.class));
                        }
                        else
                        {
                            Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

    }


    protected void onStart()
    {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),Profile.class));
            finish();
        }

    }



}