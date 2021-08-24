package com.example.quickdel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class PlaceQuickdelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_quickdel);
    }

    public void handleProductList (View v){
        TextView prod = findViewById(R.id.productList);
        TextView amt = findViewById(R.id.merchantName);
        TextView mrc = findViewById(R.id.estimatedAmount);
        TextView merchadd = findViewById(R.id.merchantAddress);
        String prodinput = prod.getText().toString();
        String amtinput = amt.getText().toString();
        String mrcinput = mrc.getText().toString();
        String merchaddinput = merchadd.getText().toString();


    }
}