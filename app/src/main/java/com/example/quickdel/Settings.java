package com.example.quickdel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        button = (Button) findViewById(R.id.button11);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetails();
            }
        });

        button = (Button) findViewById(R.id.button16);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFaq();
            }
        });
        button = (Button) findViewById(R.id.button13);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConditions();
            }
        });
        button = (Button) findViewById(R.id.button15);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPreferences();
            }
        });
        button = (Button) findViewById(R.id.button14);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPayment();
            }
        });
        button = (Button) findViewById(R.id.button12);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSupport();
            }
        });
        button = (Button) findViewById(R.id.bt_send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opentest();
            }
        });


    }

    public void openDetails() {
        Intent intent = new Intent(this, Details.class);
        startActivity(intent);
    }

    public void openFaq() {
        Intent intent = new Intent(this, com.example.quickdel.Faq.class);
        startActivity(intent);
    }

    public void openConditions() {
        Intent intent = new Intent(this, com.example.quickdel.Conditions.class);
        startActivity(intent);
    }

    public void openPreferences() {
        Intent intent = new Intent(this, com.example.quickdel.RunnerPreferences.class);
        startActivity(intent);
    }

    public void openPayment() {
        Intent intent = new Intent(this, com.example.quickdel.RunnerPaymentMethod.class);
        startActivity(intent);
    }

    public void openSupport() {
        Intent intent = new Intent(this, com.example.quickdel.Support.class);
        startActivity(intent);
    }

    public void opentest() {
        Intent intent = new Intent(this, com.example.quickdel.test.class);
        startActivity(intent);
    }
}