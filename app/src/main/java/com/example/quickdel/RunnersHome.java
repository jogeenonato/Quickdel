package com.example.quickdel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickdel.HelperClasses.HomeAdapter.CategoriesAdapter;
import com.example.quickdel.HelperClasses.HomeAdapter.CategoriesHelperClass;
import com.example.quickdel.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.example.quickdel.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.example.quickdel.HelperClasses.HomeAdapter.MostViewedAdapter;
import com.example.quickdel.HelperClasses.HomeAdapter.MostViewedHelperClass;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class RunnersHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static final float END_SCALE = 0.7f;

    RecyclerView featuredRecycler, mostViewedRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4;
    ImageView menuIcon_r;
    DrawerLayout drawerLayout_r;
    NavigationView navigationView;
    LinearLayout contentView;
    ImageButton btOnline;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_runners_home);

        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);
        mostViewedRecycler = findViewById(R.id.mv_recycler);
        categoriesRecycler = findViewById(R.id.categories_recycler);
        menuIcon_r = findViewById(R.id.menu_icon_r);
        contentView = findViewById(R.id.contentview_r);

        drawerLayout_r = findViewById(R.id.drawer_layout_r);
        navigationView = findViewById(R.id.navigation_view_r);

        btOnline = findViewById(R.id.go_online);

        firebaseAuth = FirebaseAuth.getInstance();

        listener = myFirebaseAuth -> {
            FirebaseUser user = myFirebaseAuth.getCurrentUser();
            if(user != null)
            {
                String getrunneruserid = user.getUid();
                SharedPreferences settings = getSharedPreferences("Runner", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("runnerID", getrunneruserid);
                editor.apply();
                // Toast.makeText(RunnerMain.this,  getrunneruserid, Toast.LENGTH_SHORT).show();
            }

        };


        navigationDrawer();

        featuredRecycler();
        mostViewedRecycler();
        categoriesRecycler();




        btOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openOnline();
            }
        });

    }
    public void openOnline() {
        Intent intent = new Intent(this, com.example.quickdel.SplashScreenActivity.class);
        startActivity(intent);
    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home_r);

        menuIcon_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout_r.isDrawerVisible(GravityCompat.START))
                    drawerLayout_r.closeDrawer(GravityCompat.START);
                else drawerLayout_r.openDrawer(GravityCompat.START);

            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {
        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout_r.setScrimColor(getResources().getColor(R.color.card4));
        drawerLayout_r.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

    @Override
    public void onBackPressed() {

        if(drawerLayout_r.isDrawerVisible(GravityCompat.START)) {
            drawerLayout_r.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_profile_r:
                startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.nav_logout:
                startActivity(new Intent(getApplicationContext(), LoginActivity2.class));
                finish();
                break;
            case R.id.nav_home_r:
                if (drawerLayout_r.isDrawerVisible(GravityCompat.START))
                    drawerLayout_r.closeDrawer(GravityCompat.START);
                else drawerLayout_r.openDrawer(GravityCompat.START);
                break;
            case R.id.nav_contact:
                startActivity(new Intent(getApplicationContext(), Support.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.nav_FAQ:
                startActivity(new Intent(getApplicationContext(), Faq.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.nav_terms:
                startActivity(new Intent(getApplicationContext(), Conditions.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.nav_preferences:
                startActivity(new Intent(getApplicationContext(), RunnerPreferences.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.nav_payment_method:
                startActivity(new Intent(getApplicationContext(), RunnerPaymentMethod.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.nav_earnings:
                startActivity(new Intent(getApplicationContext(), Earnings.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    public void openOrderHistory(View v) {
        Intent intent = new Intent(this, OrderHistory.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void categoriesRecycler() {

        //All Gradients
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xffb8d7f5});


        ArrayList<CategoriesHelperClass> categoriesHelperClasses = new ArrayList<>();
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.restaurant_image, "Quick", gradient1));
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.medicines2, "Convenient", gradient3));
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.goodscartoon, "Affordable", gradient2));
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.shopping, "Reliable", gradient4));


        categoriesRecycler.setHasFixedSize(true);
        adapter = new CategoriesAdapter(categoriesHelperClasses);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setAdapter(adapter);

    }

    private void featuredRecycler() {
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.go_online, "Go Online", "Tap on \"Gon online\" and start receivng Quickdels!!"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.take_picture, "Take a picture", "When you arrive to your destination take a picture of the Quickdel"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.money, "Get your money", "Once you finish your order it will be few hours for you to receive the money."));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);

        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});
    }

    private void mostViewedRecycler() {

        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<MostViewedHelperClass> mostViewedLocations = new ArrayList<>();
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.bike, "Motorbike - Use for small packages"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.car, "Sedan - Use for medium to large packages"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.ute, "Ute - Use for big packages"));

        adapter = new MostViewedAdapter(mostViewedLocations);
        mostViewedRecycler.setAdapter(adapter);

    }


}