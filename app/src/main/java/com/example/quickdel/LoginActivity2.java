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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class LoginActivity2 extends AppCompatActivity {

    Button callSignUp, login_btn;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout username, password;
    RadioButton loginrbUsers, loginrbRunners;
    String usertypereference;
    Class classtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login2);

        username = findViewById(R.id.username1);
        password = findViewById(R.id.password1);
        loginrbUsers = findViewById(R.id.rbUsers);
        loginrbRunners = findViewById(R.id.rbRunners);
    }

    /*
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
    */


    private Boolean validateUsername() {
        String val = username.getEditText().getText().toString();

        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();

        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }

    }

    public void loginUser(View view) {
        //Validate Login Info
        if (!validateUsername() | !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    private void isUser() {
        /*
        progressBar.setVisibility(View.VISIBLE);

         */
        final String userEnteredUsername = username.getEditText().getText().toString().trim();
        final String userEnteredPassword = password.getEditText().getText().toString().trim();

        if (loginrbUsers.isChecked()){
            usertypereference = "users";
            classtype = UsersHome.class;
        }  else {
            usertypereference = "runners";
            classtype = RunnerMain.class;
        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(usertypereference);
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    username.setError(null);
                    username.setErrorEnabled(false);
                    String val = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    //String passwordFromDB = BCrypt.verifyer().verify(userEnteredPassword.toCharArray(), val);
                    BCrypt.Result result = BCrypt.verifyer().verify(userEnteredPassword.toCharArray(), val);
                    if (result.verified) {
                        username.setError(null);
                        username.setErrorEnabled(false);
                        String nameFromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String phoneNoFromDB = dataSnapshot.child(userEnteredUsername).child("phoneNo").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);
                        Intent intent = new Intent(getApplicationContext(), classtype);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phoneNo", phoneNoFromDB);
                        intent.putExtra("password", userEnteredPassword);
                        startActivity(intent);


                    } else {
                        /*
                        progressBar.setVisibility(View.GONE);

                         */
                        password.setError("Incorrect Password");
                        password.requestFocus();
                    }
                } else {
                    /*
                    progressBar.setVisibility(View.GONE);

                     */
                    username.setError("Username doesn't exist");
                    username.requestFocus();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void GotoRegistration(View v) {
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity2.class);
        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(findViewById(R.id.username1), "logo_username");
        pairs[1] = new Pair<View, String>(findViewById(R.id.password1), "logo_password");
        pairs[2] = new Pair<View, String>(findViewById(R.id.slogan_name), "logo_desc");
        pairs[3] = new Pair<View, String>(findViewById(R.id.logo_Image), "logo_image");
        pairs[4] = new Pair<View, String>(findViewById(R.id.logoImage), "logo_text");
        pairs[5] = new Pair<View, String>(findViewById(R.id.btnSignIn), "logo_signin");
        pairs[6] = new Pair<View, String>(findViewById(R.id.signup_screen), "logo_signup");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity2.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}