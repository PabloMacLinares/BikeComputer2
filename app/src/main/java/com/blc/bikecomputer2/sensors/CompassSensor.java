package com.blc.bikecomputer2.sensors;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

/**
 * Created by Pablo on 11/01/2017.
 */

public class CompassSensor extends MSensor<Float> {

    private Sensor orSensor;
    private float compassBearing;
    private OnValueChangeListener<Float> valueChangeListener;

    public CompassSensor(Activity activity) {
        super(activity);
        orSensor = this.getSensorManager().getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    @Override
    public void registerListener() {
        this.registerListener(orSensor);
    }

    @Override
    public void unregisterListener() {
        this.unregisterListener(orSensor);
    }

    @Override
    public Float getValue() {
        return compassBearing;
    }

    @Override
    public void setOnValueChangeListener(OnValueChangeListener<Float> listener) {
        this.valueChangeListener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        compassBearing = Math.round(sensorEvent.values[0]);
        if(valueChangeListener != null){
            valueChangeListener.valueChanged(compassBearing);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
