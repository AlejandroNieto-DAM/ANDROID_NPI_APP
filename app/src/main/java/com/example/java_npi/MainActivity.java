package com.example.java_npi;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static String user = "";
    public static String pass = "";

    EditText username;
    EditText password;

    Button loginBton;
    Button huellaBton;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.usernameInfo);
        password = (EditText) findViewById(R.id.passwordInfo);
        loginBton = (Button) findViewById(R.id.loginButton);
        huellaBton = (Button) findViewById(R.id.fingerprintButton);


        loginBton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.user = String.valueOf(username.getText());
                MainActivity.pass = String.valueOf(password.getText());

                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);

                startActivity(intent);


            }
        });

        huellaBton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.biometric.BiometricPrompt.PromptInfo promptInfo = new androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Acceso biométrico")
                        .setDescription("Se requiere autenticación del usuario para continuar")
                        .setNegativeButtonText("Cancelar")
                        .build();
                getPrompt().authenticate(promptInfo);
            }
        });

    }

    private BiometricPrompt getPrompt() {
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                notifyUser(errString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                notifyUser("Acceso aceptado");
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                notifyUser("Acceso denegado");
            }
        };
        //androidx o hardware
        androidx.biometric.BiometricPrompt biometricPrompt = new androidx.biometric.BiometricPrompt(this,executor,callback);
        return biometricPrompt;
    }

    private void notifyUser(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}