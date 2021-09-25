package com.example.quickdel.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickdel.Comon;
import com.example.quickdel.R;
import com.example.quickdel.databinding.FragmentHomeBinding;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    //location
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;


    SupportMapFragment mapFragment;

    //Online System
    DatabaseReference onlineRef,currentUserRef,driversLocationRef;
    GeoFire geoFire;


    ValueEventListener onlineValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists())
                currentUserRef.onDisconnect().removeValue();

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Snackbar.make(mapFragment.getView(),databaseError.getMessage(),Snackbar.LENGTH_LONG)
                    .show();

        }
    };

    @Override
    public void onDestroy() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        geoFire.removeLocation(FirebaseAuth.getInstance().getCurrentUser().getUid());
        onlineRef.removeEventListener(onlineValueEventListener);
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
        registerOnlineSystem();
    }

    private void registerOnlineSystem() {
        onlineRef.addValueEventListener(onlineValueEventListener);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return root;
    }


    @SuppressLint("MissingPermission")
    private void init() {

        onlineRef = FirebaseDatabase.getInstance().getReference().child(".info/connected");
        driversLocationRef = FirebaseDatabase.getInstance().getReference(Comon.DRIVERS_LOCATION_REFERENCES);
        currentUserRef = FirebaseDatabase.getInstance().getReference(Comon.DRIVERS_LOCATION_REFERENCES)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        geoFire = new GeoFire(driversLocationRef);


        registerOnlineSystem();

        //locationRequest = new LocationRequest();
        //locationRequest.setSmallestDisplacement(10f);
        //locationRequest.setInterval(5000);
        //locationRequest.setFastestInterval(3000);
        //locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationRequest = LocationRequest.create()
                .setInterval(300)
                .setSmallestDisplacement(10f)
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setMaxWaitTime(300);
        locationCallback  = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                LatLng newPosition = new LatLng(locationResult.getLastLocation().getLatitude(),
                        locationResult.getLastLocation().getLongitude());

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPosition, 12));

                //Update Location

                geoFire.setLocation(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                        new GeoLocation(locationResult.getLastLocation().getLatitude(),
                                locationResult.getLastLocation().getLongitude()),
                        (key, error) -> {
                            if (error != null)
                                Snackbar.make(mapFragment.getView(),error.getMessage(),Snackbar.LENGTH_LONG)
                                .show();
                            //else
                                //Snackbar.make(mapFragment.getView(),"You are online",Snackbar.LENGTH_LONG).show();

                        });
            }
        };
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,Looper.myLooper());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        //Check Permission
        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new PermissionListener() {

                    @SuppressLint("MissingPermission")
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        mMap.setMyLocationEnabled(true);
                        mMap.getUiSettings().setMyLocationButtonEnabled(true);
                        mMap.setOnMyLocationButtonClickListener(() -> {
                            fusedLocationProviderClient.getLastLocation()
                                    .addOnFailureListener(e -> Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show())
                                    .addOnSuccessListener(location -> {
                                        LatLng userLatLng = new LatLng(location.getLatitude(),location.getLongitude());
                                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng,18f));

                                    });
                            return true;
                        });


                        //Set Layout Button
                        //It crashes whe I add this;
                        /*View locationButton = ((View)mapFragment.getView().findViewById(Integer.parseInt("1"))
                        .getParent())
                                .findViewById(Integer.parseInt("2"));
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
                        //Right Button
                        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
                        params.setMargins(0,0,0,50);*/


                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(getContext(), "Permission"+permissionDeniedResponse.getPermissionName()+"" +
                                " was denied!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();



        try {
            boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.uber_maps_style));
            if (!success)
                Log.e("EDMT_ERROR", "Style parsing error");
        }catch (Resources.NotFoundException e){
            Log.e("EDMT_ERROR", e.getMessage());
        }


    }
}