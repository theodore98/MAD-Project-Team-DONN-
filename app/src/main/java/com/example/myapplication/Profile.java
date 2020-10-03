package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

import static com.example.myapplication.Admin.*;

public class Profile extends AppCompatActivity {
    String userId, proemailid, profname, prolname, prophoneno;
    FirebaseAuth fAuth;
    FirebaseUser admin;
    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseDatabase database;
    Button editprof, logout, editDelete, editViewUsers, editViewCom;
    TextView fnameView, lnameView, emailidView, phonenoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fnameView = findViewById(R.id.fname);
        lnameView = findViewById(R.id.lname);
        emailidView = findViewById(R.id.emailid);
        phonenoView = findViewById(R.id.phoneno);
        editprof  = findViewById(R.id.editprof);
        logout = findViewById(R.id.prologout);
        editDelete = findViewById(R.id.editdelete);
        editViewUsers = findViewById(R.id.editViewU);
        editViewCom = findViewById(R.id.editViewCom);


        final Loading loading = new Loading(Profile.this);
        fAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        admin = fAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("admin");

        Query query = reference.orderByChild("email").equalTo(admin.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds: snapshot.getChildren())
                {
                    String email = "" + ds.child("email").getValue();
                    String fname = "" + ds.child("fName").getValue();
                    String lname = "" + ds.child("lName").getValue();
                    String phoneno = "" + ds.child("phone").getValue();

                    fnameView.setText(fname);
                    lnameView.setText(lname);
                    emailidView.setText(email);
                    phonenoView.setText(phoneno);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i =new Intent(view.getContext(),EditPage.class);

                i.putExtra("fName", profname);
                i.putExtra("lName", prolname);
                i.putExtra("email", proemailid);
                i.putExtra("phone", prophoneno);

               view.getContext().startActivity(i);

            }
        });

        editViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loading.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                },30000);
                startActivity(new Intent((getApplicationContext()), viewUsers.class));
                finish();
            }
        });

        editViewCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loading.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                },30000);
                startActivity(new Intent((getApplicationContext()), viewCompany.class));
                finish();
            }
        });

/*
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Admin adminProfile = snapshot.getValue(Admin.class);

                emailidView.setText(adminProfile.getEmail());
                fnameView.setText(adminProfile.getFname());
                lnameView.setText(adminProfile.getLname());
                phonenoView.setText(adminProfile.getPhoneno());

                Log.d("TAG", "onDataChange: ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Profile.this, "Not working", Toast.LENGTH_SHORT).show();
            }
        });


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


                   Log.d("TAG", "OnCreate: " + profname +" "+ prolname+" " +proemailid + " " + prophoneno);
                   Toast.makeText(Profile.this, "Retereved", Toast.LENGTH_SHORT).show();

               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {


               Log.d("TAG", "coudlmt find");
           }
       });

*/
        editDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    AlertDialog.Builder builder =new AlertDialog.Builder(Profile.this);
                    builder.setTitle("Delete Account")
                            .setMessage("Are you sure you want to delete?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Query query  = reference.orderByChild("email").equalTo(admin.getEmail());
                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(DataSnapshot ds: snapshot.getChildren())
                                            {
                                                ds.getRef().removeValue();
                                                Toast.makeText(Profile.this,"Account Removed", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(Profile.this,"Account NOT Removed", Toast.LENGTH_LONG).show();
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



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                loading.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                },30000);
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }

        });


    }



}





