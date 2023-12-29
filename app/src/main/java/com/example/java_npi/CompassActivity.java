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

    private double firstPoint[] = {37.49145861410788, -2.7660319406782694};
    private double secondPoint[] = {37.49277984991167, -2.765444752479533};

    private static final float ROTATION_THRESHOLD = 1.0f; // Umbral de rotación mínimo para actualizar la vista
    private static final float ALPHA = 0.25f; // Factor de suavizado para el filtro de paso bajo

    private float[] accelerometerReading = new float[3];
    private float[] magnetometerReading = new float[3];

    private float[] rotationMatrix = new float[9];
    private float[] orientationValues = new float[3];

    private float currentDegree = 0f;


    private ImageView planoImageView;
    private ImageView brujulaImageView;
    private TextView edificioTextView;

    private SensorManager sensorManager;
    private Sensor magnetometer;
    private Sensor accelerometer;

    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compass_layout);

        planoImageView = findViewById(R.id.planoImageView);
        brujulaImageView = findViewById(R.id.brujulaImageView);
        edificioTextView = findViewById(R.id.edificioTextView);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (magnetometer != null && accelerometer != null) {
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // Manejar el caso en que el dispositivo no tiene sensores magnéticos o acelerómetros
        }
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            lowPassFilter(event.values, accelerometerReading);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            lowPassFilter(event.values, magnetometerReading);
        }

        updateOrientation();
    }

    private void lowPassFilter(float[] input, float[] output) {
        for (int i = 0; i < input.length; i++) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for this example
    }

    private void updateOrientation() {
        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading);
        SensorManager.getOrientation(rotationMatrix, orientationValues);

        float azimuthInRadians = orientationValues[0];
        float azimuthInDegrees = (float) Math.toDegrees(azimuthInRadians);

        double direction = getDirection(azimuthInDegrees);
        currentDegree = (float) -direction;

        if (Math.abs(currentDegree - direction) > ROTATION_THRESHOLD) {
            RotateAnimation rotateAnimation = new RotateAnimation(
                    currentDegree,
                    (float) -direction,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);

            rotateAnimation.setDuration(250);
            rotateAnimation.setFillAfter(true);
            brujulaImageView.startAnimation(rotateAnimation);


        }
    }

    private double getDirection(float azimuthInDegrees) {
        double bearing = getBearing(firstPoint[0], firstPoint[1], secondPoint[0], secondPoint[1]);
        return azimuthInDegrees - (float) bearing;
    }

    private double getBearing(double startLat, double startLong, double endLat, double endLong) {
        double deltaLong = endLong - startLong;
        double x = Math.cos(Math.toRadians(endLat)) * Math.sin(Math.toRadians(deltaLong));
        double y = Math.cos(Math.toRadians(startLat)) * Math.sin(Math.toRadians(endLat)) -
                Math.sin(Math.toRadians(startLat)) * Math.cos(Math.toRadians(endLat)) * Math.cos(Math.toRadians(deltaLong));

        double bearing = Math.atan2(x, y);
        bearing = Math.toDegrees(bearing);
        bearing = (bearing + 360) % 360;

        return bearing;
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null && magnetometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
}


