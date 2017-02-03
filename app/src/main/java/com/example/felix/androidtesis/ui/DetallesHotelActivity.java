package com.example.felix.androidtesis.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.felix.androidtesis.Constantes;
import com.example.felix.androidtesis.R;
import com.example.felix.androidtesis.Utilidades;
import com.example.felix.androidtesis.modelo.Paquete;

public class DetallesHotelActivity extends AppCompatActivity {

    private AppCompatActivity mContext;
    private int mIdHotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_hotel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mContext = this;

        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            mIdHotel = bundle.getInt(Constantes.PARAM_ID_HOTEL);
        }

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(Constantes.FRAGMENT_DETALLES_HOTEL);

        if (fragment == null) {
            fragment = DetallesHotelFragment.newInstance(mIdHotel);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment, Constantes.FRAGMENT_DETALLES_PAQUETE)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
