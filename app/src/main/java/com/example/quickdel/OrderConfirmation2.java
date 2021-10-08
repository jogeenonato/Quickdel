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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderConfirmation2 extends AppCompatActivity {


   Button btnPay;
   ImageView back_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_confirmation2);

//        setupBackButton();
       btnPay = findViewById(R.id.btn_pay);
        back_btn = findViewById(R.id.btn_back);

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

}
