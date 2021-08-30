package com.example.quickdel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void disable(View v) {

        EditText t = findViewById(R.id.txtEmail);
        EditText t1 = findViewById(R.id.txtPassword);
        String input = t.getText().toString();
        String input1 = t1.getText().toString();

        if ("".equals(input) || "".equals(input1)) {
            Toast.makeText(this, "Please enter your Email or Password!", Toast.LENGTH_LONG).show();
        } else if ("jericho_yabut03@yahoo.com".equals(input) && "quickdel31".equals(input1)) {
            Toast.makeText(this, "Successfully login!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, UsersHome.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "Incorrect Email or Password!", Toast.LENGTH_LONG).show();
        }

        /*
        ((TextView)findViewById(R.id.output)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.output1)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.output)).setText("Your Email is " + input + ".");
        ((TextView)findViewById(R.id.output1)).setText("Your Password is " + input1 + ".");
        */

          /*
        findViewById(R.id.button).setEnabled(false);
        ((Button)findViewById(R.id.button)).setText("Disabled");

       v.setEnabled(false);
        Button button = (Button) v;
        button.setText("Disabled");

        t.setText("");
        t1.setText("");
        */
    }

    public void logo(View v) {
    ImageView i = findViewById(R.id.imageView2);
    i.setVisibility(View.INVISIBLE);
    ConstraintLayout cl = findViewById(R.id.CL1);
    cl.setVisibility(View.VISIBLE);
    }

    public void GotoRegistration(View v) {
        Intent i = new Intent(this, RegistrationActivity.class);
        startActivity(i);
    }
}