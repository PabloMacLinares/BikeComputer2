package com.blc.bikecomputer2.sensors;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Pablo on 11/01/2017.
 */

public abstract class MSensor<T> implements SensorEventListener{

    private Activity activity;
    private SensorManager sensorManager;

    public MSensor(Activity activity){
        this.activity = activity;
        this.sensorManager = (SensorManager) activity.getSystemService(activity.getApplicationContext().SENSOR_SERVICE);
    }

    public Activity getActivity() {
        return activity;
    }

    public SensorManager getSensorManager() {
        return sensorManager;
    }

    public abstract void registerListener();

    protected void registerListener(Sensor sensor){
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public abstract void unregisterListener();

    protected void unregisterListener(Sensor sensor){
        sensorManager.unregisterListener(this, sensor);
    }

    public abstract T getValue();

    public interface OnValueChangeListener<T>{
        public void valueChanged(T value);
    }

    public abstract void setOnValueChangeListener(OnValueChangeListener<T> listener);
}
