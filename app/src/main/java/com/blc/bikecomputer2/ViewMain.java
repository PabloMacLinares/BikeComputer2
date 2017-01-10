package com.blc.bikecomputer2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blc.bikecomputer2.custom_views.Compass;
import com.blc.bikecomputer2.custom_views.InclinationLevel;
import com.blc.bikecomputer2.custom_views.Speedometer;

public class ViewMain extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private float currentDegree = 0f;

    private Speedometer speedometer;
    private InclinationLevel ilPitch;
    private InclinationLevel ilRoll;
    private Compass compass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        speedometer = (Speedometer) findViewById(R.id.speedometer);
        speedometer.setMaxSpeed(60);

        ilPitch = (InclinationLevel) findViewById(R.id.ilPitch);
        ilPitch.setMode(InclinationLevel.PITCH);

        ilRoll = (InclinationLevel) findViewById(R.id.ilRoll);
        ilRoll.setMode(InclinationLevel.ROLL);

        compass = (Compass) findViewById(R.id.compass);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float degree = Math.round(sensorEvent.values[0]);
        System.out.println(degree);

        compass.setAngle((int) degree);

        currentDegree = degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
