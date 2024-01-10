package com.example.java_npi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    LinearLayout qrOption;

    LinearLayout locationsOption;


    LinearLayout administration;
    LinearLayout brujula;

    LinearLayout reservar_menu;

    LinearLayout gestos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        qrOption = (LinearLayout) findViewById(R.id.qr_option);
        locationsOption = (LinearLayout) findViewById(R.id.locations_option);
        administration = (LinearLayout) findViewById(R.id.administration);
        brujula = (LinearLayout) findViewById(R.id.brujula);
        reservar_menu = (LinearLayout) findViewById(R.id.reservar_menu);
        gestos = (LinearLayout) findViewById(R.id.gestos);


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

        brujula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CompassActivity.class);
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

        gestos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GestureActivity.class);
                startActivity(intent);
            }
        });

    }
}
