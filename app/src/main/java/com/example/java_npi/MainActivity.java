package com.example.java_npi;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static String user = "";
    public static String pass = "";

    EditText username;
    EditText password;

    Button loginBton;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.usernameInfo);
        password = (EditText) findViewById(R.id.passwordInfo);
        loginBton = (Button) findViewById(R.id.loginButton);

        loginBton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.user = String.valueOf(username.getText());
                MainActivity.pass = String.valueOf(password.getText());

                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);

                startActivity(intent);


            }
        });

    }

}