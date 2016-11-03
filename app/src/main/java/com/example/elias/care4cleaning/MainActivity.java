package com.example.elias.care4cleaning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;
    Communication communication;
    String token = "token";
    String imageName = "imageName";
    Context context;
    Bitmap imageBitmap;
    private static final String TAG = "com.example.statechange";
    String caseId = "";
    String description = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        communication = new Communication(this);
        communication.setupSSLCertificate();
        context = getApplicationContext();
        takePicture();

        SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        if (prefs.contains("token"))
        {
            token = prefs.getString("token","");
        } else{
            createUser();
        }

        sendPicture();
        //TODO setup your listeners for buttons etc....
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //ALWAYS CALL THE SUPER METHOD
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
		/* Here we put code now to save the state */
        EditText editCaseId = (EditText) findViewById(R.id.caseId);
        EditText editDescription = (EditText) findViewById(R.id.description);

        caseId = editCaseId.getText().toString();
        description = editDescription.getText().toString();

    }

    //this is called when our activity is recreated, but
    //AFTER our onCreate method has been called
    //EXTREMELY IMPORTANT DETAIL
    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        Log.i(TAG, "onRestoreInstanceState");
        ImageView imageView = (ImageView) findViewById(R.id.ImageView);

        imageView.setImageBitmap(imageBitmap);
        EditText editCaseId = (EditText) findViewById(R.id.caseId);
        EditText editDescription = (EditText) findViewById(R.id.description);

        editCaseId.setText(caseId);
        editDescription.setText(description);
    }


    public void sendPicture(){
        Button btnSendPicture = (Button) findViewById(R.id.sendPicture);

        btnSendPicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast toast = Toast.makeText(context,"Uploading case...please wait",Toast.LENGTH_LONG);
                toast.show();
                EditText editCaseId = (EditText) findViewById(R.id.caseId);
                EditText editDescription = (EditText) findViewById(R.id.description);
                String caseId = editCaseId.getText().toString();
                String description = editDescription.getText().toString();
                communication.uploadPicture(imageBitmap, token, caseId, description, imageName);
            }
        });
    }

    public void takePicture(){
        ImageView imageClick = (ImageView) findViewById(R.id.ImageView);

        imageClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView imageView = (ImageView) findViewById(R.id.ImageView);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (mCurrentPhotoPath!=null) {
                imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);

                imageView.setImageBitmap(imageBitmap);
            }
        }
    }

    private File createImageFile() throws IOException {
        String uuid = UUID.randomUUID().toString();
        String imageFileName = "JPEG_" + uuid;
        imageName = imageFileName;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                System.out.println(ex);
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void createUser() {
        LoginDialogFragment dialog = new LoginDialogFragment(this,"Create user","Choose a username")
        {
            @Override
            public void clickCancel() {
                super.clickCancel();
            }

            @Override
            public void clickOk() {
                Toast toast = Toast.makeText(context,"Creating user...please wait",Toast.LENGTH_LONG);
                toast.show();
                //Communication coms = new Communication(context);
                communication.CreateUser(getUserInput(),token+=System.currentTimeMillis());
                super.clickOk();
            }
        };
        dialog.show();
    }
}
