package com.example.food2u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginBusiness extends AppCompatActivity {
    TextInputLayout email,password;
    Button Signin;
    ImageButton Home;
    FirebaseAuth Fauth;
    String EMAIL,PASSWORD;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_business);

        email=(TextInputLayout)findViewById(R.id.LogEmail);
        password=(TextInputLayout)findViewById(R.id.LogPassword);
        Signin=(Button)findViewById(R.id.LoginBtn);
        Home=(ImageButton)findViewById(R.id.homeBtn);
    //Home Button
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home=new Intent(LoginBusiness.this,MainActivity.class);
                   startActivity(home);
            }
        });
    //Home Button End

    //Login Verifcation
        Fauth=FirebaseAuth.getInstance();

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EMAIL = email.getEditText().getText().toString().trim();
                PASSWORD = password.getEditText().getText().toString().trim();

                if (isValid()) {
                    final ProgressDialog mDialog = new ProgressDialog(LoginBusiness.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Signing in please wait......");
                    mDialog.show();

                    Fauth.signInWithEmailAndPassword(EMAIL, PASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mDialog.dismiss();
                                if (Fauth.getCurrentUser().isEmailVerified()) {
                                    mDialog.dismiss();
                                    Toast.makeText(LoginBusiness.this, "Congratulations You have Successfully Logged In", Toast.LENGTH_SHORT).show();
                                    Intent BusinessHomeDirect = new Intent(LoginBusiness.this, HomeBusiness.class);
                                    BusinessHomeDirect.putExtra("email",EMAIL);
                                    startActivity(BusinessHomeDirect);
                                    finish();
                                } else {
                                    ReusableCodeForAll.ShowAlert(LoginBusiness.this, "Verfication Failed", "You have Not been Verified");
                                    ;
                                }

                            } else {
                                mDialog.dismiss();
                                ReusableCodeForAll.ShowAlert(LoginBusiness.this, "Error", task.getException().getMessage());
                            }
                        }
                    });
                }
            }
        });

 






    }


    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid(){
        email.setErrorEnabled(false);
        email.setError("");

        password.setErrorEnabled(false);
        password.setError("");

        boolean isValid=false,isValidEmail=false,isValidPassword=false;
        if(TextUtils.isEmpty(EMAIL)) {
            email.setErrorEnabled(false);
            email.setError("Email is required");
        }
        else{
            if(EMAIL.matches(emailpattern)){
                isValidEmail=true;

            }else{
                email.setErrorEnabled(true);
                email.setError("Invalid Email Address");
            }
        }
        if(TextUtils.isEmpty(PASSWORD)) {
            password.setErrorEnabled(true);
            password.setError("Please Enter Password");
        }else {
            isValidPassword = true;
        }
        isValid=( isValidEmail && isValidPassword )?true:false;
        return isValid;
    }
}








