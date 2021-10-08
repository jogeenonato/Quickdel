package com.example.quickdel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class Faq extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Versions> versionsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        recyclerView = findViewById(R.id.recyclerView);

        initData();
        setRecyclerView();

    }

    private void setRecyclerView() {
        VersionsAdapter versionsAdapter = new VersionsAdapter(versionsList);
        recyclerView.setAdapter(versionsAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void initData() {

        versionsList = new ArrayList<>();

        versionsList.add(new Versions("What should I do if there is an unleashed dog?","If you see a dog do not enter the premises, contact the recipient first and ask if is save to enter"));
        versionsList.add(new Versions("What benefits do I get for referring a friend?", "You will receive $80 to your bank account once your friend has completed 40 deliveries with us"));
        versionsList.add(new Versions("I’m I allowed to leave the parcel unattended?", "If there is no note to not leave the parcel unattended, then you can leave it unattended"));
        versionsList.add(new Versions("It is raining and there is not roof nearby , and the recipient does not  answer the phone.", "Contact us on 01800124578 to receive support"));
        versionsList.add(new Versions("How long will it take for me to receive the payment once is done?", "Once you have successfully complete the order it should not take more than few hours if is not the first time"));
        versionsList.add(new Versions("How can I refer a friend", "We are implementing an option to refer a friend to work with us through the app, for the moment tell your friend to contact us saying is your referral so you can earn 80$ once he has completed 40 deliveries "));
        versionsList.add(new Versions("What should I do if my vehicle breaks down?", "Contact us on 01800124578 so we can send another runner to pickup and deliver your parcel"));
        versionsList.add(new Versions("Will Quickdel pay for tolls?", "No, is not necessary to go through tolls unless agreed payment with the recipient"));
        versionsList.add(new Versions("Can someone come with me when doing the delivery", "Yes, we are a very cool, and relax company, that wants our collaborators to feel as comfortable as much as they want "));
        versionsList.add(new Versions("I can not find the picked up location address", "Contact the picked up person.\n" +
                "Contact the recipient” link.\n" +
                "If no response contact us at 01800124578."));
    }
}