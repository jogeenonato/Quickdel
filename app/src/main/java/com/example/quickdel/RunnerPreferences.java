package com.example.quickdel;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class RunnerPreferences extends AppCompatActivity {

    Button btn;
    Preferences preferences;
    RadioButton bike, sedan, ute, small, medium, large, weight1, weight2, weight3, weight4;
    FirebaseDatabase database;
    DatabaseReference reference;
    String sType;
    int i = 0;
    ImageView back_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_runner_preferences);

        btn = findViewById(R.id.proceed_r);
        preferences = new Preferences()  ;
        bike = findViewById(R.id.v_bike_r);
        sedan = findViewById(R.id.v_sedan_r);
        ute = findViewById(R.id.v_ute_r);
        small = findViewById(R.id.s_small_r);
        medium = findViewById(R.id.s_medium_r);
        large = findViewById(R.id.s_large_r);
        weight1 = findViewById(R.id.weigh1_r);
        weight2 = findViewById(R.id.weight2_r);
        weight3 = findViewById(R.id.weight3_r);
        weight4 = findViewById(R.id.weight4_r);

        back_btn = findViewById(R.id.btn_back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RunnerPreferences.super.onBackPressed();
            }
        });

        //Initialise Places API
        Places.initialize(getApplicationContext(), "AIzaSyC5Dl6IRV_S908iz8VHnMED-pQ0SbOEAl0");

        reference = database.getInstance().getReference().child("Runner's Preferences");

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
                    preferences.setVehicle(v1);
                    reference.child(String.valueOf(i+1)).setValue(preferences);
                } else if (sedan.isChecked()){
                    preferences.setVehicle(v2);
                    reference.child(String.valueOf(i+1)).setValue(preferences);
                } else {
                    preferences.setVehicle(v3);
                    reference.child(String.valueOf(i+1)).setValue(preferences);
                }

                if (small.isChecked()){
                    preferences.setSize(s1);
                    reference.child(String.valueOf(i+1)).setValue(preferences);
                } else if (medium.isChecked()){
                    preferences.setSize(s2);
                    reference.child(String.valueOf(i+1)).setValue(preferences);
                } else {
                    preferences.setSize(s3);
                    reference.child(String.valueOf(i+1)).setValue(preferences);
                }

                if (weight1.isChecked()){
                    preferences.setWeight(w1);
                    reference.child(String.valueOf(i+1)).setValue(preferences);
                } else if (weight2.isChecked()){
                    preferences.setWeight(w2);
                    reference.child(String.valueOf(i+1)).setValue(preferences);
                } else if (weight3.isChecked()) {
                    preferences.setWeight(w3);
                    reference.child(String.valueOf(i + 1)).setValue(preferences);
                } else {
                    preferences.setWeight(w4);
                    reference.child(String.valueOf(i+1)).setValue(preferences);
                }

            }
        });


    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}


