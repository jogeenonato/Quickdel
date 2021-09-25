package com.example.quickdel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quickdel.ui.home.HomeFragment;

public class Track extends AppCompatActivity {
    //Initialize variable
    EditText etSource,etDestination;
    Button btTrack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        SharedPreferences settings = getSharedPreferences("Order", Context.MODE_PRIVATE);
        String pickupPoint = settings.getString("pickupPoint", "");


        //Assign Variable
        etSource = findViewById(R.id.et_source);
        etDestination = findViewById(R.id.et_destination);
        btTrack = findViewById(R.id.bt_track);

        //String sourceLocation = getIntent().getStringExtra("keysource");
        String sourceLocation = "";
        String destinationLocation = getIntent().getStringExtra("keydestination");

        etSource.setText(sourceLocation);
        etDestination.setText(pickupPoint);

        btTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get value from edit text
                String sSource = etSource.getText().toString().trim();
                String sDestination = etDestination.getText().toString().trim();

                //Check condition
                if (sDestination.equals("")){
                    //When both value blank
                    Toast.makeText(getApplicationContext()
                            , "Enter Destination point",Toast.LENGTH_LONG).show();}
               // else{
                    //when both value fill, display track
                    DisplayTrack(sSource,sDestination);
                //}
            }
        });
    }

    private void DisplayTrack(String sSource, String sDestination) {
        //if the devise does not have a map installed, then redirect to playstore
        try {
            //When google map is installed
            //Initialize uri
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + sSource + "/"
                    + sDestination);
            //Initialize intent with action vew
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            //Set package
            intent.setPackage("com.google.android.apps.maps");
            //set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //start Activity
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            //When google map is not installed
            //Initialize uri
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            //Initialize intent with action view
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            //Set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //sTART ACTIVITY
            startActivity(intent);





        }

        Intent intent = new Intent(Track.this, HomeFragment.class);
        startActivity(intent);
}}