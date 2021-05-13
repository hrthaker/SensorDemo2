package com.example.sensordemo2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VideoCaptureActivity extends AppCompatActivity {

    Button btnVideoCapture;
    VideoView vvVideo;
    File f;
    Uri videoURI=null, uri=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_capture);
        btnVideoCapture = findViewById(R.id.btnVideoCapture);
        vvVideo = findViewById(R.id.vvVideo);
        int pc1 = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA);
        int pc2 = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (pc1 != 0 && pc2 != 0) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 123);
        }
        btnVideoCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    attemptStartCamera(VideoCaptureActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void attemptStartCamera(Activity ctx) throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takePictureIntent.resolveActivity(ctx.getPackageManager()) != null) {
            // Create the File where the photo should go
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String mFileName = "JPEG_" + timeStamp + "_";
            File photoFile = null;
            try {
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                photoFile = File.createTempFile(mFileName, ".mp4", storageDir);

            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                videoURI = FileProvider.getUriForFile(ctx,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);

                //mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI);
                startActivityForResult(takePictureIntent, 1);

            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            vvVideo.setVideoURI(videoURI);
            vvVideo.start();
        }

    }
}