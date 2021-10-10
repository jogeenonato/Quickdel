package com.example.quickdel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class FinalAnimation extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000;

    //Variables
    Animation topAnim, bottomAnim;
    ImageView image1, image2;
    TextView slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_final_animation);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        image1 = findViewById(R.id.imageView1);
        image2 = findViewById(R.id.imageView2);
        slogan = findViewById(R.id.textView1);

        image1.setAnimation(topAnim);
        image2.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FinalAnimation.this, DriverHomeActivity.class);
                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair<View, String>(image1,"logo_image");
                pairs[1] = new Pair<View, String>(image2,"logo_text");
                pairs[2] = new Pair<View, String>(slogan,"logo_desc");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(FinalAnimation.this, pairs);
                    startActivity(intent, options.toBundle());
                }
                else {
                    startActivity(intent);
                }
            }
        }, SPLASH_SCREEN);

    }


    public void GotoLogin(View v) {
        Intent intent = new Intent(FinalAnimation.this, DriverHomeActivity.class);
        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View, String>(image1,"logo_image");
        pairs[1] = new Pair<View, String>(image2,"logo_text");
        pairs[2] = new Pair<View, String>(slogan,"logo_desc");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(FinalAnimation.this, pairs);
            startActivity(intent, options.toBundle());
        }
        else {
            startActivity(intent);
        }
    }
}