package com.example.java_npi;

import android.gesture.Gesture;
import android.gesture.Prediction;
import android.os.Bundle;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GestureActivity extends AppCompatActivity implements OnGesturePerformedListener {

    private GestureLibrary objGestureList;

    Button prueba;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesture_activity);

        prueba = (Button) findViewById(R.id.btn_prueba);

        objGestureList = GestureLibraries.fromRawResource(this, R.raw.gesture);
        if(!objGestureList.load()){
            finish();
        }

        prueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Funciona");
            }
        });

        GestureOverlayView objGestureOverlay = (GestureOverlayView) findViewById(R.id.gesture_layout);
        objGestureOverlay.addOnGesturePerformedListener(this);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> objPrediction = objGestureList.recognize(gesture);
        if(objPrediction.size() > 0 && objPrediction.get(0).score > 1){
            String gestureName = objPrediction.get(0).name;
            Toast.makeText(this, gestureName, Toast.LENGTH_SHORT).show();
        }
    }
}
