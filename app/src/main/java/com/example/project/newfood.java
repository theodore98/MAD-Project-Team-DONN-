package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class newfood extends AppCompatActivity {

    private static final int IMAGE_REQUEST =2;
    int i =0;
    private Uri Imageuri;
    EditText name , price, description;
    Button  category, confirm,image;
    DatabaseReference dbRef;
    Food fd;

    public newfood() {
    }

    private  void clearControls(){
        name.setText("");
        price.setText("");
        description.setText("");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfood);

        name = findViewById(R.id.Name);
        price= findViewById(R.id.Price);
        description =findViewById(R.id.description);

        category =findViewById(R.id.category);
        confirm =findViewById(R.id.confirm);
        image =findViewById(R.id.addimage);

       fd = new Food();

     confirm.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dbRef = FirebaseDatabase.getInstance().getReference().child("Foods");

               try{
                   if(TextUtils.isEmpty(name.getText().toString()))
                       Toast.makeText(getApplicationContext(), "Please Enter an Name",Toast.LENGTH_SHORT).show();

                   else if(TextUtils.isEmpty(price.getText().toString()))
                       Toast.makeText(getApplicationContext(),"please Enter a Price",Toast.LENGTH_SHORT).show();

                   else if(TextUtils.isEmpty(description.getText().toString()))
                       Toast.makeText(getApplicationContext(),"Please Enter Description",Toast.LENGTH_SHORT).show();

                   else{

                       while(i!=-1){
                        fd.setName(name.getText().toString().trim());
                        fd.setFd_price(Double.parseDouble(price.getText().toString().trim()));
                        fd.setDescription(description.getText().toString().trim());
                        fd.setFood_id("FD00"+(++i));
                       // dbRef.push().setValue(fd);
                        dbRef.child("food"+(++i)).push().setValue(fd);

                         ++i;
                        Toast.makeText(getApplicationContext(),"data saved successfully",Toast.LENGTH_SHORT).show();
                       clearControls();}
                   }
               }

               catch(NumberFormatException e) {

                   Toast.makeText(getApplicationContext(),"Invalid price",Toast.LENGTH_SHORT).show();


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

    private void openimage() {
        Intent inten = new Intent();
        inten.setType("food_images/");
        inten.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(inten,IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK)
        {
            Imageuri = data.getData();
            uploadimage();
        }
    }

    private  String getfileExtension(Uri uri )
    {
        ContentResolver  contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadimage() {

        final ProgressDialog   pd = new ProgressDialog( this);
        pd.setMessage("Uploading");
        pd.show();

        if(Imageuri != null)
        {
            final StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("images").child(System.currentTimeMillis()+"."+getfileExtension(Imageuri));
            fileRef.putFile(Imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            Log.d( "DownloadUrl",url);
                            pd.dismiss();
                            Toast.makeText(newfood.this,"Image Upload Successful",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

    }


}