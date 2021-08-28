package com.example.quickdel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class RunnerMain extends AppCompatActivity {
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runnersmain);
        ImageView myImageView = (ImageView) findViewById(R.id.imageView2);
        myImageView.setImageResource(R.drawable.quickdel);



        button = (Button) findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openSettings();
            }
        });


        button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openEarnings();
            }
        });


    }

    public void openSettings() {
        Intent intent = new Intent(this, com.example.quickdel.Settings.class);
        startActivity(intent);
    }


    public void openEarnings() {
        Intent intent = new Intent(this, com.example.quickdel.Earnings.class);
        startActivity(intent);
    }




   public void disable (View v) {



        v.setEnabled(false);
        Button b = (Button) v;
        b.setText("Disabled");


    }


}