package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class Profile extends AppCompatActivity {
    String userId, proemailid, profname, prolname, prophoneno;
    FirebaseAuth fAuth;
    FirebaseUser admin;
    DatabaseReference reference;
    FirebaseDatabase database;
    Button editprof, logout, editDelete;
    TextView fnameView, lnameView, emailidView, phonenoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        admin = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("admin");
        userId = admin.getUid();

        fnameView = findViewById(R.id.fname);
        lnameView = findViewById(R.id.lname);
        emailidView = findViewById(R.id.emailid);
        phonenoView = findViewById(R.id.phoneno);
        editprof  = findViewById(R.id.editprof);
        logout = findViewById(R.id.prologout);
        editDelete = findViewById(R.id.editdelete);

       reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               Admin adminProfile = snapshot.getValue(Admin.class);

               if(adminProfile != null)
               {
                   profname = adminProfile.fname;
                   prolname = adminProfile.lname;
                   proemailid = adminProfile.email;
                   prophoneno = adminProfile.phoneno;

                   emailidView.setText(proemailid);
                   fnameView.setText(profname);
                   lnameView.setText(prolname);
                   phonenoView.setText(prophoneno);

                   Log.d("TAG", "OnCreate: " + profname +" "+ prolname+" " +proemailid + " " + prophoneno);


               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

               Toast.makeText(Profile.this, "Not working", Toast.LENGTH_SHORT).show();
           }
       });

/*
        editDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder.setTitle("Delete Account")
                .setMessage("Are you sure you want to delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        documentReference.delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Profile.this, "Deleted", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        finish();
                                    }
                                });
                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                builder.create();
                        builder.show();


            }
        });
*/


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }

        });


    }



}





