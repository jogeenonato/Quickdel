package com.example.quickdel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Support extends AppCompatActivity {
    Button button;
    EditText mailtotext, subjecttext, compose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        mailtotext = findViewById(R.id.mailtotext);
        //subjecttext = findViewById(R.id.subjecttext);
        //compose = findViewById(R.id.compose);
       // button = (Button) findViewById(R.id.Send);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW
                , Uri.parse("mailto" + mailtotext.getText().toString()));
                intent.putExtra(Intent.EXTRA_SUBJECT,subjecttext.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT,compose.getText().toString());

                startActivity(intent);
            }
        });


    }


}