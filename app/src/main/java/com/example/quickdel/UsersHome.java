package com.example.quickdel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UsersHome extends AppCompatActivity {
    private Button button;
    private Button settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_home);

        button = (Button) findViewById(R.id.button11);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlaceQuickdelActivity();
            }
        });

        settings = (Button) findViewById(R.id.button16);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCustomerSettings();
            }
        });
    }


    public void openCustomerSettings() {
        Intent intent = new Intent(this, CustomerSettingsActivity.class);
        startActivity(intent);
    }

    public void openPlaceQuickdelActivity() {
        Intent intent = new Intent(this, PlaceQuickdelActivity2.class);
        startActivity(intent);
    }


}