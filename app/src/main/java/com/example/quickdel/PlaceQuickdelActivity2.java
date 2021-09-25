package com.example.quickdel;

import static java.lang.String.valueOf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PlaceQuickdelActivity2 extends AppCompatActivity {

    Button btn;
    Orders orders;
    RandomAlphaNumeric orderNumbers;
    RadioButton bike, sedan, ute, small, medium, large, weight1, weight2, weight3, weight4;
    FirebaseDatabase database;
    DatabaseReference reference;
    int i = 0;
    EditText etPickup, etDestination, etDesc, recipient, recipientNo;
    TextView tvDistance;
    String sType;
    public static String orderNumber;
    double lat1 = 0;
    double long1 = 0;
    double lat2 = 0;
    double long2 = 0;
    int flag = 0;
    String uid, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_quickdel2);
        setTitle("Place Quickdel Order");

        btn = findViewById(R.id.proceed);
        orders = new Orders()  ;
        RandomAlphaNumeric orderNumbers = new RandomAlphaNumeric();
        bike = findViewById(R.id.v_bike);
        sedan = findViewById(R.id.v_sedan);
        ute = findViewById(R.id.v_ute);
        small = findViewById(R.id.s_small);
        medium = findViewById(R.id.s_medium);
        large = findViewById(R.id.s_large);
        weight1 = findViewById(R.id.weigh1);
        weight2 = findViewById(R.id.weight2);
        weight3 = findViewById(R.id.weight3);
        weight4 = findViewById(R.id.weight4);
        etPickup = findViewById(R.id.pickup);
        etDestination = findViewById(R.id.destination);
        tvDistance = findViewById(R.id.text_distance);
        etDesc  = findViewById(R.id.et_description);
        recipient = findViewById(R.id.et_recipient);
        recipientNo = findViewById(R.id.et_recipientMobile);



        //Initialise Places API
        Places.initialize(getApplicationContext(), "AIzaSyACJxIppMvQl7aBxiRLHozzHSA64FU5P_4");

        reference = database.getInstance().getReference().child("Orders");

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



        //Get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            //String name = user.getDisplayName();
            //String email = user.getEmail();

            // Check if user's email is verified
            //boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            uid = user.getUid();
            userName = user.getDisplayName();
        }
        

        //Alternative to onResultActivity

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null){
                        Place place = Autocomplete.getPlaceFromIntent(result.getData());
                            //Check condition
                            if (sType.equals("pickup")){
                                flag++;
                                //set address on Edittext
                                etPickup.setText(place.getAddress());
                                //get latitude and longitude
                                String sSource = valueOf(place.getLatLng());
//                                sSource = sSource.replaceAll("lat/lng", "");
//                                sSource = sSource.replace("(", "");
//                                sSource = sSource.replace(")", "");
//                                String[] split = sSource.split(",");
//                                lat1 = Double.parseDouble(split[0]);
//                                long1 = Double.parseDouble(split[1]);
                                    lat1 = place.getLatLng().latitude;
                                    long1 = place.getLatLng().longitude;

                            }else {
                                //when type is destination
                                flag++;
                                //set address on edittext
                                etDestination.setText(place.getAddress());
                                //Get latitude and longitude
                                String sDestination = valueOf(place.getLatLng());
//                                sDestination = sDestination.replaceAll("lat/lng", "");
//                                sDestination = sDestination.replace("(", "");
//                                sDestination = sDestination.replace(")", "");
//                                String[] split = sDestination.split(",");
//                                lat2 = Double.parseDouble(split[0]);
//                                long2 = Double.parseDouble(split[1]);
                                lat2 = place.getLatLng().latitude;
                                long2 = place.getLatLng().longitude;
                            }

                    if (flag >= 2) {
                                //When flag is greater than or equal to two, calculate the distance
                                distance(lat1, long1, lat2, long2);
                            }
                        } else if (result.getResultCode() == AutocompleteActivity.RESULT_ERROR) {
                            Status status = Autocomplete.getStatusFromIntent(result.getData());
                            Toast.makeText(getApplicationContext(),status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    private void distance(double lat1, double long1, double lat2, double long2) {
                        //calculate longitude diff
                        double longDiff = long1 - long2;
                        //calculate distance
                        double distance = Math.sin(degtorad(lat1))
                                * Math.sin(degtorad(lat2))
                                + Math.cos(degtorad(lat1))
                                * Math.cos(degtorad(lat2))
                                * Math.cos(degtorad(longDiff));
                        distance = Math.acos(distance);
                        //Convert distance to degrees
                        distance = radtodeg(distance);
                        //distance to miles
                        distance = distance * 60 * 1.1515;
                        //distance in km
                        distance = distance * 1.609344;
                        //set distance on textview
                        tvDistance.setText(String.format(Locale.UK, "%.2f", distance));
                    }

                    private double radtodeg(double distance) {
                        return (distance * 180.0 / Math.PI);
                    }

                    private double degtorad(double lat1) {
                        return (lat1*Math.PI/180.0);
                    }
                    }
                );


        //Set Edit Text non-focusable

        etPickup.setFocusable(false);
        etPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //Define type
                sType = "pickup";
                //Initialize Place field list
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG);
                //Create Intent
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fields).build(PlaceQuickdelActivity2.this);
                //Start Activity
                //startActivityForResult(intent, 100);
                activityResultLauncher.launch(intent);
            }
        });

        //Set edit text non focusable
        etDestination.setFocusable(false);
        etDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Define type
                sType = "destination";
                //initialise place field list
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG);
                //Create intent
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(PlaceQuickdelActivity2.this);
                //Start activity
                //startActivityForResult(intent, 100);
                activityResultLauncher.launch(intent);
            }
        });

        //Set text on TextView
        tvDistance.setText("0.0");

        orderNumber = (orderNumbers.generateOrderNo(5));

        SharedPreferences settings = getSharedPreferences("OrderNumber", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("orderNumber", orderNumber);
        editor.apply();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handleOrder();

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
                String pp = etPickup.getText().toString();
                String dp = etDestination.getText().toString();
                String dc = etDesc.getText().toString();
                String rn = recipient.getText().toString();
                String ds = tvDistance.getText().toString();
                String rnNo = recipientNo.getText().toString();

                double dsP = Double.parseDouble(ds);

                if (dsP <= 10){
                    orders.setDistancePrice(5.00);
                    reference.child(orderNumber).setValue(orders);
                } else if (dsP <= 10 && dsP >= 25){
                    orders.setDistancePrice(7.00);
                    reference.child(orderNumber).setValue(orders);
                } else {
                    orders.setDistancePrice(10.00);
                    reference.child(orderNumber).setValue(orders);
                }

                if (bike.isChecked()){
                    orders.setVehicle(v1);
                    orders.setVehiclePrice(10.00);
                    reference.child(orderNumber).setValue(orders);
                } else if (sedan.isChecked()){
                    orders.setVehicle(v2);
                    orders.setVehiclePrice(12.00);
                    reference.child(orderNumber).setValue(orders);
                } else {
                    orders.setVehicle(v3);
                    orders.setVehiclePrice(14.00);
                    reference.child(orderNumber).setValue(orders);
                }

                if (small.isChecked()){
                    orders.setSize(s1);
                    orders.setSizePrice(0.50);
                    reference.child(orderNumber).setValue(orders);
                } else if (medium.isChecked()){
                    orders.setSize(s2);
                    orders.setSizePrice(0.60);
                    reference.child(orderNumber).setValue(orders);
                } else {
                    orders.setSize(s3);
                    orders.setSizePrice(0.80);
                    reference.child(orderNumber).setValue(orders);
                }

                if (weight1.isChecked()){
                    orders.setWeight(w1);
                    orders.setWeightPrice(2.00);
                    reference.child(orderNumber).setValue(orders);
                } else if (weight2.isChecked()){
                    orders.setWeight(w2);
                    orders.setWeightPrice(3.00);
                    reference.child(orderNumber).setValue(orders);
                } else if (weight3.isChecked()) {
                    orders.setWeight(w3);
                    orders.setWeightPrice(4.00);
                    reference.child(valueOf(i + 1)).setValue(orders);
                } else {
                    orders.setWeight(w4);
                    orders.setWeightPrice(5.00);
                    reference.child(orderNumber).setValue(orders);
                }


                orders.setDistance(ds);
                reference.child(orderNumber).setValue(orders);

                orders.setDescription(dc);
                reference.child(orderNumber).setValue(orders);

                orders.setUserID(uid);
                reference.child(orderNumber).setValue(orders);

                orders.setUserName(userName);
                reference.child(orderNumber).setValue(orders);

                orders.setPickupPoint(pp);
                reference.child(orderNumber).setValue(orders);

                orders.setDestinationPoint(dp);
                reference.child(orderNumber).setValue(orders);

                orders.setRecipient(rn);
                reference.child(orderNumber).setValue(orders);

                orders.setRecipientPhone(rnNo);
                reference.child(orderNumber).setValue(orders);

                orders.setOrderNumber(orderNumber);
                reference.child(orderNumber).setValue(orders);

                orders.setTotal(orders.getWeightPrice()+orders.getSizePrice()+ orders.getVehiclePrice()+ orders.getDistancePrice());
                reference.child(orderNumber).setValue(orders);

                orders.setStatus("");
                reference.child(orderNumber).setValue(orders);

                orders.setRunnerID("");
                reference.child(orderNumber).setValue(orders);




            }
        });

        setupBackButton();
    }



    private void handleOrder() {
        //launch OrderConfirmation activity
        Intent i = new Intent(this, OrderConfirmation2.class);

        Double bikePrice = 10.00;
        Double sedanPrice = 12.00;
        Double utePrice = 14.00;
        Double smallPrice = 0.50;
        Double mediumPrice = 0.60;
        Double largePrice = 0.80;
        Double weight1Price = 2.00;
        Double weight2Price = 3.00;
        Double weight3Price = 4.00;
        Double weight4Price = 5.00;
        Double dPrice1 = 5.00;
        Double dPrice2 = 7.00;
        Double dPrice3 = 10.00;
        Double Total = 0.00;


        String orderno = orderNumber;
        i.putExtra("ORDER", orderno);

        String ds = tvDistance.getText().toString();
        i.putExtra("DISTANCE", ds);

        String pp = etPickup.getText().toString();
        i.putExtra("PICKUP", pp);

        String dp = etDestination.getText().toString();
        i.putExtra("DESTINATION", dp);

        String recipientName = recipient.getText().toString();
        i.putExtra("RECIPIENT", recipientName);

        String recipientNumber = recipientNo.getText().toString();
        i.putExtra("RECIPIENTNO", recipientNumber);

        if (bike.isChecked()){
            String vehicle = ((RadioButton)findViewById(R.id.v_bike)).getText().toString();
            i.putExtra ("VEHICLE", vehicle);
            String vehiclePrice = bikePrice.toString();
            i.putExtra("VPRICE", vehiclePrice);
            Total += bikePrice;
        } else if (sedan.isChecked()){
            String vehicle = ((RadioButton)findViewById(R.id.v_sedan)).getText().toString();
            i.putExtra ("VEHICLE", vehicle);
            String vehiclePrice = sedanPrice.toString();
            i.putExtra("VPRICE", vehiclePrice);
            Total += sedanPrice;
        } else {
            String vehicle = ((RadioButton)findViewById(R.id.v_ute)).getText().toString();
            i.putExtra ("VEHICLE", vehicle);
            String vehiclePrice = utePrice.toString();
            i.putExtra("VPRICE", vehiclePrice);
            Total += utePrice;
        }

        if (small.isChecked()){
            String size = ((RadioButton)findViewById(R.id.s_small)).getText().toString();
            i.putExtra ("SIZE", size);
            String sizePrice = smallPrice.toString();
            i.putExtra("SPRICE", sizePrice);
            Total += smallPrice;
        } else if (medium.isChecked()){
            String size = ((RadioButton)findViewById(R.id.s_medium)).getText().toString();
            i.putExtra ("SIZE", size);
            String sizePrice = mediumPrice.toString();
            i.putExtra("SPRICE", sizePrice);
            Total += mediumPrice;
        } else {
            String size = ((RadioButton)findViewById(R.id.s_large)).getText().toString();
            i.putExtra ("SIZE", size);
            String sizePrice = largePrice.toString();
            i.putExtra("SPRICE", sizePrice);
            Total += largePrice;
        }

        if (weight1.isChecked()){
            String weight = ((RadioButton)findViewById(R.id.weigh1)).getText().toString();
            i.putExtra ("WEIGHT", weight);
            String weightPrice = weight1Price.toString();
            i.putExtra("WPRICE", weightPrice);
            Total += weight1Price;
        } else if (weight2.isChecked()){
            String weight = ((RadioButton)findViewById(R.id.weight2)).getText().toString();
            i.putExtra ("WEIGHT", weight);
            String weightPrice = weight2Price.toString();
            i.putExtra("WPRICE", weightPrice);
            Total += weight2Price;
        } else if (weight3.isChecked()) {
            String weight = ((RadioButton)findViewById(R.id.weight3)).getText().toString();
            i.putExtra ("WEIGHT", weight);
            String weightPrice = weight3Price.toString();
            i.putExtra("WPRICE", weightPrice);
            Total += weight3Price;
        } else {
            String weight = ((RadioButton)findViewById(R.id.weight4)).getText().toString();
            i.putExtra ("WEIGHT", weight);
            String weightPrice = weight4Price.toString();
            i.putExtra("WPRICE", weightPrice);
            Total += weight4Price;
        }

        double dsP = Double.parseDouble(ds);


        if (dsP <= 10){
            String distancePrice = dPrice1.toString();
            i.putExtra("DPRICE", distancePrice);
            Total += dPrice1;
        } else if (dsP <= 10 && dsP >= 25){
            String distancePrice = dPrice2.toString();
            i.putExtra("DPRICE", distancePrice);
            Total += dPrice2;
        } else {
            String distancePrice = dPrice3.toString();
            i.putExtra("DPRICE", distancePrice);
            Total += dPrice3;
        }



        String dc = etDesc.getText().toString();
        i.putExtra("DESC", dc);

        String total = Total.toString();
        i.putExtra("TOTAL", total);

        startActivity(i);
    }

    private void setupBackButton() {
        TextView bck = findViewById(R.id.back);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}


