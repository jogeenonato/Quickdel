package com.example.quickdel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class OrderConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        setTitle("Order Confirmation");
        Intent i = getIntent();
        String prodinput = i.getStringExtra("ORDER");
        ((TextView)findViewById(R.id.textView5)).setText(prodinput);
        String mrcinput = i.getStringExtra("MERCH");
        ((TextView)findViewById(R.id.textView6)).setText(mrcinput);
        String amtinput = i.getStringExtra("COST");
        ((TextView)findViewById(R.id.textView7)).setText(amtinput);
        String merchaddinput = i.getStringExtra("ADDRESS");
        ((TextView)findViewById(R.id.textView8)).setText(merchaddinput);
    }
}