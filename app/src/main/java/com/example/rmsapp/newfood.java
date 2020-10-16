package com.example.rmsapp;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;


public class newfood extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String [] foods={"Pick a category ","Sea food","Chinese food","Local","Pizza"};
    private static final int IMAGE_REQUEST =2;
    long i =0;
    private Uri Imageuri;
    EditText name , price, description;
    Button   confirm;
    ImageButton image;
    ImageView imageView;
    TextView category;
    Spinner spinner;
    DatabaseReference dbRef;
    Food fd;
    String key;

    public newfood() {
    }

    private void clearControls(){
        name.setText("");
        price.setText("");
        description.setText("");
        imageView.setImageResource(0);
        spinner.setSelection(0);

    }

        @Override
        protected void onCreate (Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfood);

        name = findViewById(R.id.Name);
        price = findViewById(R.id.Price);
        description = findViewById(R.id.descript);
        spinner = (Spinner) findViewById(R.id.spinner1);
        //category =findViewById(R.id.category);
        imageView = findViewById(R.id.foodIM);
        confirm = findViewById(R.id.confirm);
        image = findViewById(R.id.addimage);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,foods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        String  s = spinner.getSelectedItem().toString();
        fd = new Food();
        dbRef = FirebaseDatabase.getInstance().getReference().child("Foods");
       /* dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    i =(snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Foods");

                try {
                    if (TextUtils.isEmpty(name.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter an Name", Toast.LENGTH_SHORT).show();


                    else if (TextUtils.isEmpty(description.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Description", Toast.LENGTH_SHORT).show();

                    else if (spinner.getSelectedItem().toString().trim().equals("Pick a category")){
                        Toast.makeText(getApplicationContext(),"Please select a Category",Toast.LENGTH_SHORT).show();

                    }
                    else if (imageView.getDrawable()== null){
                        Toast.makeText(getApplicationContext(),"Please select an image",Toast.LENGTH_SHORT).show();
                    }



                    else {


                            fd.setName(name.getText().toString().trim());
                            fd.setFd_price(Double.parseDouble(price.getText().toString().trim()));
                            fd.setDescription(description.getText().toString().trim());
                            fd.setCategory(spinner.getSelectedItem().toString().trim());

                              key = dbRef.push().getKey();
                              fd.setF_id(key);
                              dbRef.child(key).setValue(fd);




                            Toast.makeText(getApplicationContext(), "data saved successfully", Toast.LENGTH_SHORT).show();
                            clearControls();
                            Intent in = new Intent(newfood.this,MainActivity.class);
                            startActivity(in);


                    }
                } catch (NumberFormatException e) {

                    Toast.makeText(getApplicationContext(), "Invalid price", Toast.LENGTH_SHORT).show();


                }
            }
        });





        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* uploadimage();*/
                openimage();
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

                imageView.setImageBitmap(bitmap);
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

                            Toast.makeText(newfood.this, "Image Upload Successful", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }


}