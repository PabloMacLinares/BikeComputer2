package com.blc.bikecomputer2.sensors;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

/**
 * Created by Pablo on 12/01/2017.
 */

public class BatterySensor extends BroadcastReceiver {

    private Activity activity;
    private int batteryLevel;
    private OnValueChangeListener valueChangeListener;

    public BatterySensor(){
        this.activity = null;
    }

    public BatterySensor(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        if(valueChangeListener != null){
            valueChangeListener.valueChanged(batteryLevel);
        }
    }

    public void registerListener() {
        activity.registerReceiver(this, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    public void unregisterListener() {
        activity.unregisterReceiver(this);
    }

    public interface OnValueChangeListener{
        public void valueChanged(int value);
    }

    public void setOnValueChangeListener(OnValueChangeListener listener){
        this.valueChangeListener = listener;
    }
}
