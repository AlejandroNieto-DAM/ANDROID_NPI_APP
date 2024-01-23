package com.example.java_npi;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Configuration extends AppCompatActivity {

    EditText change_user, change_password;
    Button cambio;
    Spinner idiomas;
    public static String password = "password";
    public static List<String> username = new ArrayList<>();
    static {
        username.add("Alejandro");
        username.add("Dani");
        username.add("Jolie");
        username.add("Joel");
        username.add("Alli");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        change_password = (EditText) findViewById(R.id.editTextNuevaContrasena);
        cambio = (Button) findViewById(R.id.btnCambiarCredenciales);
        idiomas = (Spinner) findViewById(R.id.spinnerOpciones);

        cambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (change_password.getText().length() > 0) {

                    password = String.valueOf(change_password.getText());

                    Toast.makeText(getApplicationContext(), "Credenciales cambiadas", Toast.LENGTH_SHORT).show();
                    change_password.getText().clear();
                }

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.idiomas_array,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        idiomas.setAdapter(adapter);

        // Maneja las selecciones del Spinner
        idiomas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Acciones a realizar cuando se selecciona una opción
                String opcionSeleccionada = parentView.getItemAtPosition(position).toString();
                if(opcionSeleccionada.equals("Español")) {
                    MenuActivity.TTS.setLanguage(new Locale("es", "ES"));
                    //Toast.makeText(getApplicationContext(), "Idioma cambiado a: " + opcionSeleccionada, Toast.LENGTH_SHORT).show();
                } else if (opcionSeleccionada.equals("English")) {
                    MenuActivity.TTS.setLanguage(new Locale("en", "US"));
                    //Toast.makeText(getApplicationContext(), "Idioma cambiado a: " + opcionSeleccionada, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Acciones a realizar cuando no se selecciona ninguna opción
            }
        });

        /*
        idiomas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Acciones a realizar cuando se selecciona una opción
                String opcionSeleccionada = parent.getItemAtPosition(position).toString();
                if(opcionSeleccionada.equals("Español")) {
                    MenuActivity.TTS.setLanguage(new Locale("es", "ES"));
                    //Toast.makeText(getApplicationContext(), "Idioma cambiado a: " + opcionSeleccionada, Toast.LENGTH_SHORT).show();
                } else if (opcionSeleccionada.equals("English")) {
                    MenuActivity.TTS.setLanguage(new Locale("en", "US"));
                    //Toast.makeText(getApplicationContext(), "Idioma cambiado a: " + opcionSeleccionada, Toast.LENGTH_SHORT).show();
                }
            }
        });*/


    }
}