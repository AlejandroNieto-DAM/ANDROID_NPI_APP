package com.example.java_npi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Administration extends  AppCompatActivity{

    LinearLayout becasOption;
    LinearLayout movilidadOption;
    LinearLayout oficinaOption;
    LinearLayout sedeOption;

    WebView web;
    WebSettings webSettings;
    boolean onTheWeb = false;
    String movilidad_url = "https://internacional.ugr.es/pages/movilidad";
    String sedeUGR_url = "https://sede.ugr.es/";
    String oficina_url = "https://oficinavirtual.ugr.es/ai/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration);

        becasOption = (LinearLayout) findViewById(R.id.becas);
        movilidadOption = (LinearLayout) findViewById(R.id.movilidad);
        sedeOption = (LinearLayout) findViewById(R.id.sede_electronica);
        oficinaOption = (LinearLayout) findViewById(R.id.oficina_virtual);


        // cargar la clase web
        web = new WebView(getApplicationContext());
        //para visualizar la web sin problemas
        webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);

        becasOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Becas.class);
                startActivity(intent);
            }
        });

        movilidadOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTheWeb = true;
                setContentView(web);
                web.loadUrl(movilidad_url);
            }
        });

        sedeOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTheWeb = true;
                setContentView(web);
                web.loadUrl(sedeUGR_url);
            }
        });

        oficinaOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTheWeb = true;
                setContentView(web);
                web.loadUrl(oficina_url);
            }
        });


    }

    @Override
    public  void onRestart(){
        super.onRestart();
        Intent intent =getIntent();
        finish();
        startActivity(intent);
    }

    //la web se carga sobre esta actividad. Si estamos en la web y hacemos back volvemos
    //a la pagina anterior en lugar de a Administration. Para ello esta onTheWeb y estos metodos
    @Override
    public void onBackPressed(){
        if(onTheWeb){
            onRestart();
        }else{
            Intent principal = new Intent(getApplicationContext(), MenuActivity.class);
            finish();
            startActivity(principal);
        }

    }


}