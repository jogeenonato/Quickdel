package com.example.quickdel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class UserProfileActivity extends AppCompatActivity {
    ImageView back_btn;
    TextInputLayout fullName, email, phoneNo, password;
    TextView fullnameLabel, usernamelabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_profile);

        //Hooks
        fullName = findViewById(R.id.full_name_profile);
        email = findViewById(R.id.email_profile);
        phoneNo = findViewById(R.id.phone_profile);
        password = findViewById(R.id.password_profile);
        fullnameLabel = findViewById(R.id.full_name_top);
        usernamelabel = findViewById(R.id.username_top);
        back_btn = findViewById(R.id.back_profile);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserProfileActivity.super.onBackPressed();
            }
        });

        //Show All Data
        showAllUserData();
        
    }
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    private void showAllUserData() {
        /*
        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        String user_name = intent.getStringExtra("name");
        String user_email = intent.getStringExtra("email");
        String user_phoneNo = intent.getStringExtra("phoneNo");
        String user_password = intent.getStringExtra("password");

        fullnameLabel.setText(user_name);
        usernamelabel.setText("#" + user_username);
        fullName.getEditText().setText(user_name);
        email.getEditText().setText(user_email);
        phoneNo.getEditText().setText(user_phoneNo);
        password.getEditText().setText(user_password);
        */


        SharedPreferences settings = getSharedPreferences("Profile", Context.MODE_PRIVATE);
        String username1 = settings.getString("username", "");
        String name1 = settings.getString("name", "");
        String email1 = settings.getString("email", "");
        String phoneNo1 = settings.getString("phoneNo", "");
        String password1 = settings.getString("password", "");

        fullnameLabel.setText(name1);
        usernamelabel.setText("#" + username1);
        fullName.getEditText().setText(name1);
        email.getEditText().setText(email1);
        phoneNo.getEditText().setText(phoneNo1);
        password.getEditText().setText(password1);





    }
}