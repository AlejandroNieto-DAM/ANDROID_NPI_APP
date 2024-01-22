package com.example.java_npi;

import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.Locale;

import io.kommunicate.KmConversationBuilder;
import io.kommunicate.Kommunicate;
import io.kommunicate.callbacks.KMLogoutHandler;
import io.kommunicate.callbacks.KmCallback;

public class MenuActivity extends AppCompatActivity implements OnGesturePerformedListener  {

    private GestureLibrary objGestureList;
    public static boolean developerMode = false;

    public static TextToSpeech TTS;

    LinearLayout qrOption, locationsOption, administration, reservar_menu, read_nfc, configuration;

    ImageView toolbar;

    /**
     * Constructor de la clase MenuActivity donde se manejan los eventos para los distintos botones
     * que mostramos en el layout que son las distintas opciones del menú
     * También tenemos en cuenta el gesto que pueda hacer el usuario para entrar en developer mode
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);


        toolbar = findViewById(R.id.VoiceIcon1);

        objGestureList = GestureLibraries.fromRawResource(this, R.raw.gesture);
        if(!objGestureList.load()){
            finish();
        }

        GestureOverlayView objGestureOverlay = (GestureOverlayView) findViewById(R.id.gesture_layout);
        objGestureOverlay.addOnGesturePerformedListener(this);

        qrOption = (LinearLayout) findViewById(R.id.qr_option);
        locationsOption = (LinearLayout) findViewById(R.id.locations_option);
        administration = (LinearLayout) findViewById(R.id.administration);
        reservar_menu = (LinearLayout) findViewById(R.id.reservar_menu);
        read_nfc = (LinearLayout) findViewById(R.id.readNfc);
        configuration = (LinearLayout) findViewById(R.id.configuracion);

        qrOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoggedActivity.class);
                intent.putExtra(MainActivity.EXTRA_MESSAGE, MainActivity.user+"@"+MainActivity.pass);

                startActivity(intent);

            }
        });

        locationsOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Locations.class);
                startActivity(intent);
            }
        });


        administration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Administration.class);
                startActivity(intent);
            }
        });


        reservar_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), ScanQR.class);
                //startActivity(intent);

                scanCode();
            }
        });

        read_nfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReadNFCTag.class);
                startActivity(intent);
            }
        });

        configuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Configuration.class);
                startActivity(intent);
            }
        });

        TTS = new TextToSpeech(this, new OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    TTS.setLanguage(new Locale("es", "ES"));
                }
            }
        });
    }

    /**
     * Inicia una nueva conversación con el chatbot Kommunicate y abre las conversaciones
     * redirigiendote a su pantalla.
     * Una vez finalizado cierra y borra la conversación.
     * @param view
     */
    public void chatBotClick(View view) {

        Kommunicate.init(this, "1e5a2696e1001f90c1312f0cf83e2b37b");

        new KmConversationBuilder(this)
                .createConversation(new KmCallback() {
                    @Override
                    public void onSuccess(Object message) {
                        String conversationId = message.toString();
                    }

                    @Override
                    public void onFailure(Object error) {
                        Log.d("ConversationTest", "Error : " + error);
                    }
                });

        // Open the Kommunicate chat widget
        Kommunicate.openConversation(this);

        logoutAndClearConversations();

    }

    /**
     * Borra las conversaciones que tiene el usuario con el bot, esto es debido
     * a que si no se borrasen se quedarían guardadas todas las conversaciones
     */
    private void logoutAndClearConversations() {
        Kommunicate.logout(this, new KMLogoutHandler() {
            @Override
            public void onSuccess(Context context) {
                Log.i("Logout","Success");
            }

            @Override
            public void onFailure(Exception exception) {
                Log.i("Logout","Failed");

            }
        });

    }

    /**
     * Inicia la camara para poder escanear un QR
     */
    private void scanCode(){
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volumen arriba para activar flash");
        //options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result->
    {
        if(result.getContents() !=null)
        {
            if(result.getContents().contains("Menu")){
                Intent intent = new Intent(getApplicationContext(), MenuReservadoExito.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "QR no reconocido, pruebe de nuevo", Toast.LENGTH_LONG).show();
            }
        }
    });

