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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private String mParam1;
    private String mParam2;
    private AdapterCart adp1;
    Button btn;
    Button edit;
    //ImageButton bt2;
    DatabaseReference reRef;
    FirebaseDatabase db;
    RecyclerView list;
    ArrayList<Food> foodlist;
    Food food;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        list = view.findViewById(R.id.recyclerView2);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        reRef = FirebaseDatabase.getInstance().getReference("Foods");
        foodlist = new ArrayList<>();
        food = new Food();
        final ArrayAdapter<String> adapt;


        ValueEventListener valueEventListener = reRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    food = snap.getValue(Food.class);
                    foodlist.add(food);
                }


               // adp1 = new AdapterCart((DashboardActivity) getContext().getApplicationContext(), foodlist);
                //list.setAdapter(adp1);
                list.setAdapter(new AdapterCart(getActivity(),foodlist));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        return view;
    }

    private Context getTargetFragment(HomeFragment homeFragment) {
        return this.getContext();
    }

}
