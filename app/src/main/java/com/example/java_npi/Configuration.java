package com.example.java_npi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Configuration extends AppCompatActivity {

    EditText change_user, change_password;
    Button cambio;
    public static String password = "password";
    public static String username = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        change_user = (EditText) findViewById(R.id.editTextNuevoUsuario);
        change_password = (EditText) findViewById(R.id.editTextNuevaContrasena);
        cambio = (Button) findViewById(R.id.btnCambiarCredenciales);

        cambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (change_user.getText().length() > 0 && change_password.getText().length() > 0) {

                    username = String.valueOf(change_user.getText());
                    password = String.valueOf(change_password.getText());

                    Toast.makeText(getApplicationContext(), "Credenciales cambiadas", Toast.LENGTH_SHORT).show();
                    change_user.getText().clear();
                    change_password.getText().clear();
                }

            }
        });

    }
}