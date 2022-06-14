package com.cst2335.n041046587;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    private EditText editTextViewEmail;
    private ImageButton button;

    public static final String SP = "Shared Preferences";
    public static final String EMAIL = "email";
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab3);

        editTextViewEmail = (EditText) findViewById(R.id.editTextEmail);
        button = (ImageButton) findViewById( R.id.button);

        /*
         * load user's email address from SharedPreferences
         */
        SharedPreferences prefs = getSharedPreferences(SP, MODE_PRIVATE);
        email = prefs.getString(EMAIL, "");

        /*
         * Set email address of Email Edit Text
         */
        editTextViewEmail.setText(email);


        /*
         * Give a message when clicking the Click Here button
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
                goToProfile.putExtra("email", editTextViewEmail.getText().toString());
                startActivity(goToProfile);
            }
        });

        }

        @Override
        protected void onPause() {
            super.onPause();

            // save a user's email address
            SharedPreferences sharedPreferences = getSharedPreferences(SP, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(EMAIL, editTextViewEmail.getText().toString());

            editor.apply();
        }


}