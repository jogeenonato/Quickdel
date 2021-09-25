package com.example.quickdel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fevziomurtekin.payview.Payview;
import com.google.firebase.database.DatabaseReference;

public class Payment extends AppCompatActivity {

    Orders orders;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_payment);
        // on below line we are creating a variable
        // for our pay view and initializing it.
        Payview payview = findViewById(R.id.payview);
        orders = new Orders();

        // on below line we are setting pay on listener for our card.
        payview.setPayOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // after clicking on pay we are displaying toast message as card added.
                Toast.makeText(Payment.this, "Your Quickdel order has been placed!", Toast.LENGTH_SHORT).show();

                orders.setStatus("paid");
                reference.child(PlaceQuickdelActivity2.orderNumber).setValue(orders);

                Intent intent = new Intent(Payment.this, TrackRunner.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


            }
        });
    }
} 