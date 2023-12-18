package com.example.java_npi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Becas extends AppCompatActivity {

    CardView becas_ugr, becas_ministerio, becas_junta;

    WebView web;
    WebSettings webSettings;
    boolean onTheWeb = false;
    String becas_ugr_url = "https://ve.ugr.es/servicios/becas/becas-y-ayudas/becas-propias";
    String becas_junta_url = "https://www.juntadeandalucia.es/temas/estudiar/becas/junta-andalucia.html";
    String becas_ministerio_url = "https://www.educacionyfp.gob.es/servicios-al-ciudadano/catalogo/estudiantes/becas-ayudas.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_becas);

        becas_ugr =findViewById(R.id.becasugr);
        becas_ministerio =findViewById(R.id.becasministerio);
        becas_junta =findViewById(R.id.becasjunta);

        // cargar la clase web
        web = new WebView(getApplicationContext());
        //para visualizar la web sin problemas
        webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);

        becas_ugr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTheWeb = true;
                setContentView(web);
                web.loadUrl(becas_ugr_url);
            }
        });

        becas_junta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTheWeb = true;
                setContentView(web);
                web.loadUrl(becas_junta_url);
            }
        });

        becas_ministerio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTheWeb = true;
                setContentView(web);
                web.loadUrl(becas_ministerio_url);
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
    //a la pagina anterior en lugar de a Administration. Para ello esta onTheWeb y estos métodos
    @Override
    public void onBackPressed(){
        if(onTheWeb){
            onRestart();
        }else{
            Intent principal = new Intent(getApplicationContext(), Administration.class);
            finish();
            startActivity(principal);
        }

    }
}