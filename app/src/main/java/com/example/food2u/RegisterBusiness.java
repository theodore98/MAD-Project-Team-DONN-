package com.example.food2u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

        registerBtn=(Button)findViewById(R.id.regSubmit);



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
                                hashMap1.put("Email",EMAIL);
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
                                                    ReusableCodeForAll.ShowAlert(RegisterBusiness.this,"Error",task.getException().getMessage());
                                                }

                                            }
                                        });
                                    }
                                });


                            }
                        });
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


    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public boolean isValid(){
        email.setErrorEnabled(false);
        email.setError("");

        name.setErrorEnabled(false);
        name.setError("");

        mobile.setErrorEnabled(false);
        mobile.setError("");

        password.setErrorEnabled(false);
        password.setError("");

        owner.setErrorEnabled(false);
        owner.setError("");

        bank.setErrorEnabled(false);
        bank.setError("");

        acNumber.setErrorEnabled(false);
        acNumber.setError("");

        address.setErrorEnabled(false);
        address.setError("");

        boolean isValid = false, isValidemail = false, isValidname = false, isValidaddress = false, isValidmobile = false, isValidpassword = false, isValidcbank = false, isValidacNumber = false, isValidowner = false;
        if (TextUtils.isEmpty(NAME)) {
            name.setErrorEnabled(true);
            name.setError("Enter Name");
        } else {
            isValidname = true;
        }
        if (TextUtils.isEmpty(ADDRESS)) {
            address.setErrorEnabled(true);
            address.setError("Enter Name");
        } else {
            isValidaddress = true;
        }
        if (TextUtils.isEmpty(MOBILE)) {
            mobile.setErrorEnabled(true);
            mobile.setError("Enter MObile");
        } else {
            isValidmobile = true;
        }
        if (TextUtils.isEmpty(PASSWORD)) {
            password.setErrorEnabled(true);
            password.setError("Enter Password");
        } else {
            isValidpassword = true;
        }
        if (TextUtils.isEmpty(BANK)) {
            bank.setErrorEnabled(true);
            bank.setError("Enter Bank");
        } else {
            isValidcbank = true;
        }
        if (TextUtils.isEmpty(ACNUMBER)) {
            acNumber.setErrorEnabled(true);
            acNumber.setError("Enter Account Number");
        } else {
            isValidacNumber = true;
        }
        if (TextUtils.isEmpty(OWNER)) {
            owner.setErrorEnabled(true);
            owner.setError("Enter Owner");
        } else {
            isValidowner = true;
        }
        if(TextUtils.isEmpty(EMAIL)){
            email.setErrorEnabled(true);
            email.setError("Email Is Required");
        }else {
            if (EMAIL.matches(emailpattern)) {
                isValidemail = true;
            } else {
                email.setErrorEnabled(true);
                email.setError("Enter a Valid Email Id");
            }
        }


        isValid = (isValidname && isValidpassword && isValidemail && isValidaddress && isValidmobile && isValidowner && isValidcbank && isValidacNumber) ? true : false;
        return isValid;
    }









}