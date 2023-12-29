package com.example.java_npi;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView brujulaView;
    private float currentDegree = 0f;

    private SensorManager sensorManager;
    private Sensor magnetometer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compass_layout);

        brujulaView = findViewById(R.id.brujulaImageView);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if (magnetometer != null) {
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // Manejar el caso en que el dispositivo no tiene sensor magnético
        }
    }

    private void updateOrientation(float degree) {

        brujulaView.setRotation((-degree + 90)%360);

        currentDegree = -degree;
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float[] magneticValues = event.values;
            float degree = calculateDegree(magneticValues);

            // Actualizar la orientación cuando cambia el sensor magnético
            updateOrientation(degree);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No es necesario para este ejemplo
    }

    private float calculateDegree(float[] magneticValues) {
        float degree = (float) Math.toDegrees(Math.atan2(
                magneticValues[1],
                magneticValues[0]));

        // Ajustar el ángulo para que esté en el rango [0, 360)
        degree = (degree + 360) % 360;

        return degree;
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        if (magnetometer != null) {
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
}