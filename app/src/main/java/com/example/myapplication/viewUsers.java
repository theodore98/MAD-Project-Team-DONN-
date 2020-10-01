package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class viewUsers extends AppCompatActivity {

    RecyclerView recyclerView;
    HolderUser holderUser;
    DatabaseReference ref;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Customer> options =
                new FirebaseRecyclerOptions.Builder<Customer>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Customer"), Customer.class)
                        .build();

        holderUser = new HolderUser(options);
        recyclerView.setAdapter(holderUser);
}

    @Override
    protected void onStart() {
        super.onStart();
        holderUser.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        holderUser.stopListening();
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

        FirebaseRecyclerOptions<Customer> options =
                new FirebaseRecyclerOptions.Builder<Customer>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Customer").orderByChild("username").startAt(s).endAt(s +"\uf8ff"), Customer.class)
                        .build();

        holderUser= new HolderUser(options);
        holderUser.startListening();
        recyclerView.setAdapter(holderUser);
    }
}