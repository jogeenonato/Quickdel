package com.example.quickdel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class TrackList extends AppCompatActivity implements OrderAdapter.OnNoteListener {
    ImageView back_btn, btn_refresh;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    OrderAdapter orderAdapter;
    ArrayList<Orders> list;
    String currentuserID, orderUserID;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_history);

        recyclerView = findViewById(R.id.orderhistory);
        title = findViewById(R.id.full_name_top);
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btn_refresh = findViewById(R.id.menu_icon);
        list = new ArrayList<Orders>();
        orderAdapter = new OrderAdapter(list, this);
        recyclerView.setAdapter(orderAdapter);
        back_btn = findViewById(R.id.btn_back);
        title.setText("Track List");
        btn_refresh.setImageResource(R.drawable.ic_baseline_refresh_24);

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
//                TrackList.super.onBackPressed();
                Intent intent1 = new Intent(TrackList.this, UsersHome2.class);
                startActivity(intent1);
            }
        });
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                getDataList();
            }
        });
        list.clear();
        getDataList();

    }

    private void getDataList() {
        SharedPreferences settings = getSharedPreferences("Profile", Context.MODE_PRIVATE);
        String name1 = settings.getString("name", "");
        Query checkUser = databaseReference.orderByChild("userName").equalTo(name1);
        //  Query checkUser = databaseReference.child("orderNumber").orderByChild("userID").equalTo(currentuserID);
        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Orders orders = dataSnapshot.getValue(Orders.class);
                    if (orders.getStatus().equals("Delivered")) {

                    } else{
                        list.add(orders);
                    }
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

    @Override
    public void onNoteclick(int position) {
        String data = list.get(position).getStatus();
        String data1 = list.get(position).getOrderNumber();


        if (!data.equals("Delivered") && !data.equals("Paid") && !data.equals("Placed") && !data.equals("")) {
            SharedPreferences settings = getSharedPreferences("TrackOrderNumber", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("orderNumber", data1);
            editor.apply();
            Intent intent = new Intent(this, Online.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            Toast.makeText(TrackList.this, "Please wait for a Runner to accept your quickdel.", Toast.LENGTH_SHORT).show();
        }
    }
}