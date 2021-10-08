package com.example.quickdel;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class Online extends FragmentActivity implements OnMapReadyCallback {
    private NotificationManagerCompat notificationManager;

    private GoogleMap mMap;
    private DatabaseReference databaseReference, ref, onlineRef, currentUserRef, driversLocationRef, reference;
    GeoFire geoFire;

    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    private LatLng latLng;
    String assignedRunner;
    private LocationRequest locationRequest;
    private com.google.android.gms.location.LocationCallback locationCallback;

    String stopnotif1 = "go", stopnotif2 = "go", stopnotif3 = "go", stopnotif4 = "go";
    private EditText editTextLatitude;
    private EditText editTextLongitude;
    private TextView textView;
    Button btn_show;
    ImageView back_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_online);
        notificationManager = NotificationManagerCompat.from(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        editTextLatitude = findViewById(R.id.editTextTextPersonName2);
        editTextLongitude = findViewById(R.id.editTextTextPersonName3);
        btn_show = findViewById(R.id.btn_show);
        textView = findViewById(R.id.LocationDisplay);
        back_btn = findViewById(R.id.btn_back);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Online.super.onBackPressed();
            }
        });
//
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                try {

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders");



                    SharedPreferences settings1 = getSharedPreferences("TrackOrderNumber", Context.MODE_PRIVATE);
                    String orderNumber = settings1.getString("orderNumber", "");

                    Query checkUser = ref.orderByChild("orderNumber").equalTo(orderNumber);

                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String runner = dataSnapshot.child(orderNumber).child("runnerID").getValue(String.class);
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Runner'sLocation");
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {

                                            double dbLatitudeString = dataSnapshot.child(runner).child("l").child("0").getValue(double.class);
                                            double dbLongitudeString = dataSnapshot.child(runner).child("l").child("1").getValue(double.class);

                                            String total1 = String.valueOf(dbLatitudeString);
                                            String total2 = String.valueOf(dbLongitudeString);
                                            editTextLatitude.setText(total1);
                                            editTextLongitude.setText(total2);
                                            double latitude = Double.parseDouble(total1);
                                            double longitude = Double.parseDouble(total2);

                                            Geocoder geocoder;
                                            List<Address> addresses;
                                            geocoder = new Geocoder(Online.this, Locale.getDefault());

                                            String address = null;
                                            String city = null;
                                            String state = null;
                                            String country = null;
                                            String postalCode = null;
                                            String knonName = null;
                                            try {
                                                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                                address = addresses.get(0).getAddressLine(0);
                                                city = addresses.get(0).getLocality();
                                                state = addresses.get(0).getAdminArea();
                                                country = addresses.get(0).getCountryName();
                                                postalCode = addresses.get(0).getPostalCode();
                                                knonName = addresses.get(0).getFeatureName();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            LatLng latLng = new LatLng(Double.parseDouble(total1), Double.parseDouble(total2));
                                            mMap.addMarker(new MarkerOptions().position(latLng).title("Runner"));
                                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                                            textView.setText(address + city + state + country + postalCode + knonName);


                                        }
                                    }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(Online.this, "Database Error", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } else {

                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void sendOnChannel2() {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Orders");
        SharedPreferences settings1 = getSharedPreferences("TrackOrderNumber", Context.MODE_PRIVATE);
        String orderNumber1 = settings1.getString("orderNumber", "");

        Query checkUser1 = ref1.orderByChild("orderNumber").equalTo(orderNumber1);
        checkUser1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String status = dataSnapshot.child(orderNumber1).child("status").getValue(String.class);
                    if (status.equals("Accepted by Runner") && stopnotif1.equals("go")) {
                        stopnotif1 = "stop";
                        Notification notification = new NotificationCompat.Builder(Online.this, App.CHANNEL_2_ID)
                                .setSmallIcon(R.drawable.notifstatus)
                                .setContentTitle("Quickdel")
                                .setContentText(status)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .build();

                        notificationManager.notify(1, notification);
                    } else if (status.equals("Runner is in Pick up Location") && stopnotif2.equals("go")) {
                        stopnotif2 = "stop";
                        Notification notification = new NotificationCompat.Builder(Online.this, App.CHANNEL_2_ID)
                                .setSmallIcon(R.drawable.notifstatus)
                                .setContentTitle("Quickdel")
                                .setContentText(status)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .build();

                        notificationManager.notify(1, notification);
                    } else if (status.equals("Runner is in Destination Location") && stopnotif3.equals("go")) {
                        stopnotif3 = "stop";
                        Notification notification = new NotificationCompat.Builder(Online.this, App.CHANNEL_2_ID)
                                .setSmallIcon(R.drawable.notifstatus)
                                .setContentTitle("Quickdel")
                                .setContentText(status)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .build();

                        notificationManager.notify(1, notification);
                    } else if (status.equals("Delivered") && stopnotif4.equals("go")) {
                        stopnotif4 = "stop";
                        Notification notification = new NotificationCompat.Builder(Online.this, App.CHANNEL_2_ID)
                                .setSmallIcon(R.drawable.notifstatus)
                                .setContentTitle("Quickdel")
                                .setContentText(status)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .build();

                        notificationManager.notify(1, notification);
                    }

                } else {

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void newrunnerlocation() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders");

        SharedPreferences settings1 = getSharedPreferences("TrackOrderNumber", Context.MODE_PRIVATE);
        String orderNumber = settings1.getString("orderNumber", "");

        Query checkUser = ref.orderByChild("orderNumber").equalTo(orderNumber);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String runner = dataSnapshot.child(orderNumber).child("runnerID").getValue(String.class);
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Runner'sLocation");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                double dbLatitudeString = dataSnapshot.child(runner).child("l").child("0").getValue(double.class);
                                double dbLongitudeString = dataSnapshot.child(runner).child("l").child("1").getValue(double.class);

                                String total1 = String.valueOf(dbLatitudeString);
                                String total2 = String.valueOf(dbLongitudeString);
                                editTextLatitude.setText(total1);
                                editTextLongitude.setText(total2);
                                double latitude = Double.parseDouble(total1);
                                double longitude = Double.parseDouble(total2);

                                Geocoder geocoder;
                                List<Address> addresses;
                                geocoder = new Geocoder(Online.this, Locale.getDefault());

                                String address = null;
                                String city = null;
                                String state = null;
                                String country = null;
                                String postalCode = null;
                                String knonName = null;
                                try {
                                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                    address = addresses.get(0).getAddressLine(0);
                                    city = addresses.get(0).getLocality();
                                    state = addresses.get(0).getAdminArea();
                                    country = addresses.get(0).getCountryName();
                                    postalCode = addresses.get(0).getPostalCode();
                                    knonName = addresses.get(0).getFeatureName();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                LatLng latLng = new LatLng(Double.parseDouble(total1), Double.parseDouble(total2));
                                mMap.addMarker(new MarkerOptions().position(latLng).title("Runner"));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                                textView.setText(address + city + state + country + postalCode + knonName);


                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Online.this, "Database Error", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getrunnerlocation() {
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders");

//        SharedPreferences settings = getSharedPreferences("Runner", Context.MODE_PRIVATE);
//        String runner = settings.getString("runnerID", "");


        SharedPreferences settings1 = getSharedPreferences("OrderNumber", Context.MODE_PRIVATE);
        String orderNumber = settings1.getString("orderNumber", "");

        Query checkUser = ref.orderByChild("orderNumber").equalTo(orderNumber);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String runner = dataSnapshot.child("URkIP").child("runnerID").getValue(String.class);
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Runner'sLocation");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                double dbLatitudeString = dataSnapshot.child(runner).child("l").child("0").getValue(double.class);
                                double dbLongitudeString = dataSnapshot.child(runner).child("l").child("1").getValue(double.class);

                                String total1 = String.valueOf(dbLatitudeString);
                                String total2 = String.valueOf(dbLongitudeString);
                                editTextLatitude.setText(total1);
                                editTextLongitude.setText(total2);
                                editTextLatitude.setVisibility(View.INVISIBLE);
                                editTextLongitude.setVisibility(View.INVISIBLE);

                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Online.this, "Database Error", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//            geoFire.getLocation("Runner'sLocation", new LocationCallback() {
//                @Override
//                public void onLocationResult(String key, GeoLocation location) {
//                    if (location != null) {
//                        mMap.addMarker(new MarkerOptions().position(latLng).title("Runner"));
//                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
//                    } else {
//                        //When location is null
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    //LogDatabase error
//                }
//            });

//            reference = FirebaseDatabase.getInstance().getReference(Comon.DRIVERS_LOCATION_REFERENCES);
//            DatabaseReference onlineRef = FirebaseDatabase.getInstance().getReference().child(".info/connected");
//            driversLocationRef = FirebaseDatabase.getInstance().getReference(Comon.DRIVERS_LOCATION_REFERENCES);
//            currentUserRef = FirebaseDatabase.getInstance().getReference(Comon.DRIVERS_LOCATION_REFERENCES)
//                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//            reference.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//
//                        double dbLatitudeString = dataSnapshot.child(String.valueOf(Comon.currentUser)).child("l").child("0").getValue(double.class);
//                        double dbLongitudeString = dataSnapshot.child(String.valueOf(Comon.currentUser)).child("l").child("1").getValue(double.class);
//
//                        String total1 = String.valueOf(dbLatitudeString);
//                        String total2 = String.valueOf(dbLongitudeString);
//                        editTextLatitude.setText(total1);
//                        editTextLongitude.setText(total2);
//                        editTextLatitude.setVisibility(View.INVISIBLE);
//                        editTextLongitude.setVisibility(View.INVISIBLE);
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    //log database error
//                }
//            });


        if (!(editTextLongitude.getText().toString().isEmpty() || editTextLatitude.getText().toString().isEmpty())) {
            latitude = Double.parseDouble(editTextLatitude.getText().toString());
            longitude = Double.parseDouble(editTextLongitude.getText().toString());
            latLng = new LatLng(latitude, longitude);
        }
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        String address = null;
        String city = null;
        String state = null;
        String country = null;
        String postalCode = null;
        String knonName = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            address = addresses.get(0).getAddressLine(0);
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            postalCode = addresses.get(0).getPostalCode();
            knonName = addresses.get(0).getFeatureName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMap.addMarker(new MarkerOptions().position(latLng).title("Runner"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
        textView.setText(address + city + state + country + postalCode + knonName);


    }

    public void getLocationDetails(View view) {
        Online.ExampleThread thread = new Online.ExampleThread(1000);
        thread.start();
    }


    class ExampleThread extends Thread {
        int seconds;

        ExampleThread(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            myloop:
            for (final int[] i = {0}; i[0] < seconds; i[0]++) {
                try {
                    Thread.sleep(1000);

                    newrunnerlocation();
                    sendOnChannel2();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateButtonOnClick(View view) {

        databaseReference.child("latitude").push().setValue(editTextLatitude.getText().toString());
        databaseReference.child("longitude").push().setValue(editTextLongitude.getText().toString());

    }
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}