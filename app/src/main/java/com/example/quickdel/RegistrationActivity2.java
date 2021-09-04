package com.example.quickdel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class RegistrationActivity2 extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button regBtn, regToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration2);

        //Hooks
        regName = findViewById(R.id.name);
        regUsername = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPhoneNo = findViewById(R.id.phone);
        regPassword = findViewById(R.id.password);
        regBtn = findViewById(R.id.btnSignUp);
        regToLoginBtn = findViewById(R.id.btnSignIn);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                String name = regName.getEditText().getText().toString();
                String username = regUsername.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phoneNo = regPhoneNo.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();

                UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);

                reference.child(phoneNo).setValue(helperClass);
                Toast.makeText(RegistrationActivity2.this , "Successfully Registered!", Toast.LENGTH_LONG).show();
            }



        });



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