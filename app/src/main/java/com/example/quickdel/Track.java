package com.example.quickdel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickdel.Model.DriverInfoModel;

import com.example.quickdel.ui.gallery.GalleryFragment;
import com.example.quickdel.ui.gallery.GalleryViewModel;
import com.example.quickdel.ui.home.HomeFragment;
import com.example.quickdel.ui.slideshow.SlideshowFragment;

import java.sql.DriverManager;

public class Track extends AppCompatActivity {
    //Initialize variable
    EditText etSource,etDestination,Recipient;

    Button btTrack;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_track);

        Intent intent = getIntent();
        SharedPreferences settings = getSharedPreferences("Order", Context.MODE_PRIVATE);
        String pickupPoint = settings.getString("pickupPoint", "");
        String edittext = settings.getString("recipient", "");
        float total = settings.getFloat("total", -1);
        String total1 = String.valueOf(total);




        //Assign Variable
        btTrack = findViewById(R.id.bt_track);
        etSource = findViewById(R.id.et_source);
        etDestination = findViewById(R.id.et_destination);
        Recipient = findViewById(R.id.recipient);
        button = findViewById(R.id.bt_startDestination);



        //String sourceLocation = getIntent().getStringExtra("keysource");
        String sourceLocation = "";
        //String destinationLocation = getIntent().getStringExtra("keydestination");

        etSource.setText(sourceLocation);
        etDestination.setText(pickupPoint);
//        Recipient.setText(edittext);

        TextView mResultTv = findViewById(R.id.resultTv);
        mResultTv.setText("Pick up Address: "+pickupPoint+"\n\nQuickdel to: "+edittext+"\n\nTotal: $ "+total1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openDeliveryToDestination();
            }
            
        });




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
                else{
                    //when both value fill, display track
                    DisplayTrack(sSource,sDestination);
                }
                btTrack.setVisibility(View.INVISIBLE);
                Thread timer = new Thread() {
                    public void run(){
                        try {
                            sleep(3000);
                            button.setVisibility(View.VISIBLE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                };
                timer.start();


            }
        });

    }
    public void disable (View v) {

        v.setEnabled(false);
        Button b = (Button) v;
        b.setText("Disabled");

    }
    public void openDeliveryToDestination() {
        Intent intent = new Intent(this, com.example.quickdel.DeliveryToDestination.class);
        startActivity(intent);
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
        //Intent intent = new Intent(Track.this, HomeFragment.class);
        //startActivity(intent);
}

}