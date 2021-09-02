package com.example.quickdel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchQuickdel (View v){
        //launch place quickdel

        Intent i = new Intent(this, PlaceQuickdelActivity.class);
        startActivity(i);
    }
    public void disable(View v){ v.setEnabled(false);
    }
}