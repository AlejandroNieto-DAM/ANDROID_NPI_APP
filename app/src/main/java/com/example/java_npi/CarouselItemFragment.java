package com.example.java_npi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;


import androidx.fragment.app.Fragment;

public class CarouselItemFragment extends Fragment {

    private int imageResId;
    private String text;

    private String info;

    public CarouselItemFragment() {
        // Required empty public constructor
    }

    public static CarouselItemFragment newInstance(int imageResId, String text, String info) {
        CarouselItemFragment fragment = new CarouselItemFragment();
        Bundle args = new Bundle();
        args.putInt("imageResId", imageResId);
        args.putString("text", text);
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageResId = getArguments().getInt("imageResId");
            text = getArguments().getString("text");
            info = getArguments().getString("info");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_fragment, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView = view.findViewById(R.id.textView);
        TextView infoView = view.findViewById(R.id.textView2);
        Button btn_frag = view.findViewById(R.id.button_site);

        imageView.setImageResource(imageResId);

        textView.setText(text);
        infoView.setText(info);




        btn_frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch(text){
                    case "CAFETERÍA":
                        Locations.selectedSite = "CAFETERÍA";
                        Nodo poste = new Nodo();
                        poste.x = 37.19110857873402;
                        poste.y = -3.609295916039702;
                        poste.padre = null;
                        poste.info = "Sigue hasta la esquina de la calle";


                        Nodo sigPoste = new Nodo();
                        sigPoste.x =  37.19119233679828;
                        sigPoste.y = -3.609489471737808;
                        sigPoste.padre = poste;
                        sigPoste.info = "Sigue andando hasta que te encuentres con un semaforo";

                        Nodo izq = new Nodo();
                        izq.x = 37.19059746216378;
                        izq.y = -3.6103855629840336;
                        izq.padre = sigPoste;
                        izq.info = "Cruza el paso de cebra";


                        Nodo der = new Nodo();
                        der.x =  37.190646004109084;
                        der.y = -3.610485925197867;
                        der.padre = izq;
                        der.info = "Sigue hacia el gimnasio";

                        Nodo der2 = new Nodo();
                        der.x =  37.19068217259672;
                        der.y = -3.6110211903578473;
                        der.padre = izq;
                        der.info = "Final!";

                        Locations.generarCamino.add(poste);
                        Locations.generarCamino.add(sigPoste);
                        Locations.generarCamino.add(izq);
                        Locations.generarCamino.add(der);
                        Locations.generarCamino.add(der2);

                        break;
                    case "BIBLIOTECA":
                        Locations.selectedSite = "BIBLIOTECA";

                        break;
                    case "CLASES":
                        Locations.selectedSite = "CLASES";

                        break;
                    case "LABORATORIOS":
                        Locations.selectedSite = "LABORATORIOS";

                        break;
                    case "COMEDOR":
                        break;
                    case "CONSERJERIA":

                        break;
                    case "DESPACHOS":

                        break;
                }

                Intent intent2 = new Intent(getActivity(), CompassActivity.class);
                startActivity(intent2);

            }
        });



        return view;
    }
}
