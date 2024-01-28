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

        // Recuperar ubi
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

        Nodo totem = new Nodo();
        totem.x = 37.1969079;
        totem.y = -3.6244766;
        totem.padre = null;

        Nodo puntoAntesPuerta = new Nodo();
        puntoAntesPuerta.x =  37.1969932;
        puntoAntesPuerta.y = -3.6243699;
        puntoAntesPuerta.padre = totem;

        Nodo puntoPasadoPuerta = new Nodo();
        puntoAntesPuerta.x =  37.1969859;
        puntoAntesPuerta.y = -3.6242794;
        puntoAntesPuerta.padre = puntoAntesPuerta;

        Nodo puntoAntesEdificio = new Nodo();
        puntoAntesPuerta.x =  37.1972270;
        puntoAntesPuerta.y = -3.6242873;
        puntoAntesPuerta.padre = puntoPasadoPuerta;

        Nodo puntoEntradoEdificio = new Nodo();
        puntoAntesPuerta.x =  37.1973225;
        puntoAntesPuerta.y = -3.6242846;
        puntoAntesPuerta.padre = puntoAntesEdificio;

        Nodo escaleras = new Nodo();
        puntoAntesPuerta.x =  37.1973352;
        puntoAntesPuerta.y = -3.6241299;
        puntoAntesPuerta.padre = puntoEntradoEdificio;

        Nodo bajadasEscaleras = new Nodo();
        bajadasEscaleras.x =  37.1972270;
        bajadasEscaleras.y = -3.6243822;
        bajadasEscaleras.padre = puntoAntesPuerta;

        Nodo cafeteria = new Nodo();
        cafeteria.x =  37.1970275;
        cafeteria.y = -3.6246784;
        cafeteria.padre = bajadasEscaleras;

        Nodo comedor = new Nodo();
        cafeteria.x =  37.1970366;
        cafeteria.y = -3.6245133;
        cafeteria.padre = bajadasEscaleras;

        Nodo subidaEscalerasTotem = new Nodo();
        cafeteria.x =  37.1969150;
        cafeteria.y = -3.6243777;
        cafeteria.padre = bajadasEscaleras;

        Nodo biblioteca = new Nodo();
        cafeteria.x =  37.1970238;
        cafeteria.y = -3.6244905;
        cafeteria.padre = subidaEscalerasTotem;

        Nodo despachos = new Nodo();
        cafeteria.x =  37.1970238;
        cafeteria.y = -3.6244905;
        cafeteria.padre = subidaEscalerasTotem;

        btn_frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locations.generarCamino.clear();

                switch(text){
                    case "CAFETERÍA":
                        Locations.selectedSite = "CAFETERÍA";

                        totem.info = "En primer lugar sigue la brujula hasta antes de la puerta de salida. Una vez ahí pulse el botón Siguiente.";
                        puntoAntesPuerta.info = "Una vez aqui deberas bajar por las escaleras que se ven a la derecha.";
                        bajadasEscaleras.info = "Una vez bajadas las escaleras deberás avanzar hacia delante y encontrarás la cafetería.";
                        cafeteria.info = "Ya ha llegado al destino. Está en la CAFETERÍA.";

                        Locations.generarCamino.add(totem);
                        Locations.generarCamino.add(puntoAntesPuerta);
                        Locations.generarCamino.add(bajadasEscaleras);
                        Locations.generarCamino.add(cafeteria);

                        break;

                    case "BIBLIOTECA":
                        Locations.selectedSite = "BIBLIOTECA";

                        totem.info = "En primer lugar gira a la derecha y sigue la brújula hasta colocarte delante de la subida de escaleras.";
                        subidaEscalerasTotem.info = "Una vez en las escaleras suba hasta la primera planta.";
                        biblioteca.info = "Si ya esta en la planta uno entre y siga la brújula hasta la entrada de la biblioteca.";


                        Locations.generarCamino.add(totem);
                        Locations.generarCamino.add(subidaEscalerasTotem);
                        Locations.generarCamino.add(biblioteca);

                        break;
                    case "CLASES":
                        Locations.selectedSite = "CLASES";

                        totem.info = "En primer dirijase hasta la salida que puede ver a la derecha.";
                        puntoAntesPuerta.info = "Una vez situado antes de la puerta. Atraviesela hasta salir del edificio.";
                        puntoPasadoPuerta.info = "Una vez pasada la puerta debera ir al edificio que ve a la izquierda y situarse en la puerta.";
                        puntoAntesEdificio.info = "Atraviese la puerta para entra al edificio.";
                        puntoEntradoEdificio.info = "Ya ha llegado al destino. A su derecha y a su izquierda encontrará las clases de teoría. No es necesario subir las escaleras.";

                        Locations.generarCamino.add(totem);
                        Locations.generarCamino.add(puntoAntesPuerta);
                        Locations.generarCamino.add(puntoPasadoPuerta);
                        Locations.generarCamino.add(puntoAntesEdificio);
                        Locations.generarCamino.add(puntoEntradoEdificio);

                        break;
                    case "LABORATORIOS":
                        Locations.selectedSite = "LABORATORIOS";

                        totem.info = "En primer dirijase hasta la salida que puede ver a la derecha.";
                        puntoAntesPuerta.info = "Una vez situado antes de la puerta. Atraviesela hasta salir del edificio.";
                        puntoPasadoPuerta.info = "Una vez pasada la puerta debera ir al edificio que ve a la izquierda y situarse en la puerta.";
                        puntoAntesEdificio.info = "Atraviese la puerta para entra al edificio.";
                        puntoEntradoEdificio.info = "Una vez dentro del edificio gire a su derecha y podrá ver las escaleras. Suba las escaleras hasta la planta uno para encontrar los " +
                                "laboratorios 1.x, para los laboratorios 2.x suba a la segunda planta y para los laboratorios 3.x suba a la tercera planta. ";
                        escaleras.info = "Ya ha llegado al destino. A su derecha y a su izquierda encontrará los laboratorios correspondientes a la planta en la que se encuentra.";

                        Locations.generarCamino.add(totem);
                        Locations.generarCamino.add(puntoAntesPuerta);
                        Locations.generarCamino.add(puntoPasadoPuerta);
                        Locations.generarCamino.add(puntoAntesEdificio);
                        Locations.generarCamino.add(puntoEntradoEdificio);
                        Locations.generarCamino.add(escaleras);

                        break;
                    case "COMEDOR":

                        totem.info = "En primer lugar sigue la brujula hasta antes de la puerta de salida. Una vez ahí pulse el botón Siguiente.";
                        puntoAntesPuerta.info = "Una vez aqui deberas bajar por las escaleras que se ven a la derecha.";
                        bajadasEscaleras.info = "Una vez bajadas las escaleras deberás girar hacia la derecha y encontrarás el comedor.";
                        cafeteria.info = "Ya ha llegado al destino. Está en el COMEDOR.";

                        Locations.generarCamino.add(totem);
                        Locations.generarCamino.add(puntoAntesPuerta);
                        Locations.generarCamino.add(bajadasEscaleras);
                        Locations.generarCamino.add(comedor);

                        break;
                    case "CONSERJERIA":

                        totem.info = "En primer dirijase hasta la salida que puede ver a la derecha.";
                        puntoAntesPuerta.info = "Una vez situado antes de la puerta. Atraviesela hasta salir del edificio.";
                        puntoPasadoPuerta.info = "Una vez pasada la puerta debera ir al edificio que ve a la izquierda y situarse en la puerta.";
                        puntoAntesEdificio.info = "Atraviese la puerta para entra al edificio.";
                        puntoEntradoEdificio.info = "Ya ha llegado al destino. A su izquierda podrá encontrar la CONSERJERÍA";

                        Locations.generarCamino.add(totem);
                        Locations.generarCamino.add(puntoAntesPuerta);
                        Locations.generarCamino.add(puntoPasadoPuerta);
                        Locations.generarCamino.add(puntoAntesEdificio);
                        Locations.generarCamino.add(puntoEntradoEdificio);
                        break;
                    case "DESPACHOS":

                        totem.info = "En primer lugar gira a la derecha y sigue la brújula hasta colocarte delante de la subida de escaleras.";
                        subidaEscalerasTotem.info = "Una vez en las escaleras suba hasta la 3ra planta.";
                        biblioteca.info = "Si ya esta en la planta tercera encontrará los despachos en los pasillos tanto a la izquierda como derecha.";

                        Locations.generarCamino.add(totem);
                        Locations.generarCamino.add(subidaEscalerasTotem);
                        Locations.generarCamino.add(biblioteca);

                        break;
                }

                Intent intent2 = new Intent(getActivity(), CompassActivity.class);
                startActivity(intent2);

            }
        });

        return view;
    }
}
