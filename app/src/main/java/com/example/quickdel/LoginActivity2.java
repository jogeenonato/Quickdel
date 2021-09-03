package com.example.quickdel;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity2 extends AppCompatActivity {

    Button callSignUp, login_btn;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login2);
    }

    public void loginHandler(View v) {
        TextInputEditText t = findViewById(R.id.username);
        TextInputEditText t1 = findViewById(R.id.password);

        String input = t.getText().toString();
        String input1 = t1.getText().toString();

        if ("".equals(input) || "".equals(input1)) {
            Toast.makeText(this, "Please enter your Email or Password!", Toast.LENGTH_LONG).show();
        } else if ("jericho_yabut03@yahoo.com".equals(input) && "quickdel31".equals(input1)) {
            Toast.makeText(this, "Successfully login!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, UsersHome.class);
            startActivity(i);
        } else {
            Toast.makeText(this,"Incorrect Email or Password!", Toast.LENGTH_LONG).show();
        }
    }

    public void GotoRegistration(View v) {
        Intent intent  = new Intent(getApplicationContext(), RegistrationActivity2.class);
        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(findViewById(R.id.username1),"logo_username");
        pairs[1] = new Pair<View, String>(findViewById(R.id.password1),"logo_password");
        pairs[2] = new Pair<View, String>(findViewById(R.id.slogan_name),"logo_desc");
        pairs[3] = new Pair<View, String>(findViewById(R.id.logo_Image),"logo_image");
        pairs[4] = new Pair<View, String>(findViewById(R.id.logoImage),"logo_text");
        pairs[5] = new Pair<View, String>(findViewById(R.id.btnSignIn),"logo_signin");
        pairs[6] = new Pair<View, String>(findViewById(R.id.signup_screen),"logo_signup");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity2.this, pairs);
            startActivity(intent, options.toBundle());
        }
        else {
            startActivity(intent);
        }
    }
}