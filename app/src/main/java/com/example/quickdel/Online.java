package com.example.quickdel;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Online extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference databaseReference, ref;
    GeoFire geoFire;

    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    private LatLng latLng;
    String assignedRunner;
    private LocationRequest locationRequest;
    private com.google.android.gms.location.LocationCallback locationCallback;


    private EditText editTextLatitude;
    private EditText editTextLongitude;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        editTextLatitude = findViewById(R.id.editTextTextPersonName2);
        editTextLongitude = findViewById(R.id.editTextTextPersonName3);
        textView = findViewById(R.id.LocationDisplay);

        editTextLatitude.setText("-33.77557");
        editTextLongitude.setText("150.917485");
        editTextLatitude.setVisibility(View.INVISIBLE);
        editTextLongitude.setVisibility(View.INVISIBLE);


        databaseReference = FirebaseDatabase.getInstance().getReference("Location");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    String databaseLatitudeString = dataSnapshot.child("latitude").getValue().toString().substring(1, dataSnapshot.child("latitude").getValue().toString().length() - 1);
                    String databaseLongitudeString = dataSnapshot.child("longitude").getValue().toString().substring(1, dataSnapshot.child("longitude").getValue().toString().length() - 1);

                    String[] stringLat = databaseLatitudeString.split(", ");
                    Arrays.sort(stringLat);
                    String latitude = stringLat[stringLat.length - 1].split("=")[1];

                    String[] stringLong = databaseLongitudeString.split(", ");
                    Arrays.sort(stringLong);
                    String longitude = stringLat[stringLong.length - 1].split("=")[1];

                    LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                    mMap.addMarker(new MarkerOptions().position(latLng).title(latitude + " , " + longitude));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

        //Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location){

                try {


                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Current Position"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            };

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
        }


        public void getLocationDetails (View view){
            double latitude = latLng.latitude;
            double longitude = latLng.longitude;
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Runner'sLocation");
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders");
            SharedPreferences settings = getSharedPreferences("Order", Context.MODE_PRIVATE);
            String orderNumber = settings.getString("orderNumber", "");
            // Query checkUser = reference.orderByChild("g").equalTo("r3grf5qg03");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    assignedRunner = snapshot.child(orderNumber).child("runnerID").getValue().toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
//                        for(DataSnapshot item: dataSnapshot.getChildren()){
//                            if (!dataSnapshot.hasChildren()) {
//                                return;
//                            }
//                            for(DataSnapshot childSnapShot :item.getChildren()){
//                                final String runner = childSnapShot.getKey();
//                                final DataSnapshot parentSnapShot = childSnapShot.child(runner);
//                                if (!parentSnapShot.hasChildren()) {
//                                    continue;
//                                }
                        geoFire.getLocation("Runner'sLocation", new LocationCallback(){
                            @Override
                            public void onLocationResult(String key, GeoLocation location) {
                                if (location != null){
                                    Double dblatitude = location.latitude;
                                    Double dblongitude = location.longitude;

                                    String total1 = String.valueOf(dblatitude);
                                    String total2 = String.valueOf(dblongitude);
                                    editTextLatitude.setText(total1);
                                    editTextLongitude.setText(total2);
                                    editTextLatitude.setVisibility(View.INVISIBLE);
                                    editTextLongitude.setVisibility(View.INVISIBLE);

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(Online.this, "No Location Detected", Toast.LENGTH_SHORT).show();
                            }
                        });

//                                double dbLatitudeString = dataSnapshot.child(assignedRunner).child("l").child("0").getValue(double.class);
//                                double dbLongitudeString = dataSnapshot.child(assignedRunner).child("l").child("1").getValue(double.class);

//                            }
//                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Online.this, "Database Error", Toast.LENGTH_SHORT).show();
                }
            });

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
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in : " + address + city + state + country + postalCode + knonName));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
            textView.setText(address + city + state + country + postalCode + knonName);


        }
        public void updateButtonOnClick (View view){

            databaseReference.child("latitude").push().setValue(editTextLatitude.getText().toString());
            databaseReference.child("longitude").push().setValue(editTextLongitude.getText().toString());

        }
    }
