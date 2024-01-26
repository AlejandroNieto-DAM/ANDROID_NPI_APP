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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import io.kommunicate.KmConversationBuilder;
import io.kommunicate.KmConversationHelper;
import io.kommunicate.KmException;
import io.kommunicate.Kommunicate;
import io.kommunicate.callbacks.KMLogoutHandler;
import io.kommunicate.callbacks.KmCallback;

public class MenuActivity extends AppCompatActivity implements OnGesturePerformedListener {

    private GestureLibrary objGestureList;
    public static boolean developerMode = false;

    public static TextToSpeech TTS;
    public static String Lang = "es";

    private String conversationId;
    private boolean firstConv = true;

    LinearLayout qrOption, locationsOption, administration, reservar_menu, read_nfc, configuration;

    ImageView toolbar;

    /**
     * Constructor de la clase MenuActivity donde se manejan los eventos para los distintos botones
     * que mostramos en el layout que son las distintas opciones del menú
     * También tenemos en cuenta el gesto que pueda hacer el usuario para entrar en developer mode
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        Kommunicate.init(this, "1e5a2696e1001f90c1312f0cf83e2b37b");

        toolbar = findViewById(R.id.VoiceIcon1);

        objGestureList = GestureLibraries.fromRawResource(this, R.raw.gesture);
        if (!objGestureList.load()) {
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
                intent.putExtra(MainActivity.EXTRA_MESSAGE, MainActivity.user + "@" + MainActivity.pass);

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
                if (status != TextToSpeech.ERROR) {
                    TTS.setLanguage(new Locale("es", "ES"));
                }
            }
        });
    }

    /**
     * Inicia una nueva conversación con el chatbot Kommunicate y abre las conversaciones
     * redirigiendote a su pantalla.
     * Una vez finalizado cierra y borra la conversación.
     *
     * @param view
     */
    public void chatBotClick(View view) throws KmException {


        if(firstConv) {


            new KmConversationBuilder(this)
                    .createConversation(new KmCallback() {
                        @Override
                        public void onSuccess(Object message) {
                            conversationId = message.toString();
                        }

                        @Override
                        public void onFailure(Object error) {
                            Log.d("ConversationTest", "Error : " + error);
                        }
                    });

            Kommunicate.openConversation(this);
            firstConv = false;

        } else {

            // Open the Kommunicate chat widget
            //

            KmConversationHelper.openConversation(this,
                    true,
                    Integer.valueOf(conversationId),
                    new KmCallback() {
                        @Override
                        public void onSuccess(Object message) {

                        }

                        @Override
                        public void onFailure(Object error) {

                        }
                    });

            Kommunicate.openConversation(this);

        }



    }

