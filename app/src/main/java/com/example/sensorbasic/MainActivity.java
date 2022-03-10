package com.example.sensorbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;

    private TextView mTextLightSensor;
    private TextView mTextProximitySensor;

    private Sensor mLightSensor;
    private Sensor mProximitySensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        for(Sensor currentSensor : sensorList) {
            Log.d("Sensor : ", currentSensor.getName());
        }

        mTextLightSensor = findViewById(R.id.light_label);
        mTextProximitySensor = findViewById(R.id.proximity_label);

        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mLightSensor != null) {
            mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mProximitySensor != null) {
            mSensorManager.registerListener(this, mProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
            int type = sensorEvent.sensor.getType();
            float result = sensorEvent.values[0];
            switch (type) {

                case Sensor.TYPE_LIGHT:
                    mTextLightSensor.setText(getResources().getString(R.string.light_text, result));
                    break;

                case Sensor.TYPE_PROXIMITY:
                    mTextProximitySensor.setText(getResources().getString(R.string.proximity_text, result));
                    break;

                default:
                    break;
            }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}