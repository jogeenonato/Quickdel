package com.example.quickdel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class RegistrationActivity2 extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button regBtn, regToLoginBtn;
    RadioButton regrbUsers, regrbRunners;
    FirebaseDatabase rootNode;
    //DatabaseReference reference;
    String usertypereference;


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
        regrbUsers = findViewById(R.id.rbUsers);
        regrbRunners = findViewById(R.id.rbRunners);
        //checkerstatus = "true";

        /*
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        */

    }

    private Boolean validateName() {
        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";


        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            regUsername.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regUsername.setError("White Spaces are not allowed");
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }


       // return true;

    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validatePhoneNo() {
        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak");
            return false;
        } else {
            //String bcryptHashing = BCrypt.withDefaults().hashToString(12, val.toCharArray());
            //BCrypt.Result result =BCrypt.verifyer().verify(val.toCharArray(), bcryptHashing);
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }

    }

    //This function will execute when user click on Register Button
    public void registerUser(View view) {

        if (!validateName() | !validatePassword() | !validatePhoneNo() | !validateEmail() | !validateUsername()) {
            return;
        }else {
            isUser();
        }


    }

    public void isUser() {
        final String userEnteredUsername = regUsername.getEditText().getText().toString().trim();
        if (regrbUsers.isChecked()) {
            usertypereference = "users";
        } else {
            usertypereference = "runners";
        }
        final DatabaseReference[] reference = {FirebaseDatabase.getInstance().getReference(usertypereference)};
        Query checkUser = reference[0].orderByChild("username").equalTo(userEnteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    regUsername.setError("Username already exist!");
                    regUsername.requestFocus();
                    return;
                } else {
                    regUsername.setError(null);
                    regUsername.setErrorEnabled(false);
                    String val = regPassword.getEditText().getText().toString();
                    String password = BCrypt.withDefaults().hashToString(12, val.toCharArray());
                    rootNode = FirebaseDatabase.getInstance();
                    reference[0] = rootNode.getReference("users");
                    String name = regName.getEditText().getText().toString();
                    String username = regUsername.getEditText().getText().toString();
                    String email = regEmail.getEditText().getText().toString();
                    String phoneNo = regPhoneNo.getEditText().getText().toString();
                    //String password = bcryptHashing;
                    //String password = regPassword.getEditText().getText().toString();


                    Intent intent = new Intent(getApplicationContext(), VerifyPhoneNoActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("username", username);
                    intent.putExtra("email", email);
                    intent.putExtra("phoneNo", phoneNo);
                    intent.putExtra("password", password);

                    if (regrbUsers.isChecked()) {
                        intent.putExtra("usertype", "users");
                    } else {
                        intent.putExtra("usertype", "runners");
                    }

                    startActivity(intent);
                    //UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);
                    //reference.child(username).setValue(helperClass);

                    //Toast.makeText(RegistrationActivity2.this, "Successfully Registered!", Toast.LENGTH_LONG).show();
                    //Intent intent = new Intent(getApplicationContext(), LoginActivity2.class);
                    //startActivity(intent);

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void GotoLogin(View v) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity2.class);
        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(findViewById(R.id.username), "logo_username");
        pairs[1] = new Pair<View, String>(findViewById(R.id.password), "logo_password");
        pairs[2] = new Pair<View, String>(findViewById(R.id.slogan_name), "logo_desc");
        pairs[3] = new Pair<View, String>(findViewById(R.id.logo_Image), "logo_image");
        pairs[4] = new Pair<View, String>(findViewById(R.id.logoImage), "logo_text");
        pairs[5] = new Pair<View, String>(findViewById(R.id.btnSignUp), "logo_signin");
        pairs[6] = new Pair<View, String>(findViewById(R.id.btnSignIn), "logo_signup");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegistrationActivity2.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}