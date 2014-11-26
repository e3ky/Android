package com.week5.cpt2mab.camera2email;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MyActivity extends Activity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputPicureFileUri();
        System.out.println(fileUri);
        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(takePicture, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == -1) {
                System.out.println("The picture has been captured");
                Intent send_img = new Intent(Intent.ACTION_SEND);
                send_img.putExtra(Intent.EXTRA_EMAIL, new String[]{"martinbelperchinov@gmail.com"});
                send_img.putExtra(Intent.EXTRA_SUBJECT, "email_subject");
                send_img.putExtra(Intent.EXTRA_STREAM, fileUri);
                send_img.putExtra(Intent.EXTRA_TEXT, "message");
                send_img.setType("text/plain");
                send_img.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                // startActivity(Intent.createChooser(send_img, "Send Email..."));
                startActivity(send_img);
                finish();
            } else {
                System.out.println("Some issue occurred. The picture has not been captured");
            }
        }
        System.out.println("resultCode:" + resultCode);
    }

    private static Uri getOutputPicureFileUri() {
        File pictureStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");

        if (!pictureStorageDir.exists()) {
            if (!pictureStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(pictureStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        return Uri.fromFile(mediaFile);
    }

}
