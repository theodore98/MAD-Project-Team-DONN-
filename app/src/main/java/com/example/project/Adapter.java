package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class Adapter extends RecyclerView.Adapter<Adapter.ImageViewHolder> {
    private Context mcontext;
    private List<Food> lfds;

    public  Adapter(Context con ,List<Food> fds)
    {
        mcontext = con;
        lfds = fds;
    }

    @NonNull
    @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mcontext).inflate(R.layout.row_for_menu,parent,false);
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

    @Override
    public int getItemCount() {
        return lfds.size();
    }

    public  class  ImageViewHolder extends  RecyclerView.ViewHolder implements  View.OnClickListener{
        public TextView txtdes, nm;
        public ImageView imageView;
        public Button dlt;

        public  ImageViewHolder(View itemView){
            super(itemView);
            txtdes =  itemView.findViewById(R.id.descript_id);
            imageView = itemView.findViewById(R.id.image_vw);
            nm = itemView.findViewById(R.id.Nm_id);
            dlt = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(this);
            itemView.setClickable(true);


            DatabaseReference dltRef = FirebaseDatabase.getInstance().getReference().child("Foods");
            dlt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dltRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(lfds.get(getAdapterPosition()).getF_id()))
                            {
                               dltRef.child(lfds.get(getAdapterPosition()).getF_id()).removeValue();
                               Toast.makeText(mcontext.getApplicationContext(),"Deleted",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(mcontext, MainActivity.class);
                                mcontext.startActivity(i);
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
            Intent i = new Intent(mcontext,Edit.class);

            i.putExtra("des",lfds.get(getAdapterPosition()).getDescription());
            i.putExtra("imagepath",lfds.get(getAdapterPosition()).getImageur());
            i.putExtra("price",lfds.get(getAdapterPosition()).getFd_price());
            i.putExtra("key",lfds.get(getAdapterPosition()).getF_id());
            i.putExtra("fdname",lfds.get(getAdapterPosition()).getName());

            mcontext.startActivity(i);



        }
    }
}
