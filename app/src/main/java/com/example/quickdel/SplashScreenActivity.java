package com.example.quickdel;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.quickdel.Model.DriverInfoModel;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;

public class SplashScreenActivity extends AppCompatActivity {


    private final static int LOGIN_REQUEST_CODE = 7171;
    private List<AuthUI.IdpConfig>providers;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;

    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;

    FirebaseDatabase database;
    DatabaseReference driverInfoRef;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //delaySplashScreen();

        init();

    }


    private void init() {

        ButterKnife.bind(this);

        database = FirebaseDatabase.getInstance();
        driverInfoRef = database.getReference(Comon.DRIVER_INFO_REFERENCE);

        providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        firebaseAuth = FirebaseAuth.getInstance();
        listener = myFirebaseAuth -> {
            FirebaseUser user = myFirebaseAuth.getCurrentUser();
            String getrunneruserid = String.valueOf(user.getUid());
            SharedPreferences settings = getSharedPreferences("Runner", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("runnerID", getrunneruserid);
            editor.apply();
            //Toast.makeText(SplashScreenActivity.this,  getrunneruserid, Toast.LENGTH_SHORT).show();
            if(user != null)
            {
                checkUserFromFirebase();
            }

            else
                showLoginLayout();
        };
    }

    private void checkUserFromFirebase() {
        driverInfoRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        if(datasnapshot.exists())
                        {

                            DriverInfoModel driverInfoModel = datasnapshot.getValue(DriverInfoModel.class);

                            goToHomeActivity(driverInfoModel);

                         }

                        else
                            {
                                showRegisterLayout();
                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Toast.makeText(SplashScreenActivity.this,""+databaseError.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void goToHomeActivity(DriverInfoModel driverInfoModel) {
        Comon.currentUser = driverInfoModel; //Init value
        startActivity(new Intent(SplashScreenActivity.this,DriverHomeActivity.class));
        finish();

    }

    private void showRegisterLayout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.DialogTheme);
        View itemView = LayoutInflater.from(this).inflate(R.layout.layout_register,null);

        TextInputEditText edt_first_name = (TextInputEditText) itemView.findViewById(R.id.edt_first_name);
        TextInputEditText edt_last_name = (TextInputEditText) itemView.findViewById(R.id.edt_last_name);

        Button btn_continue = (Button)itemView.findViewById(R.id.btn_register);

        //set data
        //if (FirebaseAuth.getInstance().getCurrentUser() != null &&
             //  !TextUtils.isEmpty(FirebaseAuth.getInstance().getCurrentUser()))
           //edt_phone.setText(FirebaseAuth.getInstance().getCurrentUser());

        //set view
        builder.setView(itemView);
        AlertDialog dialog = builder.create();
        dialog.show();

        btn_continue.setOnClickListener(view -> {
            if (TextUtils.isEmpty(edt_first_name.getText().toString())) {

                Toast.makeText(this, "Please enter first name", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(edt_last_name.getText().toString())) {

                Toast.makeText(this, "Please enter Last name", Toast.LENGTH_SHORT).show();
                return;
            }
            else
                {
                    DriverInfoModel model = new DriverInfoModel();
                    model.setFirstname(edt_first_name.getText().toString());
                    model.setLastname(edt_last_name.getText().toString());
                    model.setRating(0.0);

                    driverInfoRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(model)
                            .addOnFailureListener(e ->
                                    {
                                        dialog.dismiss();
                                        Toast.makeText(SplashScreenActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    )
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(this, "Register Successfully", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                //goToHomeActivity(model);

                            });
                }


        });

    }

    private void showLoginLayout() {
        AuthMethodPickerLayout authMethodPickerLayout = new AuthMethodPickerLayout
                .Builder(R.layout.layout_sign_in_r)
                .setPhoneButtonId(R.id.btn_phone_sign_in)
                .setGoogleButtonId(R.id.btn_google_sign_in)
                .build();

        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAuthMethodPickerLayout(authMethodPickerLayout)
                .setIsSmartLockEnabled(false)
                .setTheme(R.style.LoginTheme)
                .setAvailableProviders(providers)
                .build(),LOGIN_REQUEST_CODE);
    }
    private void delaySplashScreen() {

        progress_bar.setVisibility(View.VISIBLE);

        Completable.timer(5, TimeUnit.SECONDS,
                AndroidSchedulers.mainThread())
                .subscribe(() ->
                        //Toast.makeText(SplashScreenActivity.this,"splashscreen done",Toast.LENGTH_SHORT).show()

                        //After show splash screen ask login of not login
                        firebaseAuth.addAuthStateListener(listener)
                );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOGIN_REQUEST_CODE)
        {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK)
            {
                //creo q aquí es el runner
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            }
            else
            {
                Toast.makeText(this, "[ERROR]: "+response.getError().getMessage(), Toast.LENGTH_SHORT);
            }
        }
    }
}

