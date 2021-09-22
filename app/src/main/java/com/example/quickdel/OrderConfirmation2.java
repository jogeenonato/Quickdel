package com.example.quickdel;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderConfirmation2 extends AppCompatActivity {


   Button btnPay;
//    Orders orders;
//    RadioButton bike, sedan, ute, small, medium, large, weight1, weight2, weight3, weight4;
//    FirebaseDatabase database;
//    DatabaseReference reference;
//    int i = 0;
//    EditText etPickup, etDestination, etDesc;
//    TextView tvDistance;
//    String sType;
//    int flag = 0;
//    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation2);

        setupBackButton();
       btnPay = findViewById(R.id.btn_pay);


//        orders = new Orders()  ;
//        bike = findViewById(R.id.v_bike);
//        sedan = findViewById(R.id.v_sedan);
//        ute = findViewById(R.id.v_ute);
//        small = findViewById(R.id.s_small);
//        medium = findViewById(R.id.s_medium);
//        large = findViewById(R.id.s_large);
//        weight1 = findViewById(R.id.weigh1);
//        weight2 = findViewById(R.id.weight2);
//        weight3 = findViewById(R.id.weight3);
//        weight4 = findViewById(R.id.weight4);
//        etPickup = findViewById(R.id.pickup);
//        etDestination = findViewById(R.id.destination);
//        tvDistance = findViewById(R.id.text_distance);
//        etDesc  = findViewById(R.id.et_description);
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            // Name, email address, and profile photo Url
//            //String name = user.getDisplayName();
//            //String email = user.getEmail();
//
//            // Check if user's email is verified
//            //boolean emailVerified = user.isEmailVerified();
//
//            // The user's ID, unique to the Firebase project. Do NOT use this value to
//            // authenticate with your backend server, if you have one. Use
//            // FirebaseUser.getIdToken() instead.
//            uid = user.getUid();
//        }
//
//        reference = database.getInstance().getReference().child("Orders");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//                    i = (int) dataSnapshot.getChildrenCount();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        btnPay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String v1 = bike.getText().toString();
//                String v2 = sedan.getText().toString();
//                String v3 = ute.getText().toString();
//                String s1 = small.getText().toString();
//                String s2 = medium.getText().toString();
//                String s3 = large.getText().toString();
//                String w1 = weight1.getText().toString();
//                String w2 = weight2.getText().toString();
//                String w3 = weight3.getText().toString();
//                String w4 = weight4.getText().toString();
//                String pp = etPickup.getText().toString();
//                String dp = etDestination.getText().toString();
//                String dc = etDesc.getText().toString();
//
//                if (bike.isChecked()){
//                    orders.setVehicle(v1);
//                    orders.setVehiclePrice(10.00);
//                    reference.child(String.valueOf(i+1)).setValue(orders);
//                } else if (sedan.isChecked()){
//                    orders.setVehicle(v2);
//                    orders.setVehiclePrice(12.00);
//                    reference.child(String.valueOf(i+1)).setValue(orders);
//                } else {
//                    orders.setVehicle(v3);
//                    orders.setVehiclePrice(14.00);
//                    reference.child(String.valueOf(i+1)).setValue(orders);
//                }
//
//                if (small.isChecked()){
//                    orders.setSize(s1);
//                    orders.setSizePrice(0.50);
//                    reference.child(String.valueOf(i+1)).setValue(orders);
//                } else if (medium.isChecked()){
//                    orders.setSize(s2);
//                    orders.setSizePrice(0.60);
//                    reference.child(String.valueOf(i+1)).setValue(orders);
//                } else {
//                    orders.setSize(s3);
//                    orders.setSizePrice(0.80);
//                    reference.child(String.valueOf(i+1)).setValue(orders);
//                }
//
//                if (weight1.isChecked()){
//                    orders.setWeight(w1);
//                    orders.setWeightPrice(2.00);
//                    reference.child(String.valueOf(i+1)).setValue(orders);
//                } else if (weight2.isChecked()){
//                    orders.setWeight(w2);
//                    orders.setWeightPrice(3.00);
//                    reference.child(String.valueOf(i+1)).setValue(orders);
//                } else if (weight3.isChecked()) {
//                    orders.setWeight(w3);
//                    orders.setWeightPrice(4.00);
//                    reference.child(String.valueOf(i + 1)).setValue(orders);
//                } else {
//                    orders.setWeight(w4);
//                    orders.setWeightPrice(5.00);
//                    reference.child(String.valueOf(i+1)).setValue(orders);
//                }
//
//
//                orders.setDescription(dc);
//                reference.child(String.valueOf(i+1)).setValue(orders);
//
//                orders.setUserID(uid);
//                reference.child(String.valueOf(i+1)).setValue(orders);
//
//                orders.setPickupPoint(pp);
//                reference.child(String.valueOf(i+1)).setValue(orders);
//
//                orders.setDestinationPoint(dp);
//                reference.child(String.valueOf(i+1)).setValue(orders);
//
//                orders.setTotal(orders.getWeightPrice()+orders.getSizePrice()+ orders.getVehiclePrice());
//                reference.child(String.valueOf(i+1)).setValue(orders);
//
//
//            }
//        });


        Intent i = getIntent();
        String orderno = i.getStringExtra("ORDER");
        ((TextView)findViewById(R.id.tv_orderNo)).setText(orderno);
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

    public void gotoPayments(View view) {
        showDialog();
        //Intent intent = new Intent(this, Payment.class);
        //startActivity(intent);
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
