package com.example.quickdel;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.quickdel.databinding.ActivityTrackRunnerBinding;
import com.google.android.gms.location.LocationListener;
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
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class TrackRunner extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityTrackRunnerBinding binding;
    private DatabaseReference databaseReference;

    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    String lt, lng;
    DatabaseReference onlineRef,currentUserRef,driversLocationRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTrackRunnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        databaseReference = FirebaseDatabase.getInstance().getReference("Runner'sLocation");
        onlineRef = FirebaseDatabase.getInstance().getReference().child(".info/connected");
        driversLocationRef = FirebaseDatabase.getInstance().getReference(Comon.DRIVERS_LOCATION_REFERENCES);
        currentUserRef = FirebaseDatabase.getInstance().getReference(Comon.DRIVERS_LOCATION_REFERENCES)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    String dbLatitudeString;
                    dbLatitudeString = dataSnapshot.child(String.valueOf(currentUserRef)).child("l").child("0").getValue().toString().substring(1, dataSnapshot.child(String.valueOf(currentUserRef)).child("l").child("0").getValue().toString().length() - 1);
                    String dbLongitudeString = dataSnapshot.child(String.valueOf(currentUserRef)).child("l").child("1").getValue().toString().substring(1, dataSnapshot.child(String.valueOf(currentUserRef)).child("l").child("1").getValue().toString().length() - 1);

                    String[] stringLat = dbLatitudeString.split(",");
                    Arrays.sort(stringLat);
                    String latitude = stringLat[stringLat.length - 1].split("=")[1];


                    String[] stringLng = dbLongitudeString.split(",");
                    Arrays.sort(stringLng);
                    String longitude = stringLat[stringLng.length - 1].split("=")[1];


                    LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                    mMap.addMarker(new MarkerOptions().position(latLng).title("Runner's Current Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


                }
                catch (Exception e){
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

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//        @Override
//        public void onLocationResult(@NonNull LocationResult locationResult) {
//            super.onLocationResult(locationResult);
//
//            LatLng newPosition = new LatLng(locationResult.getLastLocation().getLatitude(),
//                    locationResult.getLastLocation().getLongitude());
//
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPosition, 12));

        locationListener = location -> {

            try {
                lt = String.valueOf(location.getLatitude());
                lng = String.valueOf(location.getLongitude());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, (android.location.LocationListener) locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, (android.location.LocationListener) locationListener);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

//    public void updateButtonOnclick(View view){
//
//    }

}