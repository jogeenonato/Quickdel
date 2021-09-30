package com.example.quickdel;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.quickdel.databinding.ActivityTrackRunnerBinding;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrackRunner extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityTrackRunnerBinding binding;
    DatabaseReference databaseReference;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    private LatLng latLng;
    private LocationRequest locationRequest;
    private com.google.android.gms.location.LocationCallback locationCallback;


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


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;


//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(@NonNull Location location) {
//                try {
//                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                    mMap.addMarker(new MarkerOptions().position(latLng).title("Current Position"));
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        try {
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }


        databaseReference = FirebaseDatabase.getInstance().getReference(Comon.DRIVERS_LOCATION_REFERENCES);
        GeoFire geoFire = new GeoFire(databaseReference);
//        String runner = "";
//        double lat = -32.9579715;
//        double lng = 150.1563683;
//        double radius = 10;
//        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(lat,lng), radius);
//                geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
//                    @Override
//                    public void onKeyEntered(String key, GeoLocation location) {
////                        Log.d(Const.TAG, String.format("Key %s entered the search area at [%f,%f]", key, location.latitude, location.longitude));
//                        String runner = key;
//                        double lat = location.latitude;
//                        double lng = location.longitude;
//                        latLng = new LatLng(lat, lng);
//                        mMap.addMarker(new MarkerOptions().position(latLng).title("Current Position"));
//                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
//                    }
//
//                    @Override
//                    public void onKeyExited(String key) {
////                        Log.d(Const.TAG,String.format("Key %s is no longer in the search area", key));
//                    }
//
//                    @Override
//                    public void onKeyMoved(String key, GeoLocation location) {
////                        Log.d(Const.TAG,String.format("Key %s moved within the search area to [%f,%f]", key, location.latitude, location.longitude));
//                        String runner = key;
//                        double lat = location.latitude;
//                        double lng = location.longitude;
//                        latLng = new LatLng(lat, lng);
//                        mMap.addMarker(new MarkerOptions().position(latLng).title("Current Position"));
//                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
//                    }
//
//                    @Override
//                    public void onGeoQueryReady() {
////                        Log.d(Const.TAG,"All initial data has been loaded and events have been fired!");
//                    }
//
//                    @Override
//                    public void onGeoQueryError(DatabaseError error) {
////                        Log.d(Const.TAG,"There was an error with this query: " + error);
//                    }
//                });
        geoFire.getLocation(String.valueOf(Comon.currentUser), new LocationCallback() {
            @Override
            public void onLocationResult(String key, GeoLocation location) {
                double lat =location.latitude;
                double lng =location.longitude;
                LatLng sydney = new LatLng(lng, lat);
                mMap.addMarker(new MarkerOptions().position(sydney).title("car"));
                Toast.makeText(getApplicationContext(),""+sydney,Toast.LENGTH_SHORT).show();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(9));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

    }

}

