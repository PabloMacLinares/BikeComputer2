package com.blc.bikecomputer2.views.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

import com.blc.bikecomputer2.R;
import com.blc.bikecomputer2.custom_views.Compass;
import com.blc.bikecomputer2.custom_views.InclinationLevel;
import com.blc.bikecomputer2.custom_views.Speedometer;
import com.blc.bikecomputer2.pojo.InfoElement;
import com.blc.bikecomputer2.sensors.BatterySensor;
import com.blc.bikecomputer2.sensors.CompassSensor;
import com.blc.bikecomputer2.sensors.LightSensor;
import com.blc.bikecomputer2.sensors.MSensor;
import com.blc.bikecomputer2.sensors.RotationSensor;

public class ViewMain extends AppCompatActivity{

    private LightSensor lightSensor;
    private CompassSensor compassSensor;
    private RotationSensor rotationSensor;
    private BatterySensor batterySensor;
    private InfoElement lightInfo;

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

        batterySensor = new BatterySensor(this);

        speedometer = (Speedometer) findViewById(R.id.speedometer);
        speedometer.setMaxSpeed(60);

        lightInfo = new InfoElement("", R.drawable.ic_brightness_24dp, this);
        speedometer.addInfoElement(lightInfo);
        speedometer.addInfoElement(new InfoElement("PlaceHolder 1", R.drawable.ic_radio_button_checked_24dp, this));
        speedometer.addInfoElement(new InfoElement("PlaceHolder 2", R.drawable.ic_radio_button_checked_24dp, this));

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
                lightInfo.setText(value + "");
                speedometer.refresh();
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

        batterySensor.setOnValueChangeListener(new BatterySensor.OnValueChangeListener() {
            @Override
            public void valueChanged(int value) {
                speedometer.setCurrBattery(value);
            }
        });

        ilPitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotationCalibrated = false;
            }
        });

        ilRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotationCalibrated = false;
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
        batterySensor.registerListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        lightSensor.unregisterListener();
        compassSensor.unregisterListener();
        rotationSensor.unregisterListener();
        rotationCalibrated = false;
        batterySensor.unregisterListener();
    }

    private int getScreenOrientation(){
        return ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
    }
}
