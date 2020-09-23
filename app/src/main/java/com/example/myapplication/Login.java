package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
    FirebaseFirestore fStore;
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
                String password = ppassword.getText().toString().trim();

                Log.d("TAG", "onClick: " +pemail.getText().toString());
                Log.d("TAG", "onClick: " +ppassword.getText().toString());

                if(TextUtils.isEmpty(email)) {
                    pemail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    ppassword.setError("Password is required");
                    return;
                }

                if(password.length() < 6){
                    ppassword.setError("Password must be longer than 6 characters");
                    return;
                }

                fAuth.signInWithEmailAndPassword(pemail.getText().toString(),ppassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                        checkUserAccessLevel(authResult.getUser().getUid());

                    }

                })
                        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void checkUserAccessLevel(String Uid) {
        DocumentReference df = fStore.collection("users").document(Uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess:  " +documentSnapshot.getData());

                if(documentSnapshot.getString("isAdmin") != null)
                {
                    startActivity(new Intent(getApplicationContext(),Profile.class));
                    finish();
                }
                else if(documentSnapshot.getString("isUser") != null) {

                    startActivity(new Intent(getApplicationContext(),Profile.class));
                    finish();
                }

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