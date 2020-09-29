package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Delete extends AppCompatActivity {

    Button delete ;
    CheckBox chk;
    DatabaseReference dlRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        delete = findViewById(R.id.delete);
        chk = findViewById(R.id.checkBox);

        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chk.isChecked())
                {
                    String  check = chk.getText().toString();
                    dlRef = FirebaseDatabase.getInstance().getReference().child("Foods");
                    dlRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });


    }
}