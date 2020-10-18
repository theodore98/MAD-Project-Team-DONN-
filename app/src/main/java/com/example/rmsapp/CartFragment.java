package com.example.rmsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // image = findViewById(R.id.image_view);

    Intent in = getActivity().getIntent();
    private Context mcontext;
    String fddescription = getActivity().getIntent().getStringExtra("des");
    String imageur = getActivity().getIntent().getStringExtra("imagepath");
    String price1 = String.valueOf(getActivity().getIntent().getDoubleExtra("price", 0));
    final String key = getActivity().getIntent().getStringExtra("key");
    String fnname = getActivity().getIntent().getStringExtra("fdname");


    //Picasso get().load(imageur).into(image);

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView  nm ,price;
        Button chk;
        price = getView().findViewById(R.id.cart_price);
        nm = getView().findViewById(R.id.fd_name_cart);
        chk = getView().findViewById(R.id.chk);

        nm.setText(fnname);
        price.setText(price1);

        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext.getApplicationContext(),"you purchased for this.",Toast.LENGTH_SHORT).show();
            }
        });


        return inflater.inflate(R.layout.fragment_cart, container, false);
    }
}