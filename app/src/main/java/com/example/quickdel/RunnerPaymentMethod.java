package com.example.quickdel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_runner_payment_method);

        back_btn = findViewById(R.id.btn_back);

        accName = findViewById(R.id.acc_name);
        accBSB = findViewById(R.id.acc_bsb);
        accNumber = findViewById(R.id.acc_number);
        save = findViewById(R.id.savepayment);

        runnerDBRef = FirebaseDatabase.getInstance().getReference().child("runners");

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

        runnerDBRef.child(userName).child("PaymentMethod").setValue(runnerPaymentData);
        Toast.makeText(RunnerPaymentMethod.this,"Bank Details Successfully Saved", Toast.LENGTH_SHORT).show();

    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}