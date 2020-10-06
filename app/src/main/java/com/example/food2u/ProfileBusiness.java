package com.example.food2u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
    TextView nameET,addressET,mobileET,ownerET,bankET,passwordET,acnumberET;
    TextView emailET;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Button update;


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
        update=(Button)findViewById(R.id.updatebtn);
        update.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                  Intent UpdateRedirect=new Intent(ProfileBusiness.this,UpdateBusiness.class);
                  startActivity(UpdateRedirect);

             }
        });


    }
/*
    private void updateInfo() {
        final FirebaseAuth FAuth;
        databaseReference = firebaseDatabase.getInstance().getReference("Business");
        FAuth = FirebaseAuth.getInstance();


        final String name=nameET.getText().toString().trim();
        final String mobile=mobileET.getText().toString().trim();
        final String address=addressET.getText().toString().trim();
        final String bank=bankET.getText().toString().trim();
        final String bankAC=acnumberET.getText().toString().trim();
        final String owner=ownerET.getText().toString().trim();
        final String password=passwordET.getText().toString().trim();
        final String email=emailET.getText().toString().trim();

        final ProgressDialog mDialog=new ProgressDialog(ProfileBusiness.this);
        progressDialog.setMessage("Updating profile...");
        progressDialog.show();
        FAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name",""+name);
                hashMap.put("address",""+address);
                hashMap.put("password",""+password);
                hashMap.put("owner",""+owner);
                hashMap.put("mobileNo",""+mobile);
                hashMap.put("bank",""+bank);
                hashMap.put("accountNumber",""+bankAC);

                FirebaseDatabase.getInstance().getReference("Business")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mDialog.dismiss();
                        FAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileBusiness.this);
                                    builder.setMessage("You Have Registered! Make Sure To Verify Your Email");
                                    builder.setCancelable(false);
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent regRedirect = new Intent(ProfileBusiness.this, HomeBusiness.class);
                                            regRedirect.putExtra("email", email);
                                            startActivity(regRedirect);

                                            dialog.dismiss();

                                        }
                                    });
                                    AlertDialog Alert = builder.create();
                                    Alert.show();
                                } else {
                                    mDialog.dismiss();
                                    ReusableCodeForAll.ShowAlert(ProfileBusiness.this, "Error", task.getException().getMessage());
                                }

                            }
                        });


                    }
                });
            }
        });
    }

*/

}