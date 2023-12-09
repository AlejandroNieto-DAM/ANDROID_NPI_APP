package com.example.java_npi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    LinearLayout qrOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        qrOption = (LinearLayout) findViewById(R.id.qr_option);


        qrOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoggedActivity.class);
                intent.putExtra(MainActivity.EXTRA_MESSAGE, MainActivity.user+"@"+MainActivity.pass);

                startActivity(intent);

            }
        });

    }
}
