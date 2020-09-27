package com.example.rmsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerProfile extends AppCompatActivity {
    EditText txtEmail, txtUsername, txtPassword, txtContact;
    Button btnUpdate, btnDelete;
    DatabaseReference dbRef;
    Customer cus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        txtEmail = findViewById(R.id.customerEmail);
        txtUsername = findViewById(R.id.customerUsername);
        txtPassword = findViewById(R.id.customerPassword);
        txtContact = findViewById(R.id.customerContact);
        btnUpdate = findViewById(R.id.buttonConfirmchange);
        btnDelete = findViewById(R.id.buttonDeleteProfile);

        //Retrieve Data from Database
        dbRef= FirebaseDatabase.getInstance().getReference().child("Customer").child("Cus3");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    txtEmail.setText(snapshot.child("email").getValue().toString());
                    txtUsername.setText(snapshot.child("username").getValue().toString());
                    txtPassword.setText(snapshot.child("password").getValue().toString());
                    txtContact.setText(snapshot.child("contact").getValue().toString());
                }
                else
                    Toast.makeText(getApplicationContext(),"No Source to display", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Update records in database
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild("Cus3")){
                            try {
                                cus.setEmail(txtEmail.getText().toString().trim());
                                cus.setUsername(txtUsername.getText().toString().trim());
                                cus.setPassword(txtPassword.getText().toString().trim());
                                cus.setContact(txtContact.getText().toString().trim());

                                dbRef = FirebaseDatabase.getInstance().getReference().child("Customer").child("Cus3");
                                dbRef.setValue(cus);

                                //clear controls method to be invoked here..

                                //Feedback to the user via a Toast...
                                Toast.makeText(getApplicationContext(), "User Data Updated Successfully", Toast.LENGTH_SHORT).show();
                            }
                            catch(NumberFormatException e){
                                Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(),"No data source to update", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        //Delete Profile from Database
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild("Cus3")){
                            dbRef = FirebaseDatabase.getInstance().getReference().child("Customer").child("Cus3");
                            dbRef.removeValue();
                            Toast.makeText(getApplicationContext(),"Your Profile has been Deleted",Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"Failed to Delete",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }
}