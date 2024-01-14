package com.example.java_npi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class HorarioClase extends AppCompatActivity {

    TextView className, day, first, second, thrid, fourth, fifth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horario_layout);


        className = (TextView) findViewById(R.id.id_clase);
        className.setText(ReadNFCTag.selectedClass);

        day = (TextView) findViewById(R.id.day_l);
        first = (TextView) findViewById(R.id.first_assg);
        second = (TextView) findViewById(R.id.second_assig);
        thrid = (TextView) findViewById(R.id.thrid_assig);
        fourth = (TextView) findViewById(R.id.fourth_assig);
        fifth = (TextView) findViewById(R.id.fifth_assig);

        if(ReadNFCTag.selectedClass.contains("2")){
            day.setText("MARTES");
            first.setText("Lenguaje de marcas");
            second.setText("Fundamentos de redes");
            thrid.setText("Informática Gráfica");
            fourth.setText("Seguridad y sistemas");
            fifth.setText("Fundamentos de Software");

        }




    }

}
