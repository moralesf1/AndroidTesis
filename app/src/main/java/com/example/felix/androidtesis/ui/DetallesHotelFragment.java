package com.example.felix.androidtesis.ui;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.felix.androidtesis.Conexion;
import com.example.felix.androidtesis.Mysingleton;
import com.example.felix.androidtesis.R;
import com.example.felix.androidtesis.modelo.Hotel;
import com.google.gson.Gson;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetallesHotelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetallesHotelFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID_HOTEL = "arg_id_hotel";

    // TODO: Rename and change types of parameters
    private String mIdHotel;
    private AppCompatActivity mContext;
    private Gson mGson = new Gson();

    private CarouselView mCarouselView;
    private TextView tvDescripcion;
    private TextView tvPais;
    private Hotel mHotel;


    public DetallesHotelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param idHotel Parameter 1.
     * @return A new instance of fragment DetallesHotelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetallesHotelFragment newInstance(int idHotel) {
        DetallesHotelFragment fragment = new DetallesHotelFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_HOTEL, idHotel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = (AppCompatActivity) getActivity();

        if (getArguments() != null) {
            mIdHotel = getArguments().getString(ARG_ID_HOTEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_detalles_hotel, container, false);

        mCarouselView = (CarouselView) rootView.findViewById(R.id.carouselView);
        tvDescripcion = (TextView) rootView.findViewById(R.id.tv_descripcion);
        tvPais = (TextView) rootView.findViewById(R.id.tv_pais);

        String url = Conexion.getConexion() + "android/gethotel/" + mIdHotel;

        final ProgressDialog progressDialog = new ProgressDialog(mContext);

        progressDialog.setMessage("Cargando...");
        progressDialog.setIndeterminate(true);

        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                mContext.finish();
            }
        });

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Hotel hotel = mGson.fromJson(response, Hotel.class);
                        Log.d("RESPUESTA", response);
                        mHotel = hotel;

                        if (hotel != null) {
                            ActionBar actionBar = mContext.getSupportActionBar();

                            if (actionBar != null) {
                                actionBar.setTitle(hotel.getHotel());
                            }

                            tvPais.setText(hotel.getEstado().getEstado() + ", " + hotel.getCiudad().getCiudad());
                            tvDescripcion.setText(hotel.getDescripcion());

                            if (hotel.getFotos() != null && hotel.getFotos().size() > 0) {
                                mCarouselView.setPageCount(hotel.getFotos().size());

                                ImageListener imageListener = new ImageListener() {
                                    @Override
                                    public void setImageForPosition(int position, ImageView imageView) {
                                        Glide.with(mContext)
                                                .load(Conexion.getConexion() + "uploads/hoteles/" + mHotel.getFotos().get(position).getFoto())
                                                .into(imageView);
                                    }
                                };
                                mCarouselView.setImageListener(imageListener);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        Mysingleton.getmInstance(mContext)
                .addToRequestQue(stringRequest);

        return rootView;
    }

}
