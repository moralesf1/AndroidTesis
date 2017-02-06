package com.example.felix.androidtesis.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.felix.androidtesis.Conexion;
import com.example.felix.androidtesis.R;
import com.example.felix.androidtesis.modelo.Habitacion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetallesHabitacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetallesHabitacionFragment extends Fragment {

    private static final String ARG_HABITACION = "args_habitacion";

    // TODO: Rename and change types of parameters
    private Habitacion mHabitacion;


    private TextView tvTitulo;
    private TextView tvPrecio;

    private ImageView ivImagen;
    private Context mContext;


    public DetallesHabitacionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param habitacion Parameter 1.
     * @return A new instance of fragment DetallesHabitacionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetallesHabitacionFragment newInstance(Habitacion habitacion) {
        DetallesHabitacionFragment fragment = new DetallesHabitacionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_HABITACION, habitacion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();

        if (getArguments() != null) {
            mHabitacion = (Habitacion) getArguments().getSerializable(ARG_HABITACION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_detalles_habitacion, container, false);

        tvTitulo = (TextView) rootView.findViewById(R.id.tv_titulo);
        tvPrecio = (TextView) rootView.findViewById(R.id.tv_precio);

        ivImagen = (ImageView) rootView.findViewById(R.id.iv_imagen);

        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tvTitulo.setText(mHabitacion.getTitulo());
        tvPrecio.setText("Bs " + mHabitacion.getPrecio());


        Glide.with(mContext)
                .load(Conexion.getConexion() + "uploads/habitaciones/" + mHabitacion.getFotos().get(0).getFoto())
                .into(ivImagen);
    }
}
