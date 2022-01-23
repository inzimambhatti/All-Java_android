package com.haroon.randomtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

   static int max= 360;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SeekBar seekBar = findViewById(R.id.seelbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                max = (360*progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                Toast.makeText(SettingsActivity.this, "Max time Set : "+max+" Seconds", Toast.LENGTH_SHORT).show();
                if (max>=960)
                {
                    MainActivity.max = max;
                    Log.i("max", String.valueOf(max));
                }
                else {
                    MainActivity.max = 960;
                }


            }
        });
    }

}