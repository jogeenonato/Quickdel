package com.example.quickdel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fevziomurtekin.payview.Payview;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Payment extends AppCompatActivity {

    Orders orders;
    DatabaseReference reference;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_payment);
        // on below line we are creating a variable
        // for our pay view and initializing it.
        Payview payview = findViewById(R.id.payview);
        orders = new Orders();

        reference = database.getInstance().getReference().child("Orders");
        // on below line we are setting pay on listener for our card.
        payview.setPayOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // after clicking on pay we are displaying toast message as card added.
                SharedPreferences settings = getSharedPreferences("OrderNumber", Context.MODE_PRIVATE);
                String orderNumber = settings.getString("orderNumber", "");
                //orders.setStatus("paid");
                Toast.makeText(Payment.this, "Your Quickdel order has been placed!" + orderNumber, Toast.LENGTH_SHORT).show();
                reference.child(orderNumber).child("status").setValue("paid");

                Intent intent = new Intent(Payment.this, Online.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


            }
        });
    }
} 