    @Override
    public void onDestroy() {
        super.onDestroy();

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
     * Borra las conversaciones que tiene el usuario con el bot, esto es debido
     * a que si no se borrasen se quedarían guardadas todas las conversaciones
     */
    private void logoutAndClearConversations() {


    }

    /**
     * Inicia la camara para poder escanear un QR
     */
    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volumen arriba para activar flash");
        //options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result ->
    {
        if (result.getContents() != null) {
            if (result.getContents().contains("Menu")) {
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
     *
     * @param view
     */
    public void onVoiceButtonClick(View view) {
        // Perform actions on back button click (e.g., navigate back)
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        if (Lang == "es") {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES"); // Set Spanish language
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora");
        }
        else{
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES"); // Set English language
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now");
        }

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

            // Si el mensaje es válido, ejecutar acción según contenido
            if (result != null && result.size() > 0) {
                String transcripcion = result.get(0);
                interpretMessage(transcripcion);
            }
        }
    }

    /**
     * Checkea se el string dado contiene alguno de los substrings
     *
     * @param mainString
     * @param sentences
     * @return
     */
    public static boolean containsWords(String mainString, List<String> sentences){
        for (String frase : sentences) {
            String[] palabras = frase.split("\\s+");
            boolean todasLasPalabrasEncontradas = true;

            for (String palabra : palabras) {
                String patron = "\\b" + Pattern.quote(palabra) + "\\b";
                if (!Pattern.compile(patron).matcher(mainString).find()) {
                    todasLasPalabrasEncontradas = false;
                    break;
                }
            }

            if (todasLasPalabrasEncontradas) {
                return true;
            }
        }

        return false;
    }

    /**
     * Decides action based on input
     * @param transcripcion : provided command string
     */
    private void interpretMessage(String transcripcion) {
        boolean entendido = false;
        //Toast.makeText(this, "Commando no entendido: " + transcripcion, Toast.LENGTH_LONG).show();
        String command = transcripcion.toLowerCase();
        //Check si contiene keywords
        if (containsWords(command, Arrays.asList("becas", "scholarship", "beca"))){
            entendido = true;
            if (Lang.equals("es")) {
                TTS.speak("Yendo a información de becas", TextToSpeech.QUEUE_FLUSH, null);
            } else {
                TTS.speak("Opening scholarship information menu.", TextToSpeech.QUEUE_FLUSH, null);
            }
            Intent intent = new Intent(getApplicationContext(), Becas.class);
            startActivity(intent);
        }
        else if (containsWords(command, Arrays.asList("movilidad", "exchange program", "cultural exchange"))){
            entendido = true;
            if (Lang.equals("es")) {
                TTS.speak("Abriendo página web del programa de movilidad", TextToSpeech.QUEUE_FLUSH, null);
            } else {
                TTS.speak("Opening exchange program webpage.", TextToSpeech.QUEUE_FLUSH, null);
            }
            // cargar la clase web
            WebView web = new WebView(getApplicationContext());
            //para visualizar la web sin problemas
            WebSettings webSettings = web.getSettings();
            webSettings.setJavaScriptEnabled(true);
            setContentView(web);
            web.loadUrl("https://internacional.ugr.es/pages/movilidad");
        }
        else if (containsWords(command, Arrays.asList("oficina virtual", "virtual office"))){
            entendido = true;
            if (Lang.equals("es")) {
                TTS.speak("Abriendo página web de la oficina virtual", TextToSpeech.QUEUE_FLUSH, null);
            } else {
                TTS.speak("Opening virtual office webpage.", TextToSpeech.QUEUE_FLUSH, null);
            }
            // cargar la clase web
            WebView web = new WebView(getApplicationContext());
            //para visualizar la web sin problemas
            WebSettings webSettings = web.getSettings();
            webSettings.setJavaScriptEnabled(true);
            setContentView(web);
            web.loadUrl("https://oficinavirtual.ugr.es/ai/");
        }
        else if(containsWords(command, Arrays.asList("sede electrónica", "electronic administration", "university page", "uni page"))){
            entendido = true;
            if (Lang.equals("es")) {
                TTS.speak("Abriendo página web de la sede electrónica", TextToSpeech.QUEUE_FLUSH, null);
            } else {
                TTS.speak("Opening university webpage.", TextToSpeech.QUEUE_FLUSH, null);
            }
            // cargar la clase web
            WebView web = new WebView(getApplicationContext());
            //para visualizar la web sin problemas
            WebSettings webSettings = web.getSettings();
            webSettings.setJavaScriptEnabled(true);
            setContentView(web);
            web.loadUrl("https://sede.ugr.es/");
        }
        //para ir a
        else if (containsWords(command, Arrays.asList("llegar a ", "camino a", "get to", "path to", "guiame a", "enseñame donde está", "where", "donde"))
        && containsWords(command, Arrays.asList( "coffee shop", "cafetería", "café" , "library", "biblioteca", "comedor", "dining", "diner", "clases",
                "classes", "conserjería", "consierge", "caretaker", "laboratorio", "laboratory", "laboratories", "despacho", "office", "aulas", "aulario") )){
            if (containsWords(command, Arrays.asList("cafeteria", "comedor", "dining", "diner", "aulario"))){
                entendido = true;
                if (Lang.equals("es")) {
                    TTS.speak("Para llegar al comedor, entre al edificio principal. Baje las escaleras y a su derecha se encuentra el comedor.", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    TTS.speak("To get to the diner, take the stairs to the basement from in the main building. Then to your right you will find the cafeteria.", TextToSpeech.QUEUE_FLUSH, null);
                }
                //CODIGO DE ABRIR MAPA A COMEDOR
            }
            else if (containsWords(command, Arrays.asList("cafetería", "cafe", "coffee shop"))){
                entendido = true;
                if (Lang.equals("es")) {
                    TTS.speak("Para llegar a la cafetería, entre al edificio principal baje las escaleras. Frente suyo a la derecha se encuentra el comedor.", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    TTS.speak("To get to the cafe, take the stairs to the basement from in the main building. Then to your front right you will find the cafeteria.", TextToSpeech.QUEUE_FLUSH, null);
                }
                //CODIGO DE ABRIR MAPA A CAFETERÍA
            }
            else if (containsWords(command, Arrays.asList("library", "biblioteca"))){
                entendido = true;
                if (Lang.equals("es")) {
                    TTS.speak("Para llegar a la biblioteca, desde el edificio principal tome las escaleras hacia arriba. En la primera planta, tome la puerta a la derecha. Al final de la sala a la derecha se encuentra la biblioteca.", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    TTS.speak("To get to the library, take the stairs to the right of the main building entrance upwards. At the first level, enter the door to the right. Then at the end of the lounge the door to the right.", TextToSpeech.QUEUE_FLUSH, null);
                }
                //CODIGO DE ABRIR MAPA A BIBLIOTECA
            }
            else if (containsWords(command, Arrays.asList("clases", "classes", "aulas", "aulario"))){
                entendido = true;
                if (Lang.equals("es")) {
                    TTS.speak("Para llegar a el edificio de los aulas, salga a la zona exterior. Luego sigue el camino al lado del edificio principal hasta llegar al edificio de las aulas. El primer dígito del aula indica la planta.", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    TTS.speak("To get to the classes building, go to the exterior zone. Then follow the path next to the main building till you arrive at the classes building. The first digit of the class indicates what floor it is on.", TextToSpeech.QUEUE_FLUSH, null);
                }
                //CODIGO ABRIR MAPA A AULARIO
            }
            else if (containsWords(command, Arrays.asList("conserjería", "consierge", "caretaker"))){
                entendido = true;
                if (Lang.equals("es")) {
                    TTS.speak("Consergería se encuentra a la izquierda al entrar al edificio principal.", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    TTS.speak("To get to the consierge, simply enter the main building. It will be to your left.", TextToSpeech.QUEUE_FLUSH, null);
                }
                //CODIGO ABRIR MAPA A CONSERGERIA
            }
            else if (containsWords(command, Arrays.asList("laboratorio", "laboratorios", "laboratories", "laboratory"))){
                entendido = true;
                if (Lang.equals("es")) {
                    TTS.speak(".", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    TTS.speak(".", TextToSpeech.QUEUE_FLUSH, null);
                }
                //CODIGO ABRIR MAPA A LOS LABORATORIOS (NO SE DONDE ESTÁN)
            }
            else if (containsWords(command, Arrays.asList("despacho", "despachos", "office", "offices"))){
                entendido = true;
                if (Lang.equals("es")) {
                    TTS.speak("Los despachos se encuentran en las plantas 2 y 3 del edificio principal. Las escaleras se encuentran a la derecha al entrar por la entrada principal.", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    TTS.speak("The offices are found on the second and third floors of the main building. The stairs and elevator can be found to the right after entering through the main entrance.", TextToSpeech.QUEUE_FLUSH, null);
                }
                //CODIGO ABRIR MAPA A LOS DESPACHOS
            }
            else{
                if (Lang.equals("es")) {
                    TTS.speak("Perdone, no lo he entendido", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    TTS.speak("Sorry, I didn't understand.", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }
        // mandar a trámites
        else if (containsWords(command, Arrays.asList("trámites", "administración", "administration", "procedures"))) {
            entendido = true;
            if (Lang.equals("es")){
                TTS.speak("Abriendo menú de trámites.", TextToSpeech.QUEUE_FLUSH, null);
            }
            else{
                TTS.speak("Opening administration menu.", TextToSpeech.QUEUE_FLUSH, null);
            }
            Intent intent = new Intent(getApplicationContext(), Administration.class);
            startActivity(intent);
            //mandar a preorder
        } else if (containsWords(command, Arrays.asList("menú","comedor", "escanear qr", "scan qr", "menu", "preorder"))) {
            entendido = true;
            if (Lang.equals("es")){
                TTS.speak("Scaneando QR de pedido.", TextToSpeech.QUEUE_FLUSH, null);
            }
            else{
                TTS.speak("Scaning preorder QR code.", TextToSpeech.QUEUE_FLUSH, null);
            }
            scanCode();
        } else if (containsWords(command, Arrays.asList("configuración", "configuration", "settings", "ajustes", "preferences"))) {
            entendido = true;
            if (Lang.equals("es")) {
                TTS.speak("Abriendo menu de configuración.", TextToSpeech.QUEUE_FLUSH, null);
            } else {
                TTS.speak("Opening settings menu.", TextToSpeech.QUEUE_FLUSH, null);
            }
            Intent intent = new Intent(getApplicationContext(), Configuration.class);
            startActivity(intent);
        }
        else if (containsWords(command, Arrays.asList("mapa","map"))) {
            entendido = true;
            if (Lang.equals("es")) {
                TTS.speak("Abriendo menú de lugares.", TextToSpeech.QUEUE_FLUSH, null);
            } else {
                TTS.speak("Opening location menu.", TextToSpeech.QUEUE_FLUSH, null);
            }
            Intent intent = new Intent(getApplicationContext(), Locations.class);
            startActivity(intent);
        } else if (containsWords(command, Arrays.asList( "nfc"))) {
            entendido = true;
            if (Lang.equals("es")) {
                TTS.speak("Abriendo lector de NFC.", TextToSpeech.QUEUE_FLUSH, null);
            } else {
                TTS.speak("Starting NFC reader.", TextToSpeech.QUEUE_FLUSH, null);
            }
            Intent intent = new Intent(getApplicationContext(), ReadNFCTag.class);
            startActivity(intent);
        } else if (containsWords(command, Arrays.asList("qr"))) {
            entendido = true;
            if (Lang.equals("es")) {
                TTS.speak("Mostrando QR para autentificarse.", TextToSpeech.QUEUE_FLUSH, null);
            } else {
                TTS.speak("Showing QR for autentification.", TextToSpeech.QUEUE_FLUSH, null);
            }
            Intent intent = new Intent(getApplicationContext(), LoggedActivity.class);
            intent.putExtra(MainActivity.EXTRA_MESSAGE, MainActivity.user + "@" + MainActivity.pass);
            startActivity(intent);
        }
        else if (containsWords(command, Arrays.asList("switch spanish", "language spanish", "cambiar inglés", "lenguaje inglés"))){
            entendido = true;
            if (Lang.equals("es")) {
                TTS.speak("Cambiando lenguaje a inglés.", TextToSpeech.QUEUE_FLUSH, null);
                Lang = "en";
                TTS.setLanguage(new Locale("en", "US"));
            } else {
                TTS.speak("Switching to spanish.", TextToSpeech.QUEUE_FLUSH, null);
                Lang = "es";
                TTS.setLanguage(new Locale("es", "ES"));
            }
        }
        if (!entendido) {
            if (Lang.equals("es")) {
                TTS.speak("Perdone, no lo he entendido", TextToSpeech.QUEUE_FLUSH, null);
            } else {
                TTS.speak("Sorry, I didn't understand.", TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

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