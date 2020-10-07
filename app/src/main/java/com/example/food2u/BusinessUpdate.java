package com.example.food2u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class BusinessUpdate extends AppCompatActivity {
    Button updateNew;

    EditText nameNew,addressNew,mobileNew,ownerNew,passwordNew,bankNew,acnumberNew;
    TextView emailNew;

    FirebaseAuth firebaseAuUpdate;
    FirebaseUser userUpdate;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Business business;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_update);

        updateNew=(Button)findViewById(R.id.updateNew);

        nameNew=findViewById(R.id.nameUpdate);
        addressNew=findViewById(R.id.addressUpdate);
        mobileNew=findViewById(R.id.mobileUpdate);
        ownerNew=findViewById(R.id.ownerUpdate);
        passwordNew=findViewById(R.id.passwordUpdate);
        bankNew=findViewById(R.id.bankUpdate);
        acnumberNew=findViewById(R.id.acnumberUpdate);

        emailNew=findViewById(R.id.emailView);

        firebaseAuUpdate=FirebaseAuth.getInstance();
        userUpdate=firebaseAuUpdate.getCurrentUser();

        if(userUpdate == null){
            startActivity(new Intent(getApplicationContext(), LoginBusiness.class));
            finish();
        }

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Business");

        Query queryRetrieve=databaseReference.orderByChild("email").equalTo(userUpdate.getEmail());
        queryRetrieve.addValueEventListener(new ValueEventListener() {
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

                    nameNew.setText(name);
                    addressNew.setText(address);
                    mobileNew.setText(mobile);
                    emailNew.setText(email);
                    ownerNew.setText(owner);
                    passwordNew.setText(password);
                    bankNew.setText(bank);
                    acnumberNew.setText(accountNumber);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInformation();

            }
        });

    }

    public void updateInformation(){
        final String nameUp=nameNew.getText().toString().trim();
        final String mobileUp=mobileNew.getText().toString().trim();
        final String addressUp=addressNew.getText().toString().trim();
        final String ownerUp=ownerNew.getText().toString().trim();
        final String passwordUp=passwordNew.getText().toString().trim();
        final String bankUp=bankNew.getText().toString().trim();
        final String acNumUp=acnumberNew.getText().toString().trim();
        final String emailUp=emailNew.getText().toString();

        business=new Business();
        databaseReference=FirebaseDatabase.getInstance().getReference("Business");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(emailUp)){
                    business.setName(nameUp);
                    String nameSend=business.getName();

                    business.setAddress(addressUp);
                    String addressSend=business.getAddress();

                    business.setMobileNo(mobileUp);
                    String mobileSend=business.getMobileNo();

                    business.setOwner(ownerUp);
                    String ownerSend=business.getOwner();

                    business.setPassword(passwordUp);
                    String passwordSend=business.getPassword();

                    business.setBank(bankUp);
                    String bankSend=business.getBank();

                    business.setAccountNo(acNumUp);
                    String acNumSend=business.getAccountNo();

                    databaseReference.child(emailUp).child("name").setValue(nameSend);
                    databaseReference.child(emailUp).child("address").setValue(addressSend);
                    databaseReference.child(emailUp).child("bank").setValue(bankSend);
                    databaseReference.child(emailUp).child("mobileNo").setValue(mobileSend);
                    databaseReference.child(emailUp).child("accountNumber").setValue(acNumSend);
                    databaseReference.child(emailUp).child("owner").setValue(ownerSend);
                    databaseReference.child(emailUp).child("password").setValue(passwordSend);

                    Intent UpdateRedirect=new Intent(BusinessUpdate.this,ProfileBusiness.class);
                    startActivity(UpdateRedirect);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Intent UpdateRedirect=new Intent(BusinessUpdate.this,RegisterBusiness.class);
                startActivity(UpdateRedirect);

            }
        });




    }

}