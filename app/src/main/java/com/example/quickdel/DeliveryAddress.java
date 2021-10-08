package com.example.quickdel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class DeliveryAddress extends AppCompatActivity {
    Button save;
    ImageView back_btn;
    EditText address;
    DatabaseReference customerDBRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);

        save = findViewById(R.id.saveaddress);
        back_btn = findViewById(R.id.btn_back);
        address = findViewById(R.id.c_address);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { DeliveryAddress.super.onBackPressed(); }
        });

        //Initialise Places API
        Places.initialize(getApplicationContext(), "AIzaSyACJxIppMvQl7aBxiRLHozzHSA64FU5P_4");

        customerDBRef = FirebaseDatabase.getInstance().getReference().child("users");

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null){
                            Place place = Autocomplete.getPlaceFromIntent(result.getData());
                                //set address on Edittext
                                address.setText(place.getAddress());
                                //get latitude and longitude
                            }
                        }
                    });

        address.setFocusable(false);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //Initialize Place field list
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG);
                //Create Intent
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fields).build(DeliveryAddress.this);
                //Start Activity
                //startActivityForResult(intent, 100);
                activityResultLauncher.launch(intent);
            }
        });

        save.setOnClickListener(view -> saveAddress());

    }

    private void saveAddress(){
        String userAddress = address.getText().toString();

        SharedPreferences settings1 = getSharedPreferences("Profile", Context.MODE_PRIVATE);
        String userName = settings1.getString("username", "");

        CustomerAddress customerAddress = new CustomerAddress(userAddress);

        customerDBRef.child(userName).child("address").setValue(customerAddress);
        Toast.makeText(DeliveryAddress.this,"Address Successfully Saved", Toast.LENGTH_SHORT).show();

    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}