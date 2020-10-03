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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProfileBusiness extends AppCompatActivity {
    ImageButton home;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog;
    EditText nameET,addressET,mobileET,ownerET,bankET,passwordET,acnumberET;
    TextView emailET;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    BUtto


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_business);

        nameET=findViewById(R.id.name);
        addressET=findViewById(R.id.address);
        mobileET=findViewById(R.id.mobile);
        ownerET=findViewById(R.id.owner);
        bankET=findViewById(R.id.bank);
        passwordET=findViewById(R.id.password);
        acnumberET=findViewById(R.id.acNumber);
        emailET=findViewById(R.id.email);

        home=(ImageButton)findViewById(R.id.homeBtn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home= new Intent(ProfileBusiness.this,MainActivity.class);
                startActivity(home);
            }
        });




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

                updateInfo();
    }

    private void updateInfo() {
        String name,mobile,address,bank,bankAC,owner,password;
        name=nameET.getText().toString().trim();
        mobile=mobileET.getText().toString().trim();
        address=addressET.getText().toString().trim();
        bank=bankET.getText().toString().trim();
        bankAC=acnumberET.getText().toString().trim();
        owner=ownerET.getText().toString().trim();
        password=passwordET.getText().toString().trim();

        progressDialog.setMessage("Updating profile...");
        progressDialog.show();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name",""+name);
        hashMap.put("address",""+address);
        hashMap.put("password",""+password);
        hashMap.put("owner",""+owner);
        hashMap.put("mobileNo",""+mobile);
        hashMap.put("bank",""+bank);
        hashMap.put("accountNumber",""+bankAC);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Business");
        ref.child(firebaseAuth.getUid()).updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileBusiness.this, "Profile Updated...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileBusiness.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });






    }
}