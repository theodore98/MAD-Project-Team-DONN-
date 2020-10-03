package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
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

public class HolderBus extends FirebaseRecyclerAdapter<Business,HolderBus.viewholder> {

    public HolderBus(@NonNull FirebaseRecyclerOptions<Business> option) {
        super(option);

    }

    @Override
    protected void onBindViewHolder(@NonNull final viewholder holder, final int position, @NonNull final Business business) {

        holder.owner.setText(business.getOwner());
        holder.mobileNo.setText("Mobile No " + business.getMobileNo());
        holder.email.setText(business.getEmail());
        holder.accountNo.setText(business.getAccountNumber());
        holder.bank.setText(business.getBank());
        holder.name.setText(business.getName());
        holder.address.setText(business.getAddress());

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.business,parent, false);
        return new viewholder(view);
    }

    class viewholder extends RecyclerView.ViewHolder
    {
        TextView owner, mobileNo, email, bank, name, accountNo, address;
        Button edit;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            owner = (TextView)itemView.findViewById(R.id.owner_tv);
            mobileNo = (TextView)itemView.findViewById(R.id.bus_contact_tv);
            email = (TextView)itemView.findViewById(R.id.bus_email_tv);
            bank = (TextView)itemView.findViewById(R.id.bank_tv);
            name = (TextView)itemView.findViewById(R.id.name_tv);
            accountNo = (TextView)itemView.findViewById(R.id.accountNo_tv);
            address = (TextView)itemView.findViewById(R.id.address_tv);

        }
    }
}
