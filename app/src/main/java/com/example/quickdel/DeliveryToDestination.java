package com.example.quickdel;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quickdel.Model.DriverInfoModel;
import com.example.quickdel.ui.gallery.GalleryFragment;
import com.example.quickdel.ui.gallery.GalleryViewModel;
import com.example.quickdel.ui.home.HomeFragment;
import com.example.quickdel.ui.slideshow.SlideshowFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.DriverManager;
import java.util.UUID;

public class DeliveryToDestination extends AppCompatActivity {
    //Initialize variable
    EditText etSource, etDestination;
    Button btDeliver;
    //private Button button;

    ImageView picture;
    Button bt_Open;
    private StorageReference mStorage;

    private ProgressDialog mProgress;

    FirebaseStorage storage;

    ImageView imageView;
    Button button, uploadPhoto, bt_finish;
    public Uri uri;

    Uri imageUri;
    DatabaseReference reference;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_to_destination);
        reference = database.getInstance().getReference().child("Orders");

        SharedPreferences settings = getSharedPreferences("Order", Context.MODE_PRIVATE);
        String pickupPoint = settings.getString("destinationPoint", "");
        String orderNumber = settings.getString("orderNumber", "");

        imageView = findViewById(R.id.image_view);
        button = findViewById(R.id.button);

        //Assign Variable
        etSource = findViewById(R.id.et_source);
        etDestination = findViewById(R.id.et_destination);
        btDeliver = findViewById(R.id.bt_recipient);
        //button = findViewById(R.id.uploadPicture);

        picture = findViewById(R.id.picture);
        bt_Open = findViewById(R.id.uploadPicture);
        mStorage = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(this);

        storage = FirebaseStorage.getInstance();
        uploadPhoto = findViewById(R.id.uploadPhoto);

        bt_finish = findViewById(R.id.cameraPhoto);


        if (ContextCompat.checkSelfPermission(DeliveryToDestination.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DeliveryToDestination.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }
        bt_Open.setOnClickListener(view -> {


            reference.child(orderNumber).child("status").setValue("Runner is in Destination Location");
            //Open Camera
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 100);
            bt_Open.setVisibility(View.INVISIBLE);
            uploadPhoto.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);

        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");

                uploadPhoto.setEnabled(true);
            }
        });

        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //upload Image on button click
                uploadImage();



            }


        });


        //String sourceLocation = getIntent().getStringExtra("keysource");
        String sourceLocation = "";
        String destinationLocation = getIntent().getStringExtra("keydestination");

        etSource.setText(sourceLocation);
        etDestination.setText(pickupPoint);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                openUploadPicture();
//            }
//        });


        btDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get value from edit text
                String sSource = etSource.getText().toString().trim();
                String sDestination = etDestination.getText().toString().trim();

                //Check condition
                if (sDestination.equals("")) {
                    //When both value blank
                    Toast.makeText(getApplicationContext()
                            , "Enter Destination point", Toast.LENGTH_LONG).show();
                }
                // else{
                //when both value fill, display track
                DisplayTrack(sSource, sDestination);
                //}
                btDeliver.setVisibility(View.INVISIBLE);
                Thread timer = new Thread() {
                    public void run() {
                        try {
                            sleep(1000);
                            bt_Open.setVisibility(View.VISIBLE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                };
                timer.start();
            }
        });
        bt_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference1;
                reference1 = database.getInstance().getReference().child("Orders");
                reference1.child(orderNumber).child("status").setValue("Delivered");
                openFinalAnimation();
            }
        });
    }

    public void openFinalAnimation() {
        Intent intent = new Intent(this, FinalAnimation.class);
        startActivity(intent);
    }

    private void uploadImage() {

        if (imageUri != null) {
            SharedPreferences settings = getSharedPreferences("Order", Context.MODE_PRIVATE);
            String orderNumber = settings.getString("orderNumber", "");
            StorageReference reference = storage.getReference().child("images/" + orderNumber);
            //we are creating a reference to store the image in firebase storage
            //It will be stored inside image folder in firebase storage.
            //use user auth id instead of uuid if your app has firebase auth

            //using the below code wwe weill store the file

            reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText(DeliveryToDestination.this, "Image uploaded successfully", Toast.LENGTH_LONG).show();
                        uploadPhoto.setVisibility(View.INVISIBLE);
                        bt_finish.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(DeliveryToDestination.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            });


        }

    }


    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {
                        imageView.setImageURI(result);
                        //result will be set in imageUri
                        imageUri = result;
                    }
                }
            });


//    private void openUploadPicture() {
//        Intent intent = new Intent(this, com.example.quickdel.UploadPicture.class);
//        startActivity(intent);
//    }


    private void DisplayTrack(String sSource, String sDestination) {
        //if the devise does not have a map installed, then redirect to playstore
        try {
            //When google map is installed
            //Initialize uri
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + sSource + "/"
                    + sDestination);
            //Initialize intent with action vew
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            //Set package
            intent.setPackage("com.google.android.apps.maps");
            //set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //start Activity
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            //When google map is not installed
            //Initialize uri
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            //Initialize intent with action view
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            //Set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //sTART ACTIVITY
            startActivity(intent);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //Get capture image
            //mProgress.setMessage("Uploading Image...");
            // mProgress.show();
            // Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            //set capture image to imageview
            // picture.setImageBitmap(captureImage);

            //uploadPicture();


            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            Uri uri = data.getData();


//            StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());
//            filepath.putFile(uri).addOnSuccessListener(taskSnapshot -> {
//               // Snackbar.make(findViewById(android.R.id.content), "image uploaded", Snackbar.LENGTH_LONG).show();
//                //mProgress.dismiss();
//                //Toast.makeText(DeliveryToDestination.this,"Uploading Finished", Toast.LENGTH_LONG).show();
//
//            });


        }
        //Intent intent = new Intent(DeliveryToDestination.this, DriverHomeActivity.class);
        //startActivity(intent);
    }

//    private void uploadPicture() {
//
//        //final String randomKey = UUID.randomUUID().toString();
//
//
//        // mountainsRef = mStorage.child("mountains.jpg").child(uri.getLastPathSegment());
//        StorageReference mountainsRef = mStorage.child("mountains.jpg");
//
//// Create a reference to 'images/mountains.jpg'
//
//        //StorageReference mountainImagesRef = mStorage.child("images/mountains.jpg").child(uri.getLastPathSegment());
//        StorageReference mountainImagesRef = mStorage.child("images/mountains.jpg");
//
//
//// While the file names are the same, the references point to different files
//        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
//        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false

}

