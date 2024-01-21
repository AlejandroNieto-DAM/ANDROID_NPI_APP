package com.example.java_npi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Configuration extends AppCompatActivity {

    EditText change_user, change_password;
    Button cambio;
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

    }
}