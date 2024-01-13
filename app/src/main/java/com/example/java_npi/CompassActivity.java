package com.example.java_npi;


import static com.example.java_npi.CalculateAngle.calculateAngle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView compassImageView;
    private TextView directionTextView;
    private TextView site;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;

    private float[] lastAccelerometer = new float[3];
    private float[] lastMagnetometer = new float[3];
    private boolean lastAccelerometerSet = false;
    private boolean lastMagnetometerSet = false;

    private float currentDegree = 0f;

    private double anglePos;

    private static final float MIN_ANGLE_CHANGE = 5.0f;
    private float lastAzimuth = 0;

    private ArrayList<Nodo> caminoASeguir;
    private int posCamino = 0;

    Button nextStep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compass_layout);

        //caminoASeguir = new ArrayList<Nodo>();


        site = findViewById(R.id.siteText);
        site.setText(Locations.selectedSite);
        compassImageView = findViewById(R.id.brujulaImageView);
        directionTextView = findViewById(R.id.infoTextView);
        nextStep = findViewById(R.id.btn_step);

        caminoASeguir = Locations.generarCamino;

        anglePos = calculateAngle(caminoASeguir.get(posCamino), caminoASeguir.get(posCamino + 1));
        directionTextView.setText(caminoASeguir.get(posCamino).info);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        }

        if (accelerometer != null && magnetometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (posCamino < caminoASeguir.size() - 2) {
                    posCamino+=1;
                    anglePos = calculateAngle(caminoASeguir.get(posCamino), caminoASeguir.get(posCamino + 1));
                    directionTextView.setText(caminoASeguir.get(posCamino).info);
                } else if (posCamino == caminoASeguir.size() - 2) {
                    directionTextView.setText(caminoASeguir.get(posCamino + 1).info);
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, lastAccelerometer, 0, event.values.length);
            lastAccelerometerSet = true;
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, lastMagnetometer, 0, event.values.length);
            lastMagnetometerSet = true;
        }


        if (lastAccelerometerSet && lastMagnetometerSet) {
            updateDirection();
        }


    }

    private void updateDirection() {
        float[] rotationMatrix = new float[9];
        float[] orientationValues = new float[3];

        if (SensorManager.getRotationMatrix(rotationMatrix, null, lastAccelerometer, lastMagnetometer)) {
            SensorManager.getOrientation(rotationMatrix, orientationValues);

            float azimuth = (float) Math.toDegrees(orientationValues[0]);
            azimuth = (azimuth + 360) % 360;

            RotateAnimation rotateAnimation = new RotateAnimation(
                    currentDegree,
                    -azimuth,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);

            rotateAnimation.setDuration(500);
            rotateAnimation.setFillAfter(true);

            compassImageView.startAnimation(rotateAnimation);

            if (anglePos > 0 && anglePos < 90)
                currentDegree = (-azimuth - (int) anglePos) % 360;
            if (anglePos > 90 && anglePos < 180)
                currentDegree = (-azimuth + (int) anglePos) % 360;
            if (anglePos > 180 && anglePos < 270)
                currentDegree = (-azimuth - (int) anglePos) % 360;
            if (anglePos > 270 && anglePos < 360)
                currentDegree = (-azimuth + (int) anglePos) % 360;

            lastAzimuth = azimuth;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No es necesario para este ejemplo
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
