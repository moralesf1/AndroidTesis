package com.example.felix.androidtesis.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.felix.androidtesis.Constantes;
import com.example.felix.androidtesis.R;
import com.example.felix.androidtesis.modelo.Paquete;

public class DetallesPaqueteActivity extends AppCompatActivity {

    private Paquete mPaquete;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_paquete);
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
            mPaquete = (Paquete) bundle.getSerializable(Constantes.PARAM_PAQUETE);
        }

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(Constantes.FRAGMENT_DETALLES_PAQUETE);

        if (fragment == null) {
            fragment = DetallesPaqueteFragment.newInstance(mPaquete);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment, Constantes.FRAGMENT_DETALLES_PAQUETE)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .addToBackStack(null)
                .commit();
    }

}
