package com.example.java_npi;

import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements OnGesturePerformedListener  {

    private GestureLibrary objGestureList;

    LinearLayout qrOption, locationsOption, administration, brujula, reservar_menu, gestos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        objGestureList = GestureLibraries.fromRawResource(this, R.raw.gesture);
        if(!objGestureList.load()){
            finish();
        }

        GestureOverlayView objGestureOverlay = (GestureOverlayView) findViewById(R.id.gesture_layout);
        objGestureOverlay.addOnGesturePerformedListener(this);

        qrOption = (LinearLayout) findViewById(R.id.qr_option);
        locationsOption = (LinearLayout) findViewById(R.id.locations_option);
        administration = (LinearLayout) findViewById(R.id.administration);
        reservar_menu = (LinearLayout) findViewById(R.id.reservar_menu);


        qrOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoggedActivity.class);
                intent.putExtra(MainActivity.EXTRA_MESSAGE, MainActivity.user+"@"+MainActivity.pass);

                startActivity(intent);

            }
        });

        locationsOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Locations.class);
                startActivity(intent);
            }
        });


        administration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Administration.class);
                startActivity(intent);
            }
        });


        reservar_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScanQR.class);
                startActivity(intent);
            }
        });


    }
    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> objPrediction = objGestureList.recognize(gesture);
        if(objPrediction.size() > 0 && objPrediction.get(0).score > 1){
            String gestureName = objPrediction.get(0).name;
            Toast.makeText(this, gestureName, Toast.LENGTH_SHORT).show();
            if (gestureName.contains("Daltonismo")){
                String a = "Sisi";
                Toast.makeText(this, "Changing to daltonic mode", Toast.LENGTH_SHORT).show();

            }

        }
    }
}
