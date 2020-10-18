package com.example.rmsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ImageViewHolder> {
    private Context mcontext;
    private List<Food> lfds;
    private Orders or;



    public  AdapterCart(Context con , List<Food> fds)
    {

        mcontext = con;
        lfds = fds;
    }



    @NonNull

    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mcontext).inflate(R.layout.user_food,parent,false);
        return new ImageViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Food fd = lfds.get(position);
        holder.txtdes.setText(fd.getDescription());
        holder.nm.setText(fd.getName());
        Picasso.get()
                .load(fd.getImageur())
                .fit()
                .centerCrop()
                .into(holder.imageView);

    }





    public int getItemCount() {
        return lfds.size();
    }

    public  class  ImageViewHolder extends  RecyclerView.ViewHolder implements  View.OnClickListener{
        public TextView txtdes, nm ,price;
        public ImageView imageView;
        public Button add;

        public  ImageViewHolder(View itemView){
            super(itemView);
            txtdes =  itemView.findViewById(R.id.descript_id);
            imageView = itemView.findViewById(R.id.image_vw);
            nm = itemView.findViewById(R.id.Nm_id);
            add = itemView.findViewById(R.id.button);
            price = itemView.findViewById(R.id.price_id);

            itemView.setOnClickListener(this);
            itemView.setClickable(true);


            final DatabaseReference addRef = FirebaseDatabase.getInstance().getReference().child("Foods");
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(lfds.get(getAdapterPosition()).getF_id()))
                            {
                                String key =addRef.child(lfds.get(getAdapterPosition()).getF_id()).getKey();
                                Toast.makeText(mcontext.getApplicationContext(),"you ordered this food",Toast.LENGTH_SHORT).show();
                                DatabaseReference add = FirebaseDatabase.getInstance().getReference().child("Orders");
                                 or= new Orders();
                                 or.setFood_id(key);
                                 or.setFdname(lfds.get(getAdapterPosition()).getName());
                                 or.setPrice(lfds.get(getAdapterPosition()).getFd_price());
                                 String keyor = add.push().getKey();
                                 or.setOderid(keyor);
                                 add.child(keyor).push();



                            }
                            else{
                                Toast.makeText(mcontext.getApplicationContext(),"no source to Delete",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });



        }



        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            //Intent i = new Intent(mcontext,Edit.class);

           // i.putExtra("des",lfds.get(getAdapterPosition()).getDescription());
           // i.putExtra("imagepath",lfds.get(getAdapterPosition()).getImageur());
           // i.putExtra("price",lfds.get(getAdapterPosition()).getFd_price());
           // i.putExtra("key",lfds.get(getAdapterPosition()).getF_id());
           // i.putExtra("fdname",lfds.get(getAdapterPosition()).getName());

           //  mcontext.startActivity(i);



        }
    }
}
