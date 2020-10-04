package com.example.rmsapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseAppLifecycleListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;


public class CustomerProfileFragment extends Fragment {
    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    //Storage
    StorageReference storageReference;
    //path where images are stored
    String storagePath = "Users_Profile_Imgs/";

    //variables
    ImageView avatarIv;
    TextView userName, emailCustomer, contactCustomer;
    FloatingActionButton fab;
    Button delete;
    //Permissions constants
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_GALLERY_CODE = 300;
    private static final int IMAGE_PICK_CAMERA_CODE = 400;
    //Arrays of permissions requested
    String cameraPermissions[];
    String storagePermissions[];

    //uri of picked image
    Uri image_uri;

    //for checking profile
    String profilePhoto;

    public CustomerProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_profile, container, false);

        //Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Customers");
        storageReference = FirebaseStorage.getInstance().getReference(); //firebase storage reference

        //Initialize Arrays of permissions
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //Initialize variables
        avatarIv = view.findViewById(R.id.avatarIv);
        userName = view.findViewById(R.id.usernameTxtview);
        emailCustomer = view.findViewById(R.id.emailTxtview);
        contactCustomer = view.findViewById(R.id.contactTxtview);
        fab = view.findViewById(R.id.fab);
        delete = view.findViewById(R.id.btnDeleteAcc);

        //Initialize progress dialog
        progressDialog = new ProgressDialog(getActivity());


        //Retrieve signed in user's details through email
        //Using orderbychild query, retrieve from node where email key = signed in email
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //check until
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //get data
                    String username = "" + ds.child("username").getValue();
                    String email = "" + ds.child("email").getValue();
                    String contact = "" + ds.child("contact").getValue();
                    String image = "" + ds.child("image").getValue();

                    //Set data
                    userName.setText(username);
                    emailCustomer.setText(email);
                    contactCustomer.setText(contact);
                    try {
                        //image retrieval success and set image
                        Picasso.get().load(image).into(avatarIv);
                    } catch (Exception e) {
                        //Exception caught when retrieving image
                        Picasso.get().load(R.drawable.ic_add_image).into(avatarIv);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //handle edit button click
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditProfileDialog();
            }
        });


        //Delete from database
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Delete");
                builder.setMessage("Deactivating your account will delete your profile. Proceed? ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Customers");
                        user.delete();
                        databaseReference.removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        progressDialog.setMessage("Deleting..");
                                        Toast.makeText(getActivity(), "Account Deleted Successfully", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getActivity(), CustomerLoginActivity.class);
                                        startActivity(i);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Delete Failed", Toast.LENGTH_SHORT).show();

                                    }
                                });

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });


        return view;
    }


    private boolean checkStoragePermission() {
        //return true if storage permission enabled
        //return false if disabled
        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission() {
        //request runtime storage permission
        requestPermissions(storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        //return true if storage permission enabled
        //return false if disabled
        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission() {
        //request runtime storage permission
        requestPermissions(cameraPermissions, CAMERA_REQUEST_CODE);
    }


    private void showEditProfileDialog() {
        //Edit Username, Contact number and photo
        //Show options
        String options[] = {"Edit Profile Photo", "Edit Username", "Edit Contact Number"};
        //Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //set Title
        builder.setTitle("Choose action");
        //Set items for dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //handle item clicks
                if (i == 0) {
                    //Edit Username clicked
                    progressDialog.setMessage("Updating Username");
                    //call method to update username and contact, pass key as parameter
                    //to update value in database
                    showNameContactUpdateDialog("username");
                } else if (i == 1) {
                    //Edit Contact Clicked
                    progressDialog.setMessage("Updating Contact Number");
                    //call method to update username and contact, pass key as parameter
                    //to update value in database
                    showNameContactUpdateDialog("contact");
                }

            }
        });
        //create and show dialog
        builder.create().show();
    }

    private void showNameContactUpdateDialog(final String key) {
        //parameter "key" will contain value
        //key is username or contact, used to update value in database
        //custom dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update " + key);
        //dialog layout
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10, 10, 10, 10);

        //add edit text
        final EditText editText = new EditText(getActivity());
        editText.setHint("Enter " + key); //edit username, contact
        linearLayout.addView(editText);

        builder.setView(linearLayout);

        //add button in dialog to update
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //input text from edit text
                String value = editText.getText().toString().trim();
                //validate if user has entered something or not
                if (!TextUtils.isEmpty(value)) {
                    progressDialog.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put(key, value);

                    databaseReference.child(user.getUid()).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //updated, dismiss dialog
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "Updated..", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //failed, dismiss progress, display error
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });

                } else {
                    Toast.makeText(getActivity(), "Please Enter" + key, Toast.LENGTH_SHORT).show();
                }

            }
        });
        //button to cancel
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                progressDialog.dismiss();

            }
        });
        builder.create().show();
    }
}


