package com.example.quickdel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.quickdel.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadPicture extends AppCompatActivity {
    //Initiliaze Variable
    ImageView picture;
    Button bt_Open;
    private StorageReference mStorage;

    private ProgressDialog mProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_picture);


        //Assign Variable

        picture = findViewById(R.id.picture);
        bt_Open = findViewById(R.id.bt_open);
        mStorage = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(this);



        //Request permission from camera

        if (ContextCompat.checkSelfPermission(UploadPicture.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UploadPicture.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }
        bt_Open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Open Camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK){
            //Get capture image
            mProgress.setMessage("Uploading Image...");
            mProgress.show();
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            //set capture image to imageview
            picture.setImageBitmap(captureImage);
            Uri uri = data.getData();


            StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgress.dismiss();
                    Toast.makeText(UploadPicture.this,"Uploading Finished", Toast.LENGTH_LONG).show();

                }
            });

        }

    }

}