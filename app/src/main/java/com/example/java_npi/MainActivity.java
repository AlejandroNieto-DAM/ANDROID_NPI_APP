package com.example.java_npi;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.Toast;

import java.sql.SQLOutput;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

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
                String usrName = String.valueOf(username.getText());
                String pass = String.valueOf(password.getText());

                //Toast.makeText(getApplicationContext(), usrName, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), LoggedActivity.class);
                intent.putExtra(EXTRA_MESSAGE, usrName+"@"+pass);

                startActivity(intent);
            }
        });

    }

}