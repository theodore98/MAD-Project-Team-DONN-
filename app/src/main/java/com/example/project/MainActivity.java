package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private Adapter adp;
    Button btn;
    Button edit;
    //ImageButton bt2;
    DatabaseReference reRef;
    FirebaseDatabase db;
    RecyclerView list;
    ArrayList<Food> foodlist ;
    Food food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.recyclerView);
        list.setHasFixedSize(true);
         list.setLayoutManager(new LinearLayoutManager(this));
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

                adp = new Adapter(MainActivity.this,foodlist);
                list.setAdapter(adp);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        btn = findViewById(R.id.newfood);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,newfood.class);
                startActivity(intent);
            }
        });




        /*edit  = findViewById(R.id.edit1);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



       bt2 = findViewById(R.id.);
       bt2.setLongClickable(true);
        bt2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Intent I = new Intent(MainActivity.this,Delete.class);
                startActivity(I);
                return true;
            }
        });*/


        food = new Food();
        /*reRef = FirebaseDatabase.getInstance().getReference("Foods");

        reRef.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                  for (DataSnapshot snap :snapshot.getChildren())
                  {
                     food = snap.getValue(Food.class);


                      assert food != null;
                      foodlist.add(food.getName().toString()+"\n\n"+food.getDescription());




                  }
                  adapt = new ArrayAdapter<String>(this, foodlist);


                  list.setAdapter(adapt);

                  list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                      @Override
                      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                          //String des =food.getDescription();
                          //double pr = food.getFd_price();



                          Intent  intent1 = new Intent (MainActivity.this,Edit.class);
                          intent1.putExtra(DESCRIPTION ,adapt.getItem(position));
                          //intent1.putExtra("price", pr);
                          startActivityForResult(intent1,REQUEST_RESPONSE);

                      }
                  }) ;



              }





              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }
          });*/






    }
}