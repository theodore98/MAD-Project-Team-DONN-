package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class HolderUser extends FirebaseRecyclerAdapter<Customer,HolderUser.viewholder> {

    public HolderUser(@NonNull FirebaseRecyclerOptions<Customer> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull final viewholder holder, final int position, @NonNull final Customer customer) {

        holder.username.setText(customer.getUsername());
        holder.contact.setText(customer.getContact());
        holder.email.setText(customer.getEmail());



    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer,parent, false);
        return new viewholder(view);
    }

    class viewholder extends RecyclerView.ViewHolder
    {
           TextView username, contact, email;
           Button edit;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            username = (TextView)itemView.findViewById(R.id.username_tv);
            contact = (TextView)itemView.findViewById(R.id.contact_tv);
            email = (TextView)itemView.findViewById(R.id.email_tv);

        }
    }
}
