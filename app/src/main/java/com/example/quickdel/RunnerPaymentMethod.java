package com.example.quickdel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RunnerPaymentMethod extends AppCompatActivity {

    Button save;
    EditText accName, accBSB, accNumber;
    DatabaseReference runnerDBRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runner_payment_method);

        accName = findViewById(R.id.acc_name);
        accBSB = findViewById(R.id.acc_bsb);
        accNumber = findViewById(R.id.acc_number);

        runnerDBRef = FirebaseDatabase.getInstance().getReference().child("runners");




        save.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               savePaymentDetails();
;           }
        });
    }

    private void savePaymentDetails(){
        String accountName = accName.getText().toString();
        String accountBSB = accBSB.getText().toString();
        String accountNo = accNumber.getText().toString();


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

            }
        });
    }

}