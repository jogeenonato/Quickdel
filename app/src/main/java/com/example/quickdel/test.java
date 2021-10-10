package com.example.quickdel;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class test extends AppCompatActivity {
    EditText etPhone, etMessage;
    Button btSend;
    ImageView back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_support);

        etPhone = findViewById(R.id.et_phone);
        etMessage = findViewById(R.id.et_message);
        btSend = findViewById(R.id.bt_send);
        back_btn = findViewById(R.id.btn_back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test.super.onBackPressed();
            }
        });
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(test.this
                        , Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED) {

                    sendMessage();
                } else {
                    ActivityCompat.requestPermissions(test.this
                            , new String[]{Manifest.permission.SEND_SMS},
                            100);
                }
            }
        });
    }

    private void sendMessage() {

        String sPhone = etPhone.getText().toString().trim();
        String sMessage = etMessage.getText().toString().trim();

        if (!sPhone.equals("") && !sMessage.equals("")) {
            SmsManager smsManager = SmsManager.getDefault();

            smsManager.sendTextMessage(sPhone, null, sMessage
                    , null, null);

            Toast.makeText(getApplicationContext()
                    , "Message sent successfully!", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext()
                    , "Enter value first.", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100 && grantResults.length > 0 && grantResults[0]
                == PackageManager.PERMISSION_GRANTED){
            sendMessage();

        }else{
            Toast.makeText(getApplicationContext()
                    , "Permission Denied!",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}