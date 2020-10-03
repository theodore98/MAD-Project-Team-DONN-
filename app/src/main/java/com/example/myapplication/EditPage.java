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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.Map;


public class EditPage extends AppCompatActivity {

    EditText proemailid, profname, prolname, prophoneno;
    Button editSave, deleteBtn;
    FirebaseAuth fAuth;
    DatabaseReference reference;
    DatabaseReference readReference;
    DatabaseReference dbRef;
    FirebaseUser admin;
    FirebaseDatabase db;
    String userId;

    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);

        Intent in = getIntent();


        proemailid = findViewById(R.id.editemail);
        profname = findViewById(R.id.editfname);
        prolname = findViewById(R.id.editlname);
        prophoneno = findViewById(R.id.editphone);
        editSave = findViewById(R.id.editsave);
        deleteBtn = findViewById(R.id.editdelete);
        Admin ad = new Admin();

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        admin = fAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("admin");

        String fname = getIntent().getStringExtra("fName");
        String lname = getIntent().getStringExtra("lName");
        String email = getIntent().getStringExtra("email");
        String phoneno = getIntent().getStringExtra("phone");
        final String key = getIntent().getStringExtra("key");

        profname.setText(fname);
        prolname.setText(lname);
        proemailid.setText(email);
        prophoneno.setText(phoneno);




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

                    profname.setText(fname);
                    prolname.setText(lname);
                    proemailid.setText(email);
                    prophoneno.setText(phoneno);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


           editSave.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   updateProfile(profname.getText().toString(),
                                    prolname.getText().toString(),
                                        proemailid.getText().toString(),
                                        prophoneno.getText().toString());
                   Toast.makeText(EditPage.this, "done", Toast.LENGTH_SHORT).show();
                   startActivity(new Intent((getApplicationContext()), Profile.class));
                   finish();
               }

               private void updateProfile(final String proemailid, final String profname, final String prolname, final String prophoneno) {


                   FirebaseDatabase db = FirebaseDatabase.getInstance();
                   reference = db.getInstance().getReference("admin");
                   String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                   Query query = reference.orderByChild("email").equalTo(admin.getEmail());
                   query.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {


                               HashMap<String, Object> adminMap = new HashMap<>();

                               adminMap.put("fName", profname );
                               adminMap.put("lName", prolname );
                               adminMap.put("email", proemailid );
                               adminMap.put("phone", prophoneno );

                               reference.updateChildren(adminMap);

                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });




               }
           });


/*
            editSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (profname.getText().toString().isEmpty() || prolname.getText().toString().isEmpty() || proemailid.getText().toString().isEmpty() || prophoneno.getText().toString().isEmpty()) {
                        Toast.makeText(EditPage.this, "One or many fields are empty", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists())
                            {
                                String fname = snapshot.child("fName").getValue().toString();
                                String lname = snapshot.child("lName").getValue().toString();
                                String email = snapshot.child("email").getValue().toString();
                                String phoneno = snapshot.child("fName").getValue().toString();

                                proemailid.setText(email);
                                profname.setText(fname);
                                prolname.setText(lname);
                                prophoneno.setText(phoneno);

                                Log.d(TAG, "OnCreate: " + fname +" "+ lname+" " +email + " " + phoneno);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(EditPage.this,"" +error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
*/


            }


}