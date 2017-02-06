package com.example.felix.androidtesis;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.felix.androidtesis.modelo.Usuario;
import com.example.felix.androidtesis.ui.HotelesFragment;
import com.example.felix.androidtesis.ui.PaquetesFragment;

public class Inicio extends AppCompatActivity
        /**
         * implements Registro.OnFragmentInteractionListener es para recibir datos de un fragment a este activity
         * Sesion.Onlogin es la interfaz creada en Sesion para hacer uso de datos enviado en VIVO sin recargar
         */
        implements NavigationView.OnNavigationItemSelectedListener, Registro.OnFragmentInteractionListener, Sesion.OnLogin {
    public TextView nombreNav, correoNav;
    private AppCompatActivity mContext;
    private NavigationView mNavigationView;
    FragmentManager mFragmentManager;


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


        mostrarListaPaquetes();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
//        de esta manera obtego el header del navigation view
        View Hview = mNavigationView.getHeaderView(0);
        SharedPreferences sharedPref = getSharedPreferences("usuario", mContext.MODE_PRIVATE);
        int id = sharedPref.getInt("id", 0);
        Log.v("Shared: ", "" + id);
        Menu menu = mNavigationView.getMenu();
        MenuItem logout = menu.findItem(R.id.LogOut);
        MenuItem sesion = menu.findItem(R.id.sesion);
        MenuItem registro = menu.findItem(R.id.registro);
        if (id != 0) {
            Log.v("Logged ", "Si");
            nombreNav = (TextView) Hview.findViewById(R.id.nameNav);
            correoNav = (TextView) Hview.findViewById(R.id.correoNav);
            if (nombreNav != null) {
                nombreNav.setText(sharedPref.getString("full_name", "Invitado"));
            }
            if (correoNav != null) {
                correoNav.setText(sharedPref.getString("correo", ""));
            }
            logout.setVisible(true);
            sesion.setVisible(false);
            registro.setVisible(false);
        }

    }

    private void mostrarListaPaquetes() {
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(Constantes.FRAGMENT_PAQUETES);

        if (fragment == null) {
            fragment = PaquetesFragment.newInstance();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_inicio, fragment, Constantes.FRAGMENT_PAQUETES)
                .commit();
    }

    private void mostrarListaHoteles() {
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(Constantes.FRAGMENT_HOTELES);

        if (fragment == null) {
            fragment = HotelesFragment.newInstance();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_inicio, fragment, Constantes.FRAGMENT_HOTELES)
                .commit();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.inicio, menu);
//        return true;
//    }

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
        FragmentManager fragmentManager = getSupportFragmentManager();


        int id = item.getItemId();
        if (id == R.id.inicio) {
            mostrarListaPaquetes();

            mostrarTitulo("Inicio");

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            mostrarListaHoteles();
            mostrarTitulo("Hoteles");
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.sesion) {
            Sesion sesion = new Sesion();
            fragmentManager.beginTransaction().replace(R.id.content_inicio, sesion, sesion.getTag()).commit();
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.registro) {

            registro();
        } else if (id == R.id.LogOut) {
            logout();
        }

        drawer.closeDrawer(Gravity.LEFT);
        return true;
    }

    private void mostrarTitulo(String titulo) {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(titulo);
        }
    }

    @Override
    /**
     * Interfaz creada para recibir los datos del fragment Registro
     */
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(getApplicationContext(), uri.toString(), Toast.LENGTH_LONG).show();
    }

    public void logout() {
        Menu menu = mNavigationView.getMenu();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        MenuItem logout = menu.findItem(R.id.LogOut);
        MenuItem sesion = menu.findItem(R.id.sesion);
        MenuItem registro = menu.findItem(R.id.registro);

        SharedPreferences sp = getSharedPreferences("usuario", mContext.MODE_PRIVATE);
        sp.edit().clear().apply();

        if (logout != null) {
            logout.setVisible(false);
        }
        if (sesion != null) {
            sesion.setVisible(true);
        }
        if (registro != null) {
            registro.setVisible(true);
        }
        nombreNav = (TextView) findViewById(R.id.nameNav);
        correoNav = (TextView) findViewById(R.id.correoNav);
        nombreNav.setText("Invitado");
        correoNav.setText("");
    }

    public void registro() {
//            Bundle params = new Bundle();

//            String dato = "dato 1";
//            String dato2 = "dato 2";
//            Registro registro = Registro.newInstance(dato,dato2);
        mFragmentManager = getSupportFragmentManager();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Registro registro = new Registro();
        mFragmentManager.beginTransaction().replace(R.id.content_inicio, registro, registro.TAG).commit();
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    /**
     * Interfaz creada para recibir los datos del fragment Sesion
     */
    public void onUserLogging(Usuario datos) {
//        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);// el NavigationView es el que esta importando en la activity principal como un widget
//        Menu menu = navigationView.getMenu();
//        SharedPreferences sh = mContext.getPreferences(mContext.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sh.edit();
//        editor.putInt("id",datos.getId());
//        editor.putString("full_name",datos.getNombre()+" "+datos.getApellido());
//        editor.putString("correo",datos.getEmail());
//        editor.apply();

        nombreNav = (TextView) findViewById(R.id.nameNav);
        correoNav = (TextView) findViewById(R.id.correoNav);
        SharedPreferences sp = getSharedPreferences("usuario", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("id", datos.getId());
        editor.putString("full_name", datos.getNombre() + " " + datos.getApellido());
        editor.putString("correo", datos.getEmail());
        editor.apply();

        Menu menu = mNavigationView.getMenu();

        MenuItem logout = menu.findItem(R.id.LogOut);
        MenuItem sesion = menu.findItem(R.id.sesion);
        MenuItem registro = menu.findItem(R.id.registro);


        if (logout != null) {
            logout.setVisible(true);
        }
        if (sesion != null) {
            sesion.setVisible(false);
        }
        if (registro != null) {
            registro.setVisible(false);
        }

//        menu.add(datos.getEmail());

        nombreNav.setText(datos.getNombre() + " " + datos.getApellido());
        correoNav.setText(datos.getEmail());
//        Toast.makeText(mContext,"esto es la actividad: "+datos.getEmail(),Toast.LENGTH_LONG).show();

    }
}
