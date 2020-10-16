package com.example.rmsapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = DashboardActivity.class.getSimpleName();
    //firebase authentication
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;
    ChipNavigationBar bottomNav;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //Action Bar and its title
        actionBar = getSupportActionBar();
        actionBar.setTitle("Dashboard");

        //initialization
        firebaseAuth =FirebaseAuth.getInstance();
        //Bottom navigation view
        bottomNav = findViewById(R.id.navigation);

        //Default load homeview fragment when customer logs in
        if(savedInstanceState==null){
            bottomNav.setItemSelected(R.id.home, true);
            fragmentManager = getSupportFragmentManager();
            HomeFragment homeFragment = new HomeFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container,homeFragment).commit();
        }


        //Onselected item listener for bottom navbar
        bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i){
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;

                    /*case R.id.cart:
                        fragment = new CartFragment();
                        break;*/

                    case R.id.profile:
                        fragment = new CustomerProfileFragment();
                        break;
                }
                if(fragment!=null){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                }else{
                    Log.e(TAG, "Error creating fragment");
                }

            }
        });

    }


    private void checkUserStatus(){
        //Get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            //User is signed in and no changes
            //Set email of logged in user
            // loggedEmail.setText(user.getEmail());
        }
        else{
            //User is not signed in, go to main activity
            startActivity(new Intent(DashboardActivity.this, CustomerLoginActivity.class));
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        //Check when app is started
        checkUserStatus();
        super.onStart();
    }

    //Inflate options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating menu
        getMenuInflater().inflate(R.menu.menu_main, menu) ;
        return super.onCreateOptionsMenu(menu);
    }

    //Handle menu item click
    //LOGOUT

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Get item id
        int id = item.getItemId();
        if(id == R.id.action_logout){
            firebaseAuth.signOut();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }
}
