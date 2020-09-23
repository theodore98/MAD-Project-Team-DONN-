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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class EditPage extends AppCompatActivity {

    EditText  proemailid, profname, prolname, prophoneno;
    Button editSave;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;

    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);

            Intent data = getIntent();

            String fName = data.getStringExtra("fName");
            String lName = data.getStringExtra("lName");
            String email = data.getStringExtra("email");
            String phone = data.getStringExtra("phone");

            fAuth = FirebaseAuth.getInstance();
            fStore = FirebaseFirestore.getInstance();
            user = fAuth.getCurrentUser();

            proemailid = findViewById(R.id.editemail);
            profname = findViewById(R.id.editfname);
            prolname = findViewById(R.id.editlname);
            prophoneno = findViewById(R.id.editphone);
            editSave = findViewById(R.id.editsave);


            editSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (profname.getText().toString().isEmpty() || prolname.getText().toString().isEmpty() || proemailid.getText().toString().isEmpty() || prophoneno.getText().toString().isEmpty()) {
                        Toast.makeText(EditPage.this, "One or many fields are empty", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    final String email = proemailid.getText().toString();
                    user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            DocumentReference documentReference = fStore.collection("users").document(user.getUid());
                            Map<String,Object> edited = new HashMap<>();
                            edited.put("email", email);
                            edited.put("fName", profname.getText().toString());
                            edited.put("lName", prolname.getText().toString());
                            edited.put("phone", prophoneno.getText().toString());

                            documentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(EditPage.this,"profile updated", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), Profile.class));
                                    finish();
                                }
                            });

                           
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditPage.this, "Error occured with email", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            proemailid.setText(email);
            profname.setText(fName);
            prolname.setText(lName);
            prophoneno.setText(phone);

        Log.d(TAG, "OnCreate: " + fName +" "+ lName+" " +email + " " + phone);

    }
}