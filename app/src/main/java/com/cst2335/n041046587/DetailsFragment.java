package com.cst2335.n041046587;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


public class DetailsFragment extends Fragment {

    private boolean isTablet;
    private Bundle dataFromActivity;
    private long db_id;
    private int id;

    public void setTablet(boolean tablet) { isTablet = tablet; }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();
        db_id = dataFromActivity.getLong("db_id" );
        id = dataFromActivity.getInt("id" );
        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.fragment_details, container, false);

        //show the message
        TextView messageFragment = (TextView)result.findViewById(R.id.messageFragment);
        messageFragment.setText("message: "+dataFromActivity.getString("item"));

        //show the id:
        TextView idMessage = (TextView)result.findViewById(R.id.idMessage);
        idMessage.setText("Listview ID=" + id);

        CheckBox checkBox = (CheckBox)result.findViewById(R.id.checkBoxFragment);
        checkBox.setChecked(true);


        // get the hide button, and add a click listener:
        Button hideButton = (Button)result.findViewById(R.id.hideButton);
        hideButton.setOnClickListener( click -> {


            if(isTablet) { //both the list and details are on the screen:
                ChatRoomActivity parent = (ChatRoomActivity)getActivity();
                //now remove the fragment since you deleted it from the database:
                // this is the object to be removed, so remove(this):
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
            }
            //for Phone:
            else {
                EmptyActivity parent = (EmptyActivity) getActivity();
                Intent backToFragmentExample = new Intent();
                backToFragmentExample.putExtra("db_id", dataFromActivity.getLong("db_id"));

                parent.setResult(Activity.RESULT_OK, backToFragmentExample);
                parent.finish(); //go back
            }

        });
        return result;
    }
}