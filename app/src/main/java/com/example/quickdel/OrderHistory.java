package com.example.quickdel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderHistory extends AppCompatActivity {
    ImageView back_btn;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    OrderAdapter orderAdapter;
    ArrayList<Orders> list;
    String currentuserID, orderUserID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_history);

        recyclerView = findViewById(R.id.orderhistory);
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<Orders>();
        orderAdapter = new OrderAdapter(this, list);
        recyclerView.setAdapter(orderAdapter);
        back_btn = findViewById(R.id.btn_back);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            //String name = user.getDisplayName();
            //String email = user.getEmail();

            // Check if user's email is verified
            //boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            currentuserID = user.getUid();
        }
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderHistory.super.onBackPressed();
            }
        });

        SharedPreferences settings = getSharedPreferences("Profile", Context.MODE_PRIVATE);
        String name1 = settings.getString("name", "");
        Query checkUser = databaseReference.orderByChild("userName").equalTo(name1);
     //  Query checkUser = databaseReference.child("orderNumber").orderByChild("userID").equalTo(currentuserID);
        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                orderUserID = snapshot.child("userID").getValue(String.class);
//
//                if (userID == orderUserID) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Orders orders = dataSnapshot.getValue(Orders.class);
                        list.add(orders);
                    }

                    orderAdapter.notifyDataSetChanged();
                }
//            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}