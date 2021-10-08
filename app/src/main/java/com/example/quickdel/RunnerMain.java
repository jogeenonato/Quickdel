package com.example.quickdel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RunnerMain extends AppCompatActivity {
    private Button button, button1, button2;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_runnersmain);
        ImageView myImageView = (ImageView) findViewById(R.id.imageView2);
        myImageView.setImageResource(R.drawable.quickdel);


        firebaseAuth = FirebaseAuth.getInstance();
        listener = myFirebaseAuth -> {
            FirebaseUser user = myFirebaseAuth.getCurrentUser();
            String getrunneruserid = user.getUid();
            SharedPreferences settings = getSharedPreferences("Runner", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("runnerID", getrunneruserid);
            editor.apply();
           // Toast.makeText(RunnerMain.this,  getrunneruserid, Toast.LENGTH_SHORT).show();

        };


        button1 = (Button) findViewById(R.id.button4);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openSettings();
            }
        });


        button = (Button) findViewById(R.id.switch1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openOnline();
            }
        });

        button2 = (Button) findViewById(R.id.earn);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openEarnings();
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(listener);
        // delaySplashScreen();

    }

    @Override
    protected void onStop() {
        if(firebaseAuth != null && listener != null)
            firebaseAuth.removeAuthStateListener(listener);
        super.onStop();
    }

    public void openSettings() {
        Intent intent = new Intent(this, com.example.quickdel.Settings.class);
        startActivity(intent);
    }


    public void openOnline() {
        Intent intent = new Intent(this, com.example.quickdel.SplashScreenActivity.class);
        startActivity(intent);
    }


    public void openEarnings() {
        Intent intent = new Intent(this,com.example.quickdel.Earnings.class);
        startActivity(intent);
    }

   public void disable (View v) {

        v.setEnabled(false);
        Button b = (Button) v;
        b.setText("Disabled");

    }


}