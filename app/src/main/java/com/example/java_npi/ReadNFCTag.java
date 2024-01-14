package com.example.java_npi;



import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Locale;

public class ReadNFCTag extends AppCompatActivity {

    private TextView textView;
    private PendingIntent pendingIntent;
    private IntentFilter[] readFilters;

    private EditText input;
    private Button btn_write;
    NdefMessage messageToWrite;
    private IntentFilter[] writeFilters;
    private String[][] writeTechList;

    Animation zoom;
    ImageView img;

    public static String selectedClass = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MenuActivity.developerMode ){
            setContentView(R.layout.activity_nfc_reader);

            textView = (TextView) findViewById(R.id.text);
            input = (EditText) findViewById(R.id.input);
            btn_write = (Button) findViewById(R.id.btn_write);

            btn_write.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onWriteText();
                }
            });

        } else {
            setContentView(R.layout.nfc_reader_ndm);
            textView = (TextView) findViewById(R.id.text_l);

            //zoom = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom);
            img = findViewById(R.id.image);
            //zoom.setRepeatCount(Animation.INFINITE);
            //img.startAnimation(zoom);

            AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.zoom);
            animatorSet.setTarget(img);
            animatorSet.start();


        }


        try {
            Intent intent = new Intent(this, getClass());
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            IntentFilter javadudeFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
            javadudeFilter.addDataScheme("http");
            javadudeFilter.addDataAuthority("javadude.com", null);
            IntentFilter textFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED, "text/plain");

            readFilters = new IntentFilter[]{javadudeFilter, textFilter};
            writeFilters = new IntentFilter[]{};
            writeTechList = new String[][]{
                    {Ndef.class.getName()},
                    {NdefFormatable.class.getName()},
            };

        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException(e);
        }

        if (MenuActivity.developerMode){
            processNFC(getIntent());
        } else {
            readTag(getIntent());
        }


    }

    private void enableRead() {
        NfcAdapter.getDefaultAdapter(this).enableForegroundDispatch(this, pendingIntent, readFilters, null);
    }

    private void enableWrite() {
        NfcAdapter.getDefaultAdapter(this).enableForegroundDispatch(this, pendingIntent, writeFilters, writeTechList);
    }

    private void disableRead() {
        NfcAdapter.getDefaultAdapter(this).disableForegroundDispatch(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableRead();


    }

    @Override
    protected void onPause() {
        super.onPause();
        disableRead();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processNFC(intent);
    }

    private void processNFC(Intent intent) {
        if (messageToWrite != null) {
            writeTag(intent);
        } else {
            readTag(intent);
        }
    }

    private void writeTag(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {
            try {
                Ndef ndef = Ndef.get(tag);
                if (ndef == null) {
                    NdefFormatable ndefFormatable = NdefFormatable.get(tag);
                    if (ndefFormatable != null) {
                        ndefFormatable.connect();
                        ndefFormatable.format(messageToWrite);
                        ndefFormatable.close();
                        Toast.makeText(this, "Tag formatted and written", Toast.LENGTH_LONG).show();
                    } else {

                    }
                } else {
                    ndef.connect();
                    ndef.writeNdefMessage(messageToWrite);
                    ndef.close();
                    Toast.makeText(this, "Tag written", Toast.LENGTH_LONG).show();


                }
            } catch (FormatException | IOException e) {
                throw new RuntimeException(e);
            } finally {
                messageToWrite = null;
            }
        }

    }

    private void readTag(Intent intent) {
        Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        //textView.setText("");
        if (messages != null) {
            for (Parcelable message : messages) {
                NdefMessage ndefMessage = (NdefMessage) message;
                for (NdefRecord record : ndefMessage.getRecords()) {
                    switch (record.getTnf()) {
                        case NdefRecord.TNF_WELL_KNOWN:
                            if (Arrays.equals(record.getType(), NdefRecord.RTD_TEXT)) {

                                //img.clearAnimation();
                                if(!MenuActivity.developerMode){
                                    Intent intent1 = new Intent(this, HorarioClase.class);
                                    if (new String(record.getPayload()).contains("clase1")){
                                        ReadNFCTag.selectedClass = "Clase 1";
                                        startActivity(intent1);
                                    }

                                    if (new String(record.getPayload()).contains("clase2")){
                                        ReadNFCTag.selectedClass = "Clase 2";
                                        startActivity(intent1);
                                    }

                                } else {
                                    textView.setText("");
                                    textView.append("Tag written ");
                                    textView.append("TEXT ");
                                    textView.append(new String(record.getPayload()));
                                    textView.append("\n");
                                }



                            } else if (Arrays.equals(record.getType(), NdefRecord.RTD_URI)) {
                               //Esto es para escribir URI
                            }
                    }
                }
            }
        }
    }


    public void onWriteText() {
        try {
            byte[] language = Locale.getDefault().getLanguage().getBytes("UTF-8");
            byte[] text = input.getText().toString().getBytes();
            byte[] payload = new byte[text.length + language.length + 1];

            payload[0] = 0x02;
            System.arraycopy(language, 0, payload, 1, language.length);
            System.arraycopy(text, 0, payload, 1 + language.length, text.length);

            NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);
            messageToWrite = new NdefMessage(new NdefRecord[]{record});
            textView.setText("Please tap a tag to write the text");

            enableWrite();

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void onWriteUri(View view) {
        NdefRecord record = NdefRecord.createUri(input.getText().toString());
        messageToWrite = new NdefMessage(new NdefRecord[]{record});
        textView.setText("Please tap a tag to write the uri");

        enableWrite();
    }




}


