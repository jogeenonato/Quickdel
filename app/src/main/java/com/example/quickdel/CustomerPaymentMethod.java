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

import at.favre.lib.crypto.bcrypt.BCrypt;

public class CustomerPaymentMethod extends AppCompatActivity {
    ImageView back_btn;
    Button save;
    EditText cardName, cardNumber, cardMonth, cardYear,cardCVC;
    DatabaseReference customerDBRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_customer_payment_method);

        back_btn = findViewById(R.id.btn_back);
        cardName = findViewById(R.id.c_name);
        cardNumber = findViewById(R.id.c_cardno);
        cardMonth = findViewById(R.id.c_month);
        cardYear = findViewById(R.id.c_year);
        cardCVC = findViewById(R.id.c_cvc);
        save = findViewById(R.id.savecustomerpayment);

        customerDBRef = FirebaseDatabase.getInstance().getReference().child("users");


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { CustomerPaymentMethod.super.onBackPressed(); }
        });

        save.setOnClickListener(view -> savePaymentDetails());
    }

    private void savePaymentDetails(){
        String cName = cardName.getText().toString();
        String cNumber = cardNumber.getText().toString();
        String cMonth = cardMonth.getText().toString();
        String cYear = cardYear.getText().toString();
        String cCVC = cardCVC.getText().toString();
        String encryptCVC = BCrypt.withDefaults().hashToString(12, cCVC.toCharArray());
        String encryptCardNo = BCrypt.withDefaults().hashToString(12, cNumber.toCharArray());

        SharedPreferences settings1 = getSharedPreferences("Profile", Context.MODE_PRIVATE);
        String userName = settings1.getString("username", "");

       CustomerPaymentData customerPaymentData = new CustomerPaymentData(cName, encryptCardNo, cMonth, cYear, encryptCVC);

        customerDBRef.child(userName).child("PaymentMethod").setValue(customerPaymentData);
        Toast.makeText(CustomerPaymentMethod.this,"Card Details Successfully Saved", Toast.LENGTH_SHORT).show();
    }
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}