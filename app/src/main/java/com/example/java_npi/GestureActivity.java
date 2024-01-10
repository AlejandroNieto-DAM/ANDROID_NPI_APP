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
import android.widget.Toast;

public class GestureActivity extends AppCompatActivity implements OnGesturePerformedListener {

    private GestureLibrary objGestureList;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesture_activity);

        objGestureList = GestureLibraries.fromRawResource(this, R.raw.gesture);
        if(!objGestureList.load()){
            finish();
        }

        GestureOverlayView objGestureOverlay = (GestureOverlayView) findViewById(R.id.gesture_layout);
        objGestureOverlay.addOnGesturePerformedListener(this);
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
