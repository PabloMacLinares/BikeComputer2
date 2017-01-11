package com.blc.bikecomputer2.views.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.WindowManager;

import com.blc.bikecomputer2.R;
import com.blc.bikecomputer2.custom_views.Compass;
import com.blc.bikecomputer2.custom_views.InclinationLevel;
import com.blc.bikecomputer2.custom_views.Speedometer;
import com.blc.bikecomputer2.sensors.CompassSensor;
import com.blc.bikecomputer2.sensors.LightSensor;
import com.blc.bikecomputer2.sensors.MSensor;
import com.blc.bikecomputer2.sensors.RotationSensor;

public class ViewMain extends AppCompatActivity{

    private LightSensor lightSensor;
    private CompassSensor compassSensor;
    private RotationSensor rotationSensor;

    private Speedometer speedometer;
    private InclinationLevel ilPitch;
    private InclinationLevel ilRoll;
    private Compass compass;

    private boolean rotationCalibrated = false;
    private float startPitch;
    private float startRoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initEvents();
    }

    private void init(){
        lightSensor = new LightSensor(this);

        compassSensor = new CompassSensor(this);

        rotationSensor = new RotationSensor(this);

        speedometer = (Speedometer) findViewById(R.id.speedometer);
        speedometer.setMaxSpeed(60);

        ilPitch = (InclinationLevel) findViewById(R.id.ilPitch);
        ilPitch.setMode(InclinationLevel.PITCH);

        ilRoll = (InclinationLevel) findViewById(R.id.ilRoll);
        ilRoll.setMode(InclinationLevel.ROLL);

        compass = (Compass) findViewById(R.id.compass);
    }

    private void initEvents(){
        lightSensor.setOnValueChangeListener(new MSensor.OnValueChangeListener<Float>() {
            @Override
            public void valueChanged(Float value) {
                //System.out.println("Illuminance: " + value);
            }
        });

        compassSensor.setOnValueChangeListener(new MSensor.OnValueChangeListener<Float>() {
            @Override
            public void valueChanged(Float value) {
                int orientation = getScreenOrientation();
                if(orientation == Surface.ROTATION_0 || orientation == Surface.ROTATION_180){
                    compass.setAngle(-value);
                }else if(orientation == Surface.ROTATION_90) {
                    compass.setAngle(-value - 90);
                }
            }
        });

        rotationSensor.setOnValueChangeListener(new MSensor.OnValueChangeListener<float[]>() {
            @Override
            public void valueChanged(float[] value) {
                if(rotationCalibrated) {
                    ilPitch.setAngle(startPitch - value[1]);
                    ilRoll.setAngle(startRoll - value[2]);
                }else{
                    startPitch = value[1];
                    startRoll = value[2];
                    rotationCalibrated = true;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        lightSensor.registerListener();
        compassSensor.registerListener();
        rotationSensor.registerListener();
        rotationCalibrated = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        lightSensor.unregisterListener();
        compassSensor.unregisterListener();
        rotationSensor.unregisterListener();
        rotationCalibrated = false;
    }

    private int getScreenOrientation(){
        return ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
    }
}
