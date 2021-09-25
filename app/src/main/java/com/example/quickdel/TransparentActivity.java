package com.example.quickdel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TransparentActivity extends AppCompatActivity {

    //public EditText recipient;
    //public Button accept;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_transparent);
        showDialog();


        /*recipient = findViewById(R.id.address2);
        accept = findViewById(R.id.btn_acceptar);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String destinationLocation = recipient.getText().toString();

                Intent intent = new Intent(TransparentActivity.this,Track.class);
                intent.putExtra("keydestination",destinationLocation);
                startActivity(intent);
            }
        });*/


    }



    private void showDialog() {
        SharedPreferences settings = getSharedPreferences("Order", Context.MODE_PRIVATE);
        String orderNumber = settings.getString("orderNumber", "");
        String pickupPoint = settings.getString("pickupPoint", "");
        String recipient = settings.getString("recipient", "");
        String destinationPoint = settings.getString("destinationPoint", "");
        float total = settings.getFloat("total", -1);
        String total1 = String.valueOf(total);

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.acceptquickdel_dialog);



        TextView orderNo = dialog.findViewById(R.id.orderNumber);
        TextView sender = dialog.findViewById(R.id.name2);
        TextView pickup = dialog.findViewById(R.id.address2);
        TextView recipientname = dialog.findViewById(R.id.name);
        TextView destination = dialog.findViewById(R.id.address);
        TextView pricetag = dialog.findViewById(R.id.price);



        Button btn_accept = dialog.findViewById(R.id.btn_acceptar);

        orderNo.setText("New Quickdel: " + orderNumber);
        pickup.setText(pickupPoint);
        recipientname.setText(recipient);
        destination.setText(destinationPoint);
        pricetag.setText("$" + total1);

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TransparentActivity.this, Track.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });





        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setOnCancelListener(
                new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        TransparentActivity.this.finish();
                    }
                }
        );

    }

}