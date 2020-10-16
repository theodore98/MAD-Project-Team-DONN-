package com.example.rmsapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class CartFragment extends Fragment {

    private Adapter adp;
    Button btn;
    Button edit;
    //ImageButton bt2;
    DatabaseReference reRef;
    FirebaseDatabase db;
    RecyclerView list;
    ArrayList<Food> foodlist ;
    Food food;
    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*list = list.findViewById(R.id.descript_id);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getTargetFragment(this)));
        reRef = FirebaseDatabase.getInstance().getReference("Foods");
        foodlist = new ArrayList<>();
        food = new Food();
        final ArrayAdapter<String> adapt;

        reRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap :snapshot.getChildren())
                {
                    food = snap.getValue(Food.class);
                    foodlist.add(food);

                }

                adp = new cartAdapter(CartFragment.this,foodlist);
                list.setAdapter(adp);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private Context getTargetFragment(CartFragment cartFragment) {
        return this.getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }*/
}}