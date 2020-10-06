package com.example.food2u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UpdateBusiness extends AppCompatActivity {

    ImageButton home;
    /*FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog;
    EditText nameET1,addressET,mobileET,ownerET,bankET,passwordET,acnumberET;
    EditText emailET;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Button update,delete;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_business);

        //nameET1=findViewById(R.id.name1);
       // addressET=(EditText)findViewById(R.id.address);
        //mobileET=(EditText)findViewById(R.id.mobile);
        //ownerET=(EditText)findViewById(R.id.owner);
        //bankET=(EditText)findViewById(R.id.bank);
        //passwordET=(EditText)findViewById(R.id.password);
        //acnumberET=(EditText)findViewById(R.id.acNumber);
        //emailET=(EditText)findViewById(R.id.email);

        home=(ImageButton)findViewById(R.id.homeBtn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home= new Intent(UpdateBusiness.this,MainActivity.class);
                startActivity(home);
            }
        });
/*
        firebaseAuth = FirebaseAuth.getInstance();
        //firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseUser user = firebaseAuth.getCurrentUser();
       if(user == null){
            startActivity(new Intent(getApplicationContext(), LoginBusiness.class));
            finish();
        }
        firebaseDatabase =  FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Business");

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = "" + ds.child("name").getValue();
                    String mobile = "" + ds.child("mobileNo").getValue();
                    String address = "" + ds.child("address").getValue();
                    String email = "" + ds.child("email").getValue();
                    String bank = "" + ds.child("bank").getValue();
                    String accountNumber = "" + ds.child("accountNumber").getValue();
                    String password = "" + ds.child("password").getValue();
                    String owner = "" + ds.child("owner").getValue();


                    nameET.setText(name);
                    mobileET.setText(mobile);
                    addressET.setText(address);
                    emailET.setText(email);
                    acnumberET.setText(accountNumber);
                    bankET.setText(bank);
                    passwordET.setText(password);
                    ownerET.setText(owner);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*update=(Button)findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent UpdateRedirect=new Intent(UpdateBusiness.this,ProfileBusiness.class);
                startActivity(UpdateRedirect);

            }
        });

        }*/
    }
    
}