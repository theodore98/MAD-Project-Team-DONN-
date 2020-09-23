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
import com.google.firebase.auth.FirebaseUser;
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
    FirebaseFirestore fStore;
    String userID;
    Boolean valid = true;



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




        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


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
                            FirebaseUser user  = fAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            DocumentReference df = fStore.collection("users").document(user.getUid());

                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("fName", fname);
                            userInfo.put("lName", lname);
                            userInfo.put("email", email);
                            userInfo.put("phone", phone);

                            userInfo.put("isAdmin", "1");
                            df.set(userInfo);

                            startActivity(new Intent(getApplicationContext(), Profile.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Failed to create account", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
