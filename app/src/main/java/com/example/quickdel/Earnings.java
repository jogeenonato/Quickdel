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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Earnings extends AppCompatActivity implements EarningsAdapter.OnNoteListener {
    ImageView back_btn;
    RecyclerView recyclerView;
    DatabaseReference database;
    EarningsAdapter earningsAdapter;
    ArrayList<Orders> list1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_earnings);

        back_btn = findViewById(R.id.btn_back);
        recyclerView = findViewById(R.id.earnings);
        database = FirebaseDatabase.getInstance().getReference("Orders");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list1 = new ArrayList<>();
        earningsAdapter = new EarningsAdapter(list1, this);
        recyclerView.setAdapter(earningsAdapter);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Earnings.super.onBackPressed();
            }
        });


        SharedPreferences settings1 = getSharedPreferences("Runner", Context.MODE_PRIVATE);
        String runnerUID = settings1.getString("runnerID", "");
        Query checkUser = database.orderByChild("runnerID").equalTo(runnerUID);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Orders orders = dataSnapshot1.getValue(Orders.class);
                        list1.add(orders);
                    }
                    earningsAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    @Override
    public void onNoteClick(int position) {
//      String data = String.valueOf(list1.get(position).getOrderNumber());
//      Toast.makeText(Earnings.this,  data, Toast.LENGTH_SHORT).show();
//


    }
    public void finish () {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
