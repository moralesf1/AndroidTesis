package com.example.felix.androidtesis.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.felix.androidtesis.Conexion;
import com.example.felix.androidtesis.Constantes;
import com.example.felix.androidtesis.R;
import com.example.felix.androidtesis.modelo.Paquete;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetallesPaqueteFragment extends Fragment {


    private CarouselView mCarouselView;

    private Paquete mPaquete;
    private AppCompatActivity mContext;

    private TextView tvPais;
    private TextView tvDescripcion;

    private static final String ARG_PAQUETE = "arg_paquete";

    public DetallesPaqueteFragment() {
    }


    public static DetallesPaqueteFragment newInstance(Paquete paquete) {

        DetallesPaqueteFragment fragment = new DetallesPaqueteFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_PAQUETE, paquete);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = (AppCompatActivity) getActivity();

        if (getArguments() != null) {
            mPaquete = (Paquete) getArguments().getSerializable(ARG_PAQUETE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_detalles_paquete, container, false);

        mCarouselView = (CarouselView) rootView.findViewById(R.id.carouselView);

        tvDescripcion = (TextView) rootView.findViewById(R.id.tv_descripcion);
        tvPais = (TextView) rootView.findViewById(R.id.tv_pais);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mPaquete != null) {

            ActionBar actionBar = mContext.getSupportActionBar();

            if (actionBar != null) {
                actionBar.setTitle(mPaquete.getTitulo());
            }

            tvPais.setText(mPaquete.getEstado().getEstado() + ", " + mPaquete.getCiudad().getCiudad());
            tvDescripcion.setText(mPaquete.getDescripcion());

            if (mPaquete.getFotos() != null && mPaquete.getFotos().size() > 0) {
                mCarouselView.setPageCount(mPaquete.getFotos().size());

                ImageListener imageListener = new ImageListener() {
                    @Override
                    public void setImageForPosition(int position, ImageView imageView) {
                        Glide.with(mContext)
                                .load(Conexion.getConexion() + "uploads/" + mPaquete.getFotos().get(position).getFoto())
                                .into(imageView);
                    }
                };
                mCarouselView.setImageListener(imageListener);
            }
        }

    }
}
