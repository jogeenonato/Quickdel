package com.example.quickdel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerSettingsActivity extends AppCompatActivity {
    private Button payment, faqs, support, tC, uAddress;
    ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.settings_activity);

        back_btn = findViewById(R.id.btn_back);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerSettingsActivity.super.onBackPressed();
            }
        });


    payment = findViewById(R.id.c_payment);
        payment.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openPayment();
        }
    });

    faqs = (Button) findViewById(R.id.c_faq);
        faqs.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openFaq();
        }
    });
    tC = (Button) findViewById(R.id.c_terms);
        tC.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openConditions();
        }
    });


        uAddress = (Button) findViewById(R.id.c_address);
        uAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeliveryAddress();
            }
        });


    support = (Button) findViewById(R.id.c_support);
        support.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openSupport();
        }
    });
}

    public void openFaq() {
        Intent intent = new Intent(this, com.example.quickdel.Faq.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openConditions() {
        Intent intent = new Intent(this, com.example.quickdel.Conditions.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public void openPayment() {
        Intent intent = new Intent(this, com.example.quickdel.CustomerPaymentMethod.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openSupport() {
        Intent intent = new Intent(this, com.example.quickdel.test.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public void openDeliveryAddress() {
        Intent intent = new Intent(this, com.example.quickdel.DeliveryAddress.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void finish () {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}


        /*if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/


    /*public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }*/