    /**
     * Inicia el reconocedor de voz de google para
     * iniciar de esta forma el asistente por voz.
     * @param view
     */
    public void onVoiceButtonClick(View view) {
        // Perform actions on back button click (e.g., navigate back)
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES"); // Set Spanish language
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora");
        startActivityForResult(intent, 111);
    }



    /**
     * Una vez reconocido el texto a través del asistente de voz aqui es donde ejecutamos las
     * distintas operaciones a realizar.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 111 && resultCode == RESULT_OK) {
            // Obtener la lista de resultados
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            // Mostrar el primer resultado (la transcripción de voz)
            if (result != null && result.size() > 0) {
                String transcripcion = result.get(0);
                boolean entendido = false;

                //Toast.makeText(this, "Commando no entendido: " + transcripcion, Toast.LENGTH_LONG).show();

                String command = transcripcion.toLowerCase();
                //Check si contiene keywords
                if (command.contains("trámites") || command.contains("administración")){
                    entendido = true;
                    Intent intent = new Intent(getApplicationContext(), Administration.class);
                    startActivity(intent);
                }
                if (command.contains("menu")){
                    entendido = true;
                    scanCode();
                }
                if (command.contains("configuración")) {
                    entendido = true;
                    Intent intent = new Intent(getApplicationContext(), Configuration.class);
                    startActivity(intent);
                }
                if (command.contains("lugar") || command.contains("mapa") || command.contains("como llegar a")) {
                    entendido = true;
                    Intent intent = new Intent(getApplicationContext(), Locations.class);
                    startActivity(intent);
                }
                if (command.contains("nfc")) {
                    entendido = true;
                    Intent intent = new Intent(getApplicationContext(), ReadNFCTag.class);
                    startActivity(intent);
                }
                if (command.contains("qr")) {
                    entendido = true;
                    Intent intent = new Intent(getApplicationContext(), LoggedActivity.class);
                    intent.putExtra(MainActivity.EXTRA_MESSAGE, MainActivity.user+"@"+MainActivity.pass);

                    startActivity(intent);
                }
                if (!entendido){
                    TTS.speak("Perdone, no lo he entendido", TextToSpeech.QUEUE_FLUSH, null);
                }

            }
        }
    }

    /**
     * Possible features:
     *      getting somewhere: Ask how to get to: "Lugares"
     *          -Cafetería
     *          - Biblioteca
     *          - Clases
     *          - Comedor
     *          - Consergeria
     *          - Laboratorios
     *          - Despachos
     *      opening menu options:
     *          "Escanear QR"
     *          "Información" or "Administración": "Tramites"
     *              Becas
     *              Movilidad
     *              Oficina virtual
     *              Sede electronica
     *          "Mostrar QR"
     *          "Leer NFC"
     *
     */


    /**
     * Para cada gesto que se realice en la pantalla tenemos que comprobar si es el gesto que inicia el modo
     * developer para lo cual deberemos de ir iterando sobre los distintos gestos que tenemos guardados, que en
     * este caso es solo uno y vez si el gesto reconocido se parece a el y de esta forma activar/desactivar el
     * modo desarrollador.
     * @param overlay
     * @param gesture
     */
    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> objPrediction = objGestureList.recognize(gesture);
        if(objPrediction.size() > 0 && objPrediction.get(0).score > 1){
            String gestureName = objPrediction.get(0).name;
            //Toast.makeText(this, gestureName, Toast.LENGTH_SHORT).show();
            if (gestureName.contains("developer")){
                MenuActivity.developerMode = !MenuActivity.developerMode;

                if (MenuActivity.developerMode){
                    Toast.makeText(this, "Changing to developers mode", Toast.LENGTH_SHORT).show();
                }

                if (!MenuActivity.developerMode){
                    Toast.makeText(this, "Changing to normal mode", Toast.LENGTH_SHORT).show();
                }

            }

        }
    }
}