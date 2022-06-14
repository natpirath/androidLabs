package com.cst2335.n041046587;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ProfileActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextName;
    private ImageButton button;
    public static final String TAG = "PROFILE_ACTIVITY";
    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Log.e(TAG, " in function: " + "onCreate");

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        ImageButton button = findViewById( R.id.button);


        ActivityResultLauncher<Intent> myPictureTakerLauncher =
                registerForActivityResult( new ActivityResultContracts.StartActivityForResult()
                        ,new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                if (result.getResultCode() == Activity.RESULT_OK)
                                { Intent data = result.getData();
                                    Bitmap imgbitmap = (Bitmap) data.getExtras().get("data");
                                    button.setImageBitmap(imgbitmap); // the imageButton
                                }
                                else if(result.getResultCode() == Activity.RESULT_CANCELED)
                                    Log.i(TAG, "User refused to capture a picture.");
                            }
                        } );

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    myPictureTakerLauncher.launch(takePictureIntent);
                }

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, " in function: " + "onActivityResult");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            button.setImageBitmap(imageBitmap);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, " in function: " + "onStart");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, " in function: " + "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, " in function: " + "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, " in function: " + "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, " in function: " + "onResume");
    }

}