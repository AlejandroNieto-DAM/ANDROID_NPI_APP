package com.example.java_npi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class Locations extends AppCompatActivity {

    private ViewPager2 viewPager;
    private CarouselAdapter carouselAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locations);

        viewPager = findViewById(R.id.viewPager);
        carouselAdapter = new CarouselAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(carouselAdapter);

        String infoCafeteria = "Ofrece un ambiente propicio para el descanso y la socialización, contribuyendo al bienestar de la comunidad universitaria en la Escuela Técnica Superior de Ingeniería Informática y de Telecomunicaciones.";
        String infoBiblioteca = "Es un recurso vital para estudiantes y profesionales. Ofrece una amplia colección de libros, revistas y recursos electrónicos, facilitando la investigación y el aprendizaje en disciplinas de tecnología de la información y telecomunicacione";
        String infoClases = "Están diseñadas para facilitar el aprendizaje en disciplinas de ingeniería informática y telecomunicaciones, ofreciendo un espacio propicio para la interacción y la enseñanza efectiva entre estudiantes y profesores.";
        String infoComedor = "Proporciona un espacio cómodo para que estudiantes y personal recarguen energías, fomentando la convivencia y el bienestar en la Escuela Técnica Superior de Ingeniería Informática y de Telecomunicaciones.";
        String infoConserjeria = "Es el punto central para información, orientación y asistencia administrativa. El personal de conserjería ofrece servicios esenciales para estudiantes y visitantes, contribuyendo al funcionamiento eficiente de la Escuela Técnica Superior de Ingeniería Informática y de Telecomunicaciones.";

        // Agrega fragmentos al adaptador con imágenes y texto
        carouselAdapter.addFragment(CarouselItemFragment.newInstance(R.drawable.cafeteria, "CAFETERÍA",  infoCafeteria));
        carouselAdapter.addFragment(CarouselItemFragment.newInstance(R.drawable.biblioteca, "BIBLIOTECA", infoBiblioteca));
        carouselAdapter.addFragment(CarouselItemFragment.newInstance(R.drawable.clasees, "CLASES", infoClases));
        carouselAdapter.addFragment(CarouselItemFragment.newInstance(R.drawable.comedor, "COMEDOR", infoComedor));
        carouselAdapter.addFragment(CarouselItemFragment.newInstance(R.drawable.conserjeria, "CONSERJERIA", infoConserjeria));

    }

    private static class CarouselAdapter extends FragmentStateAdapter {

        private final List<Fragment> fragments = new ArrayList<>();

        public CarouselAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        public void addFragment(Fragment fragment) {
            fragments.add(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return fragments.size();
        }
    }
}