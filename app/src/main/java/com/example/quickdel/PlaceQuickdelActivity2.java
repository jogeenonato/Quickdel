package com.example.quickdel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PlaceQuickdelActivity2 extends AppCompatActivity {

    Button btn;
    Orders orders;
    RadioButton bike, sedan, ute, small, medium, large, weight1, weight2, weight3, weight4;
    FirebaseDatabase database;
    DatabaseReference reference;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_quickdel2);

        btn = findViewById(R.id.proceed);
        orders = new Orders()  ;
        bike = findViewById(R.id.v_bike);
        sedan = findViewById(R.id.v_sedan);
        ute = findViewById(R.id.v_ute);
        small = findViewById(R.id.s_small);
        medium = findViewById(R.id.s_medium);
        large = findViewById(R.id.s_large);
        weight1 = findViewById(R.id.weigh1);
        weight2 = findViewById(R.id.weight2);
        weight3 = findViewById(R.id.weight3);
        weight4 = findViewById(R.id.weight4);


        reference = database.getInstance().getReference().child("Orders");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    i = (int) dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String v1 = bike.getText().toString();
                String v2 = sedan.getText().toString();
                String v3 = ute.getText().toString();
                String s1 = small.getText().toString();
                String s2 = medium.getText().toString();
                String s3 = large.getText().toString();
                String w1 = weight1.getText().toString();
                String w2 = weight2.getText().toString();
                String w3 = weight3.getText().toString();
                String w4 = weight4.getText().toString();

                if (bike.isChecked()){
                    orders.setVehicle(v1);
                    orders.setVehiclePrice(10.00);
                    reference.child(String.valueOf(i+1)).setValue(orders);
                } else if (sedan.isChecked()){
                    orders.setVehicle(v2);
                    orders.setVehiclePrice(12.00);
                    reference.child(String.valueOf(i+1)).setValue(orders);
                } else {
                    orders.setVehicle(v3);
                    orders.setVehiclePrice(14.00);
                    reference.child(String.valueOf(i+1)).setValue(orders);
                }

                if (small.isChecked()){
                    orders.setSize(s1);
                    orders.setSizePrice(0.50);
                    reference.child(String.valueOf(i+1)).setValue(orders);
                } else if (medium.isChecked()){
                    orders.setSize(s2);
                    orders.setSizePrice(0.60);
                    reference.child(String.valueOf(i+1)).setValue(orders);
                } else {
                    orders.setSize(s3);
                    orders.setSizePrice(0.80);
                    reference.child(String.valueOf(i+1)).setValue(orders);
                }

                if (weight1.isChecked()){
                    orders.setWeight(w1);
                    orders.setWeightPrice(2.00);
                    reference.child(String.valueOf(i+1)).setValue(orders);
                } else if (weight2.isChecked()){
                    orders.setWeight(w2);
                    orders.setWeightPrice(3.00);
                    reference.child(String.valueOf(i+1)).setValue(orders);
                } else if (weight3.isChecked()) {
                    orders.setWeight(w3);
                    orders.setWeightPrice(4.00);
                    reference.child(String.valueOf(i + 1)).setValue(orders);
                } else {
                    orders.setWeight(w4);
                    orders.setWeightPrice(5.00);
                    reference.child(String.valueOf(i+1)).setValue(orders);
                }

                orders.setTotal(orders.getWeightPrice()+orders.getSizePrice()+ orders.getVehiclePrice());
                reference.child(String.valueOf(i+1)).setValue(orders);


            }
        });


        }


    }

