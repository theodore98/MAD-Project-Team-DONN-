package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class viewCompany extends AppCompatActivity {

    RecyclerView recyclerView;
    HolderBus holderBus;
    DatabaseReference ref;
    FirebaseDatabase db;
    Button userHome;
    final Loading loading = new Loading(viewCompany.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_company);

        recyclerView=(RecyclerView)findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Business> option =
                new FirebaseRecyclerOptions.Builder<Business>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Business"), Business.class)
                        .build();

        holderBus = new HolderBus(option);
        recyclerView.setAdapter(holderBus);

        userHome = findViewById(R.id.viewhome);

        userHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                },30000);
                startActivity(new Intent((getApplicationContext()), Profile.class));
                finish();
            }
        });

    }
  @Override
    protected void onStart() {
        super.onStart();
        holderBus.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        holderBus.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search,menu);

        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processSearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



    private void processSearch(String s) {

        FirebaseRecyclerOptions<Business> option =
                new FirebaseRecyclerOptions.Builder<Business>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Business").orderByChild("owner").startAt(s).endAt(s +"\uf8ff"), Business.class)
                        .build();

        holderBus= new HolderBus(option);
        holderBus.startListening();
        recyclerView.setAdapter(holderBus);
    }
}