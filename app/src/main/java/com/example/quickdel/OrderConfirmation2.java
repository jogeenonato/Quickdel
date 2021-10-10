package com.example.quickdel;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class OrderConfirmation2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


   Button btnPay;
   ImageView back_btn;

    ImageView menuIcon;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    LinearLayout contentView;
    static final float END_SCALE = 0.7f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_confirmation2);

//        setupBackButton();
       btnPay = findViewById(R.id.btn_pay);
        back_btn = findViewById(R.id.btn_back);

        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.contentview);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);


        navigationDrawer();


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderConfirmation2.super.onBackPressed();
            }
        });


        Intent i = getIntent();
        String orderno = i.getStringExtra("ORDER");
        ((TextView)findViewById(R.id.tv_orderNo)).setText("Order #" + orderno);
        String pp = i.getStringExtra("PICKUP");
        ((TextView)findViewById(R.id.tv_pickup)).setText(pp);
        String dp = i.getStringExtra("DESTINATION");
        ((TextView)findViewById(R.id.tv_destination)).setText(dp);
        String ds = i.getStringExtra("DISTANCE");
        ((TextView)findViewById(R.id.tv_distance)).setText(ds);
        String distancePrice = i.getStringExtra("DPRICE");
        ((TextView)findViewById(R.id.tv_distancePrice)).setText(distancePrice);
        String recipientName = i.getStringExtra("RECIPIENT");
        ((TextView)findViewById(R.id.tv_recipient)).setText(recipientName);
        String recipientNo = i.getStringExtra("RECIPIENTNO");
        ((TextView)findViewById(R.id.tv_recipientNo)).setText(recipientNo);
        String vehicle = i.getStringExtra("VEHICLE");
        ((TextView)findViewById(R.id.tv_vehicle)).setText(vehicle);
        String vehiclePrice = i.getStringExtra("VPRICE");
        ((TextView)findViewById(R.id.tv_vehiclePrice)).setText(vehiclePrice);
        String size = i.getStringExtra("SIZE");
        ((TextView)findViewById(R.id.tv_pSize)).setText(size);
        String sizePrice = i.getStringExtra("SPRICE");
        ((TextView)findViewById(R.id.tv_pSizePrice)).setText(sizePrice);
        String weight = i.getStringExtra("WEIGHT");
        ((TextView)findViewById(R.id.tv_pWeight)).setText(weight);
        String weightPrice = i.getStringExtra("WPRICE");
        ((TextView)findViewById(R.id.tv_pWeightPrice)).setText(weightPrice);
        String dc = i.getStringExtra("DESC");
        ((TextView)findViewById(R.id.tv_description)).setText(dc);
        String total = i.getStringExtra("TOTAL");
        ((TextView)findViewById(R.id.tv_total)).setText(total);


    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {
        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.setScrimColor(getResources().getColor(R.color.card4));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
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



    public void gotoPayments(View view) {
        showDialog();
        //Intent intent = new Intent(this, Payment.class);
        //startActivity(intent);
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
           super.onBackPressed();
        }

    }
    private void showDialog() {
        Intent i = getIntent();
        String recipientName = i.getStringExtra("RECIPIENT");
        String total = i.getStringExtra("TOTAL");
        String pp = i.getStringExtra("PICKUP");
        String dp = i.getStringExtra("DESTINATION");


        SharedPreferences settings = getSharedPreferences("Profile", Context.MODE_PRIVATE);
        String name1 = settings.getString("name", "");

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.payment_dialog);

        TextView pricetag = dialog.findViewById(R.id.price);
        TextView recipient = dialog.findViewById(R.id.name);
        TextView sender = dialog.findViewById(R.id.name2);
        TextView pickup = dialog.findViewById(R.id.address2);
        TextView destination = dialog.findViewById(R.id.address);
        Button btn_payment = dialog.findViewById(R.id.pay_with_card_button);

        pricetag.setText("$" + total);
        recipient.setText(recipientName);
        sender.setText(name1);
        pickup.setText(pp);
        destination.setText(dp);

        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderConfirmation2.this, Payment.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });




        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

//    private void setupBackButton() {
//        TextView bck = findViewById(R.id.back);
//        bck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//    }




    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_profile:
                startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.nav_logout:
                startActivity(new Intent(getApplicationContext(), LoginActivity2.class));
                finish();
                break;
            case R.id.nav_track:
                startActivity(new Intent(getApplicationContext(), TrackList.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.nav_history:
                startActivity(new Intent(getApplicationContext(), OrderHistory.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                break;
            case R.id.nav_home:
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.nav_place:
                startActivity(new Intent(getApplicationContext(), PlaceQuickdelActivity2.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.nav_settings:
                startActivity(new Intent(getApplicationContext(), CustomerSettingsActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
        return false;
    }
}
