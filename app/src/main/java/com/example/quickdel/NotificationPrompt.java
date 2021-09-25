package com.example.quickdel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NotificationPrompt extends AppCompatActivity {

    private EditText source, destination;
    private Button accept;

    //ListView myListView;

    //ArrayList<String> myArrayList = new ArrayList<>();

    //DatabaseReference mRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_prompt);

       // ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(NotificationPrompt.this, android.R.layout.simple_list_item_1,myArrayList   );


        /*myListView = (ListView) findViewById(R.id.listview1);
        myListView.setAdapter(myArrayAdapter);
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot datasnapshot, @Nullable String previousChildName) {
                String value = datasnapshot.getValue(String.class);
                myArrayList.add(value);
                myArrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                myArrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        source = findViewById(R.id.source);
        destination = findViewById(R.id.destination);
        accept = findViewById(R.id.bt_accept);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sourceLocation = source.getText().toString();
                String destinationLocation = destination.getText().toString();

                Intent intent = new Intent(NotificationPrompt.this,Track.class);
                intent.putExtra("keysource", sourceLocation);
                intent.putExtra("keydestination",destinationLocation);
                startActivity(intent);
            }
        });
    }
}