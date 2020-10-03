package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project.newfood;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Edit extends AppCompatActivity {

     Button edit , save;
     EditText pricefd , descript;
     DatabaseReference upRef;
     TextView name1;
     //DatabaseReference dbRef;
     ImageView image;
     Food fd;
    private Uri Imageuri;
    private static final int IMAGE_REQUEST =2;

    Intent in = getIntent();








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        image = findViewById(R.id.image_view);
        edit = findViewById(R.id.edit);
        save = findViewById(R.id.save);
        descript= findViewById(R.id.descript);
        pricefd = findViewById(R.id.price_fd);
        name1 = findViewById(R.id.textView3);
        fd = new Food();

        String fddescription = getIntent().getStringExtra("des");
        String imageur = getIntent().getStringExtra("imagepath");
        String price = String.valueOf(getIntent().getDoubleExtra("price", 0));
        String key = getIntent().getStringExtra("key");
        String fnname = getIntent().getStringExtra("fdname");

        Picasso.get().load(imageur).into(image);

        descript.setText(fddescription);
        pricefd.setText(price);
        name1.setText("EDIT "+fnname);






        upRef = FirebaseDatabase.getInstance().getReference("Foods");



         save.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {



                upRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.hasChild(key)) {
                            try {

                                 /*snapshot.getKey();*/
                                /*fd.setFd_price(Double.parseDouble(pricefd.getText().toString().trim()));
                                fd.setDescription(descript.getText().toString().trim());
                                dbRef = FirebaseDatabase.getInstance().getReference().child("Foods");
                                dbRef.setValue(fd);*/

                                fd.setDescription(descript.getText().toString().trim());
                                fd.setFd_price(Double.parseDouble(pricefd.getText().toString().trim()));
                                String  des = fd.getDescription();
                                Double pri = fd.getFd_price();

                                upRef.child(key).child("description").setValue(des);
                                upRef.child(key).child("fd_price").setValue(pri);
                                upRef.child(key).child("Imageur").setValue(fd.getImageur());

                                //clearControls();
                                Intent i = new Intent(Edit.this, MainActivity.class);
                                startActivity(i);

                                Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();

                            } catch (NumberFormatException e) {
                                Toast.makeText(getApplicationContext(), "Invalid Price", Toast.LENGTH_SHORT).show();
                            }


                        }
                        else {
                            Toast.makeText(getApplicationContext(),"No source to Update",Toast.LENGTH_SHORT).show();
                        }
                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
             }
         });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* uploadimage();*/
                openimage();
                fd.setImageur(" ");
            }
        });

    }







    private void openimage () {
        Intent inten = new Intent();
        inten.setType("image/*");
        inten.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(inten, IMAGE_REQUEST);


    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK ) {
            assert data != null;
            Imageuri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),Imageuri);

                image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*confirm.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {*/

            uploadimage();
           /*    }
           });*/
        }
    }

    private String getfileExtension (Uri uri )
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadimage () {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        if (Imageuri != null) {
            final StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("image").child(System.currentTimeMillis() + "." + getfileExtension(Imageuri));
            fileRef.putFile(Imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String url = uri.toString();
                            fd = new Food();
                            fd.setImageur(url);







                            Log.d("DownloadUrl", url);

                            pd.dismiss();

                            Toast.makeText(Edit.this, "Image Upload Successful", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

    }



    @Override
    protected void onStart() {
        super.onStart();





        // price.setText(String.valueOf(pri));


    }


}