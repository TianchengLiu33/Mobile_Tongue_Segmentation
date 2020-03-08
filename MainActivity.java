package com.example.tongue_try_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    private static final int REUQUEST_CAPTURE_IMAGE =100;
    private Button takeImageButton;
    private ImageView mImageView;
    String currentPhotoPath;

    private static String TAG = "MainActivity";
    static {
        if (OpenCVLoader.initDebug()){
            Log.d(TAG, "OpenCV is Connected");
        } else {
            Log.d(TAG, "Not connected");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takeImageButton = findViewById(R.id.imageBTN);
        mImageView = findViewById(R.id.imageView);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        takeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Log.v("myAPP", "Before enter");
                if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                    //Log.v("myAPP", "Enter if and before start activity");
                    startActivityForResult(pictureIntent, REUQUEST_CAPTURE_IMAGE);
                    //Log.v("myAPP", "Enter if and after start activity");
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REUQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                mImageView.setImageBitmap(imageBitmap);
            }
        }
    }

    
}
