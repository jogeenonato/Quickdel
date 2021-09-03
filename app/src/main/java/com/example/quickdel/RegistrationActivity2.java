package com.example.quickdel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

public class RegistrationActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration2);
    }



    public void GotoLogin(View v) {
        Intent intent  = new Intent(getApplicationContext(), LoginActivity2.class);
        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(findViewById(R.id.username),"logo_username");
        pairs[1] = new Pair<View, String>(findViewById(R.id.password),"logo_password");
        pairs[2] = new Pair<View, String>(findViewById(R.id.slogan_name),"logo_desc");
        pairs[3] = new Pair<View, String>(findViewById(R.id.logo_Image),"logo_image");
        pairs[4] = new Pair<View, String>(findViewById(R.id.logoImage),"logo_text");
        pairs[5] = new Pair<View, String>(findViewById(R.id.btnSignUp),"logo_signin");
        pairs[6] = new Pair<View, String>(findViewById(R.id.btnSignIn),"logo_signup");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegistrationActivity2.this, pairs);
            startActivity(intent, options.toBundle());
        }
        else {
            startActivity(intent);
        }
    }
}