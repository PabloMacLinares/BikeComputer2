package com.blc.bikecomputer2.sensors;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

/**
 * Created by Pablo on 11/01/2017.
 */

public class RotationSensor extends MSensor<float[]> {

    private Sensor rotSensor;
    private float[] rotation = new float[3];
    private OnValueChangeListener<float[]> valueChangeListener;

    public RotationSensor(Activity activity) {
        super(activity);
        rotSensor = this.getSensorManager().getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    @Override
    public void registerListener() {
        this.registerListener(rotSensor);
    }

    @Override
    public void unregisterListener() {
        this.unregisterListener(rotSensor);
    }

    @Override
    public float[] getValue() {
        return rotation;
    }

    @Override
    public void setOnValueChangeListener(OnValueChangeListener<float[]> listener) {
        this.valueChangeListener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] mRotationMatrix = new float[16];
        this.getSensorManager().getRotationMatrixFromVector(mRotationMatrix, sensorEvent.values);
        this.getSensorManager().remapCoordinateSystem(
                mRotationMatrix,
                this.getSensorManager().AXIS_X,
                this.getSensorManager().AXIS_Z,
                mRotationMatrix
        );
        this.getSensorManager().getOrientation(mRotationMatrix, rotation);

        rotation[0] = (float) Math.toDegrees(rotation[0]);
        rotation[1] = (float) Math.toDegrees(rotation[1]);
        rotation[2] = (float) Math.toDegrees(rotation[2]);

        if(valueChangeListener != null){
            valueChangeListener.valueChanged(rotation);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
