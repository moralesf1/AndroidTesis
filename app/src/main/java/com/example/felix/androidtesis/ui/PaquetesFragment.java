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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.felix.androidtesis.Constantes;
import com.example.felix.androidtesis.Mysingleton;
import com.example.felix.androidtesis.R;
import com.example.felix.androidtesis.modelo.Paquete;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaquetesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaquetesFragment extends Fragment {

    private Context mContext;
    private Gson mGson = new Gson();


    public PaquetesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PaquetesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaquetesFragment newInstance() {
        PaquetesFragment fragment = new PaquetesFragment();
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

        String url = "http://35.165.205.125/tesis/public/android/getpaquetes";

        mCargandoPaquetes.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPUESTA", response);

                        Paquete[] paquetes = mGson.fromJson(response, Paquete[].class);

                        mCargandoPaquetes.setVisibility(View.GONE);

                        PaquetesAdapter adapter = new PaquetesAdapter(new ArrayList<>(Arrays.asList(paquetes)), mContext, new PaquetesAdapter.OnListFragmentInteractionListener() {
                            @Override
                            public void onListFragmentInteraction(Paquete paquete) {
                                Intent intent = new Intent(mContext, DetallesPaqueteActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(Constantes.PARAM_PAQUETE, paquete);
                                intent.putExtras(bundle);
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
