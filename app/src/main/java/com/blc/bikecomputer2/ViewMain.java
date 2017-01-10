package com.blc.bikecomputer2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blc.bikecomputer2.custom_views.InclinationLevel;
import com.blc.bikecomputer2.custom_views.Speedometer;

public class ViewMain extends AppCompatActivity {

    private Speedometer speedometer;
    private InclinationLevel ilPitch;
    private InclinationLevel ilRoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        speedometer = (Speedometer) findViewById(R.id.speedometer);
        speedometer.setMaxSpeed(60);

        ilPitch = (InclinationLevel) findViewById(R.id.ilPitch);
        ilPitch.setMode(InclinationLevel.PITCH);

        ilRoll = (InclinationLevel) findViewById(R.id.ilRoll);
        ilRoll.setMode(InclinationLevel.ROLL);
    }
}
