package com.example.quickdel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RunnerPaymentMethod extends AppCompatActivity {
    ImageView back_btn;
    Button save;
    EditText accName, accBSB, accNumber;
    DatabaseReference runnerDBRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runner_payment_method);

        back_btn = findViewById(R.id.btn_back);

        accName = findViewById(R.id.acc_name);
        accBSB = findViewById(R.id.acc_bsb);
        accNumber = findViewById(R.id.acc_number);
        save = findViewById(R.id.savepayment);

        runnerDBRef = FirebaseDatabase.getInstance().getReference().child("runners");


        save.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               savePaymentDetails();
;           }
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { RunnerPaymentMethod.super.onBackPressed(); }
        });



        save.setOnClickListener(view -> savePaymentDetails());
    }

    private void savePaymentDetails(){
        String accountName = accName.getText().toString();
        String accountBSB = accBSB.getText().toString();
        String accountNo = accNumber.getText().toString();

        SharedPreferences settings1 = getSharedPreferences("Profile", Context.MODE_PRIVATE);
        String userName = settings1.getString("username", "");

        RunnerPaymentData runnerPaymentData = new RunnerPaymentData(accountName, accountBSB, accountNo);

        SharedPreferences settings = getSharedPreferences("Profile", Context.MODE_PRIVATE);
        String runner = settings.getString("name", "");
        Query checkUser = runnerDBRef.orderByChild("userName").equalTo(runner);
        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                runnerDBRef.child("Payment").setValue(runnerPaymentData);
                Toast.makeText(RunnerPaymentMethod.this,"Bank Details Successfully Saved", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
        runnerDBRef.child(userName).child("PaymentMethod").setValue(runnerPaymentData);
        Toast.makeText(RunnerPaymentMethod.this,"Bank Details Successfully Saved", Toast.LENGTH_SHORT).show();

    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}