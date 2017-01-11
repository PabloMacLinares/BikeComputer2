package com.blc.bikecomputer2.sensors;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

/**
 * Created by Pablo on 11/01/2017.
 */

public class LightSensor extends MSensor<Float> {

    private Sensor lightSensor;
    private float illuminance;
    private OnValueChangeListener<Float> valueChangeListener;

    public LightSensor(Activity activity) {
        super(activity);
        lightSensor = this.getSensorManager().getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    public void registerListener() {
        this.registerListener(lightSensor);
    }

    @Override
    public void unregisterListener() {
        this.unregisterListener(lightSensor);
    }

    @Override
    public Float getValue() {
        return illuminance;
    }

    @Override
    public void setOnValueChangeListener(OnValueChangeListener<Float> listener) {
        this.valueChangeListener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        illuminance = sensorEvent.values[0];
        if(valueChangeListener != null){
            valueChangeListener.valueChanged(illuminance);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
