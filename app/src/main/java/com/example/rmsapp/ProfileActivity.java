package com.example.rmsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    //firebase authentication
    FirebaseAuth firebaseAuth;
    TextView loggedEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Action Bar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");

        //init
        firebaseAuth =FirebaseAuth.getInstance();
        loggedEmail = findViewById(R.id.userlogEmail);

    }
    private void checkUserStatus(){
        //Get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            //User is signed in and no changes
            //Set email of logged in user
            loggedEmail.setText(user.getEmail());
        }
        else{
            //User is not signed in, go to main activity
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        //Check when app is started
        checkUserStatus();
        super.onStart();
    }
}

