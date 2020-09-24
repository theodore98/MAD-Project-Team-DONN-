package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
        fAuth = FirebaseAuth.getInstance();
        loginbtn = findViewById(R.id.loginbtn);
        gotoRegister = findViewById(R.id.gotoregis);

        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                            startActivity(new Intent(Login.this, Profile.class));
                        }
                        else
                        {
                            Toast.makeText(Login.this, "failed to login", Toast.LENGTH_SHORT).show();
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