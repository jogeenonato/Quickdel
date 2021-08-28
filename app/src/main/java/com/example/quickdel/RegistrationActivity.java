package com.example.quickdel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void GotoRegistration(View v) {
        Intent i = new Intent(this, com.example.quickdel.LoginActivity.class);
        startActivity(i);
    }
}