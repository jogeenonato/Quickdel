package com.example.quickdel;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.quickdel.databinding.ActivityDriverHomeBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DriverHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static final float END_SCALE = 0.7f;



    //carlo code
    private NotificationManagerCompat notificationManager;
    ImageView menuIcon_r;
    DrawerLayout drawerLayout_r;
    NavigationView navigationView;
    LinearLayout contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_driver_home);

//        setSupportActionBar(binding.appBarDriverHome.toolbar);

//        DrawerLayout drawer = binding.drawerLayout;
//        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
//                .setOpenableLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_driver_home);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);


        //Carlo code
        menuIcon_r = findViewById(R.id.menu_icon_r);
        contentView = findViewById(R.id.contentview_r);
        drawerLayout_r = findViewById(R.id.drawer_layout_r);
        navigationView = findViewById(R.id.navigation_view_r);
        navigationDrawer();
        notificationManager = NotificationManagerCompat.from(this);
        lookForQuickdel();

    }
    @Override
    public void onBackPressed() {

        startActivity(new Intent(getApplicationContext(), DriverHomeActivity.class));

    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home_r);

        menuIcon_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout_r.isDrawerVisible(GravityCompat.START))
                    drawerLayout_r.closeDrawer(GravityCompat.START);
                else drawerLayout_r.openDrawer(GravityCompat.START);

            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {
        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout_r.setScrimColor(getResources().getColor(R.color.card4));
        drawerLayout_r.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

    private void lookForQuickdel() {
        ExampleThread thread = new ExampleThread(1000);
        thread.start();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_profile_r:
                startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.nav_logout:
                startActivity(new Intent(getApplicationContext(), LoginActivity2.class));
                finish();
                break;
            case R.id.nav_runners_home:
                startActivity(new Intent(getApplicationContext(), RunnersHome.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            case R.id.nav_home_r:
                if (drawerLayout_r.isDrawerVisible(GravityCompat.START))
                    drawerLayout_r.closeDrawer(GravityCompat.START);
                else drawerLayout_r.openDrawer(GravityCompat.START);
                break;
            case R.id.nav_contact:
                startActivity(new Intent(getApplicationContext(), test.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.nav_FAQ:
                startActivity(new Intent(getApplicationContext(), Faq.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.nav_terms:
                startActivity(new Intent(getApplicationContext(), Conditions.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.nav_preferences:
                startActivity(new Intent(getApplicationContext(), RunnerPreferences.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.nav_payment_method:
                startActivity(new Intent(getApplicationContext(), RunnerPaymentMethod.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.nav_earnings:
                startActivity(new Intent(getApplicationContext(), Earnings.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
        return false;
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
                    Query checkUser = reference.orderByChild("status").equalTo("Paid");
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
                                    float runnerEarnings = snapshot.child("runnerEarnings").getValue(float.class);


                                    SharedPreferences settings = getSharedPreferences("Order", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString("orderNumber", orderNumber);
                                    editor.putString("pickupPoint", pickupPoint);
                                    editor.putString("recipient", recipient);
                                    editor.putString("destinationPoint", destinationPoint);
                                    editor.putString("sender", sender);
                                    editor.putFloat("total", total);
                                    editor.putFloat("runnerEarnings", runnerEarnings);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.driver_home, menu);
//        return true;
//    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_driver_home);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}