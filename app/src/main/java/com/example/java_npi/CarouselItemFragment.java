package com.example.java_npi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        imageView.setImageResource(imageResId);

        textView.setText(text);
        infoView.setText(info);

        return view;
    }
}
