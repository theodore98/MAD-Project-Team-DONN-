package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class EditPage extends AppCompatActivity {

    EditText  proemailid, profname, prolname, prophoneno;
    Button editSave;
    FirebaseAuth fAuth;
    DatabaseReference reference;
    FirebaseUser user;

    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);

            Intent data = getIntent();

        proemailid = findViewById(R.id.editemail);
        profname = findViewById(R.id.editfname);
        prolname = findViewById(R.id.editlname);
        prophoneno = findViewById(R.id.editphone);
        editSave = findViewById(R.id.editsave);

            fAuth = FirebaseAuth.getInstance();
            user = fAuth.getCurrentUser();
            reference = FirebaseDatabase.getInstance().getReference().child("admin");


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







    }
}