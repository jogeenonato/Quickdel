package com.example.quickdel;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.WindowManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickdel.databinding.ActivityDriverHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Driver;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class DriverHomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDriverHomeBinding binding;


    //carlo code
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityDriverHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarDriverHome.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_driver_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        //Carlo code
        notificationManager = NotificationManagerCompat.from(this);
        lookForQuickdel();
    }

    private void lookForQuickdel() {
        ExampleThread thread = new ExampleThread(1000);
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
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Orders");
                    Query checkUser = reference.orderByChild("status").equalTo("paid");
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    String orderNumber = snapshot.child("orderNumber").getValue(String.class);
                                    String pickupPoint = snapshot.child("pickupPoint").getValue(String.class);
                                    String recipient = snapshot.child("recipient").getValue(String.class);
                                    String destinationPoint = snapshot.child("destinationPoint").getValue(String.class);
                                    String sender = snapshot.child("userName").getValue(String.class);
                                    float total = snapshot.child("total").getValue(float.class);


                                    SharedPreferences settings = getSharedPreferences("Order", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString("orderNumber", orderNumber);
                                    editor.putString("pickupPoint", pickupPoint);
                                    editor.putString("recipient", recipient);
                                    editor.putString("destinationPoint", destinationPoint);
                                    editor.putString("sender", sender);
                                    editor.putFloat("total", total);
                                    editor.apply();
                                }
                                Intent notifyIntent = new Intent(DriverHomeActivity.this, TransparentActivity.class);
                                notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                //UNIQUE_ID if you expect more than one notification to appear
                                PendingIntent intent = PendingIntent.getActivity(DriverHomeActivity.this, 0,
                                        notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                                Notification notification = new NotificationCompat.Builder(DriverHomeActivity.this, App.CHANNEL_1_ID)
                                        .setSmallIcon(R.drawable.ic_baseline_delivery_dining_24)
                                        .setContentTitle("Quickdel")
                                        .setContentText("You have a new quickdel!")
                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                        .setContentIntent(intent)
                                        .setAutoCancel(true)
                                        .build();

                                notificationManager.notify(1, notification);

                                i[0] = seconds;

                                //currentThread().interrupt();
                            } else {

                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(i[0] == seconds) {
                    break myloop;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.driver_home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_driver_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}