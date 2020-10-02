package com.example.food2u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterBusiness extends AppCompatActivity {
    ImageButton home;
    TextInputLayout name,address,mobile,email,password,owner,bank,acNumber;
    Button registerBtn;
    FirebaseAuth FAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    String NAME,OWNER,EMAIL,PASSWORD,BANK,ACNUMBER,MOBILE,ADDRESS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_business);

        name=(TextInputLayout) findViewById(R.id.Name);
        address=(TextInputLayout) findViewById(R.id.Address);
        mobile=(TextInputLayout) findViewById(R.id.Mobile);
        email=(TextInputLayout) findViewById(R.id.Email);
        password=(TextInputLayout) findViewById(R.id.LogPassword);
        owner=(TextInputLayout) findViewById(R.id.Owner);
        bank=(TextInputLayout) findViewById(R.id.Bank);
        acNumber=(TextInputLayout) findViewById(R.id.AcNumber);

        registerBtn=(Button)findViewById(R.id.regbtn);



        databaseReference = firebaseDatabase.getInstance().getReference("Business");
        FAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NAME=name.getEditText().getText().toString().trim();
                ADDRESS=address.getEditText().getText().toString().trim();
                MOBILE=mobile.getEditText().getText().toString().trim();
                OWNER=owner.getEditText().getText().toString().trim();
                BANK=bank.getEditText().getText().toString().trim();
                PASSWORD=password.getEditText().getText().toString().trim();
                EMAIL=email.getEditText().getText().toString().trim();
                ACNUMBER=acNumber.getEditText().getText().toString().trim();

                    if(isValid()){
                        final ProgressDialog mDialog = new ProgressDialog(RegisterBusiness.this);
                        mDialog.setCancelable(false);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setMessage("Registration in progress please wait......");
                        mDialog.show();

                        FAuth.createUserWithEmailAndPassword(EMAIL,PASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                HashMap<String , String> hashMap1 = new HashMap<>();
                                hashMap1.put("Mobile No",MOBILE);
                                hashMap1.put("Name",NAME);
                                hashMap1.put("Owner",OWNER);
                                hashMap1.put("Bank",BANK);
                                hashMap1.put("Account Number",ACNUMBER);
                                hashMap1.put("Email",EMAIL;
                                hashMap1.put("Password",PASSWORD);
                                hashMap1.put("Address",ADDRESS);

                                firebaseDatabase.getInstance().getReference("Business")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        mDialog.dismiss();
                                        FAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterBusiness.this);
                                                    builder.setMessage("You Have Registered! Make Sure To Verify Your Email");
                                                    builder.setCancelable(false);
                                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {

                                                            dialog.dismiss();

                                                        }
                                                    });
                                                    AlertDialog Alert = builder.create();
                                                    Alert.show();
                                                }else{
                                                    mDialog.dismiss();
                                                    ReusableCodeForAll.ShowAlert(ChefRegistration.this,"Error",task.getException().getMessage());
                                                }

                                            }
                                        })
                                    }
                                })


                                }
                            });
                        }
                    }


            }
        });






















        home=(ImageButton)findViewById(R.id.homeBtn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home= new Intent(RegisterBusiness.this,MainActivity.class);
                startActivity(home);
            }
        });
    }
}