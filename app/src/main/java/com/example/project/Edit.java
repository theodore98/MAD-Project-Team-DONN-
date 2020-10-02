package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project.newfood;

import android.content.Intent;
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

public class Edit extends AppCompatActivity {

     Button edit , save;
     EditText price , descript;
     DatabaseReference upRef;
     DatabaseReference dbRef;
     Food fd;

    Intent in = getIntent();


    private void clearControls(){

        price.setText("");
        descript.setText("");
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edit = findViewById(R.id.edit);
        save = findViewById(R.id.save);
        descript= findViewById(R.id.descript);
        //price = findViewById(R.id.pr);
        fd = new Food();










         save.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 upRef = FirebaseDatabase.getInstance().getReference().child("Foods");

                upRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                            try {
                                //fd.setFd_price(Double.parseDouble(price.getText().toString().trim()));
                                fd.setDescription(descript.getText().toString().trim());

                                dbRef =FirebaseDatabase.getInstance().getReference().child("Foods");
                                dbRef.setValue(fd);
                                //clearControls();
                                Intent i = new Intent(Edit.this,MainActivity.class);
                                 startActivity(i);

                                Toast.makeText(getApplicationContext(),"Data Updated Successfully",Toast.LENGTH_SHORT).show();

                            }

                            catch (NumberFormatException e)
                            {
                                Toast.makeText(getApplicationContext(),"Invalid Price",Toast.LENGTH_SHORT).show();
                            }
                        }

                       /* else{
                            Toast.makeText(getApplicationContext(),"No source to Update",Toast.LENGTH_SHORT).show();
                        }*/


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
             }
         });


    }


    @Override
    protected void onResume() {
        super.onResume();

        //Bundle b = this.getIntent().getExtras();
        String name= getIntent().getStringExtra(MainActivity.DESCRIPTION);
        //double pri = getIntent().getDoubleExtra("price", 0);

        descript.setText(name);
       // price.setText(String.valueOf(pri));


    }


}