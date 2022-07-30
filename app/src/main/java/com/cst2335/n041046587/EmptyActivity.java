package com.cst2335.n041046587;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Bundle dataToPass = getIntent().getExtras(); //get the data that was passed from FragmentExample

        //This is copied directly from FragmentExample.java lines 47-54
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments( dataToPass ); //pass data to the the fragment
        detailsFragment.setTablet(false); //tell the Fragment that it's on a phone.
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.FrameLayout, detailsFragment)
                .addToBackStack("Anything here")
                .commit();
    }
}