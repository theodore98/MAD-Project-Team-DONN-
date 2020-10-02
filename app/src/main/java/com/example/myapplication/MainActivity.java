package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText pfname, plname, pemail, ppnview, ppassword;
    Button maddbtn, gotoLogin;
    FirebaseAuth fAuth;
    String userID;
    Boolean valid = true;
    DatabaseReference reference;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        pfname = findViewById(R.id.pfnview);
        plname = findViewById(R.id.plnview);
        pemail = findViewById(R.id.pemailview);
        ppnview = findViewById(R.id.ppnview);
        ppassword = findViewById(R.id.ppassword);
        maddbtn = findViewById(R.id.addbtn);
        gotoLogin =findViewById(R.id.gotologin);
        final Loading loading = new Loading(MainActivity.this);

        fAuth = FirebaseAuth.getInstance();

        maddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkField(pfname);
                checkField(plname);
                checkField(pemail);
                checkField(ppassword);
                checkField(ppnview);

                final String email = pemail.getText().toString().trim();
                String password = ppassword.getText().toString().trim();
                final String fname = pfname.getText().toString().trim();
                final String lname =  plname.getText().toString().trim();
                final String phone =  ppnview.getText().toString().trim();


                if(password.length() < 6){
                    ppassword.setError("Password must be longer than 6 characters");
                    return;
                }

                if (valid) {

                    fAuth.createUserWithEmailAndPassword(pemail.getText().toString(),ppassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser admin  = fAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "User Created", Toast.LENGTH_SHORT).show();


                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("fName", fname);
                            userInfo.put("lName", lname);
                            userInfo.put("email", email);
                            userInfo.put("phone", phone);

                            userInfo.put("isAdmin", "1");

                            FirebaseDatabase.getInstance().getReference().child("admin").push()
                                    .setValue(userInfo)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.i("tag", "OnComplete");
                                    loading.startLoadingDialog();
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            loading.dismissDialog();
                                        }
                                    },30000);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i("tag", "OnFailure");
                                }
                            });


                            startActivity(new Intent(getApplicationContext(), Profile.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Failed to create account", Toast.LENGTH_SHORT).show();
                            Log.i("tag", "OnFailure");
                        }
                    });
                }
            }
        });

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismissDialog();
                    }
                },30000);
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        }

        public boolean checkField(EditText textField)
        {
            if(textField.getText().toString().isEmpty()) {
                textField.setError("Empty!");
                valid = false;
            }
            else
            {
                valid = true;
            }
            return valid;

        }


    }
