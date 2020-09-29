package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    Button btn;
    Button edit;
    //ImageButton bt2;
    DatabaseReference reRef;
    FirebaseDatabase db;
    ListView list;
    ArrayList<String> foodlist ;
    Food food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.list);
        db = FirebaseDatabase.getInstance();
        foodlist = new ArrayList<>();
        final ArrayAdapter<String> adapt;



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
        reRef = FirebaseDatabase.getInstance().getReference("Foods");
        adapt = new ArrayAdapter<>(this, R.layout.row_for_menu, R.id.descript_id, foodlist);

        reRef.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                  for (DataSnapshot snap :snapshot.getChildren())
                  {
                      food = snap.getValue(Food.class);


                      assert food != null;
                      foodlist.add(food.getName().toString()+"\n\n"+ food.getDescription().toString()+"\n"+food.getFd_price());




                  }

                  list.setAdapter(adapt);

                  list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                      @Override
                      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                          String des =food.getDescription();
                          double pr = food.getFd_price();



                          Intent  intent1 = new Intent (MainActivity.this,Edit.class);
                          intent1.putExtra("food_name",des);
                          intent1.putExtra("price", pr);
                          startActivity(intent1);

                      }
                  }) ;



              }





              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }
          });






    }
}