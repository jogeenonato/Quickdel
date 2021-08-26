package com.example.quickdel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class PlaceQuickdelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_quickdel);
        setTitle("Place Quickdel Order");
    }

    public void handleProductList (View v){
        //launch OrderConfirmation activity
        Intent i = new Intent(this, OrderConfirmation.class);
        String prodinput = ((EditText)findViewById(R.id.productList)).getText().toString();
        i.putExtra ("ORDER", prodinput);
        String mrcinput = ((EditText)findViewById(R.id.merchantName)).getText().toString();
        i.putExtra ("MERCH", mrcinput);
        String amtinput = ((EditText)findViewById(R.id.estimatedAmount)).getText().toString();
        i.putExtra ("COST", amtinput);
        String merchaddinput = ((EditText)findViewById(R.id.merchantAddress)).getText().toString();
        i.putExtra ("ADDRESS", merchaddinput);
        startActivity(i);
    }
}