package com.cst2335.n041046587;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    LinearLayout MainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_constraint);


        /*
         * Give a message when clicking the Click Here button
         */
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(v -> {

            Toast t = Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.toast_message),
                    Toast.LENGTH_LONG);
            t.show();
        });

        /*
         * Give a message when switching the switch button.
         */
        Switch s = (Switch) findViewById(R.id.s);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                s.setChecked(isChecked);
                if(s.isChecked()){
                    Snackbar.make(s, getResources().getString(R.string.s_on), BaseTransientBottomBar.LENGTH_SHORT).setAction("Undo",
                            click -> buttonView.setChecked(!isChecked)).show();
                }
                else {
                    Snackbar.make(s, getResources().getString(R.string.s_off), BaseTransientBottomBar.LENGTH_SHORT).setAction("Undo",
                            click -> buttonView.setChecked(!isChecked)).show();
                }
            }
        });
    }
}