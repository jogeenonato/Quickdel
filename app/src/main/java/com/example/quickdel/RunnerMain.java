package com.example.quickdel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class RunnerMain extends AppCompatActivity {
    private Button button, button1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_runnersmain);
        ImageView myImageView = (ImageView) findViewById(R.id.imageView2);
        myImageView.setImageResource(R.drawable.quickdel);



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


    }

    public void openSettings() {
        Intent intent = new Intent(this, com.example.quickdel.Settings.class);
        startActivity(intent);
    }


    public void openOnline() {
        Intent intent = new Intent(this, com.example.quickdel.SplashScreenActivity.class);
        startActivity(intent);
    }


   public void disable (View v) {

        v.setEnabled(false);
        Button b = (Button) v;
        b.setText("Disabled");

    }


}