package com.example.felix.androidtesis;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Inicio extends AppCompatActivity
        /**
         * implements Registro.OnFragmentInteractionListener es para recibir datos de un fragment a este activity
         * Sesion.Onlogin es la interfaz creada en Sesion para hacer uso de datos enviado en VIVO sin recargar
         */
        implements NavigationView.OnNavigationItemSelectedListener, Registro.OnFragmentInteractionListener, Sesion.OnLogin {

    private AppCompatActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.sesion) {
            Sesion sesion = new Sesion();
            fragmentManager.beginTransaction().replace(R.id.content_inicio, sesion, sesion.getTag()).commit();
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.registro) {

            Bundle params = new Bundle();
            String dato = "dato 1";
            String dato2 = "dato 2";
            Registro registro = Registro.newInstance(dato,dato2);
            FragmentManager fragmentManager1 = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_inicio,registro,registro.TAG).commit();
            drawer.closeDrawer(GravityCompat.START);
        }


        return true;
    }

    @Override
    /**
     * Interfaz creada para recibir los datos del fragment Registro
     */
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(getApplicationContext(),uri.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    /**
     * Interfaz creada para recibir los datos del fragment Sesion
     */
    public void onUserLogging(String usuario, String correo) {
        Toast.makeText(mContext,correo,Toast.LENGTH_LONG).show();
    }
}
