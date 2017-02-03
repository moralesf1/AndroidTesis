package com.example.felix.androidtesis.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.felix.androidtesis.Conexion;
import com.example.felix.androidtesis.Constantes;
import com.example.felix.androidtesis.Mysingleton;
import com.example.felix.androidtesis.R;
import com.example.felix.androidtesis.modelo.Hotel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HotelesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotelesFragment extends Fragment {
    private Context mContext;
    private Gson mGson = new Gson();


    public HotelesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PaquetesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HotelesFragment newInstance() {
        HotelesFragment fragment = new HotelesFragment();
        return fragment;
    }

    private RecyclerView mRecyclerView;
    private LinearLayout mNoPaquetesView;

    private View mCargandoPaquetes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_paquetes, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        mNoPaquetesView = (LinearLayout) rootView.findViewById(R.id.no_paquete);
        mCargandoPaquetes = rootView.findViewById(R.id.cargando_paquetes);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(getResources()));

        String url = Conexion.getConexion() + "android/gethoteles";

        mCargandoPaquetes.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPUESTA", response);

                        Hotel[] hoteles = mGson.fromJson(response, Hotel[].class);

                        mCargandoPaquetes.setVisibility(View.GONE);

                        HotelesAdapter adapter = new HotelesAdapter(new ArrayList<>(Arrays.asList(hoteles)), mContext, new HotelesAdapter.OnListFragmentInteractionListener() {
                            @Override
                            public void onListFragmentInteraction(Hotel hotel) {
                                Intent intent = new Intent(mContext, DetallesHotelActivity.class);
                                intent.putExtra(Constantes.PARAM_ID_HOTEL, hotel.getId());
                                mContext.startActivity(intent);
                            }
                        });

                        if (mRecyclerView != null) {
                            mRecyclerView.setAdapter(adapter);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mCargandoPaquetes.setVisibility(View.VISIBLE);
                error.printStackTrace();
            }
        });

        Mysingleton.getmInstance(mContext)
                .addToRequestQue(stringRequest);
    }

}
