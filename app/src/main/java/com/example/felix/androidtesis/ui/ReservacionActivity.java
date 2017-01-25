package com.example.felix.androidtesis.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.felix.androidtesis.Conexion;
import com.example.felix.androidtesis.Constantes;
import com.example.felix.androidtesis.Mysingleton;
import com.example.felix.androidtesis.R;
import com.example.felix.androidtesis.Utilidades;
import com.example.felix.androidtesis.modelo.Respuesta;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ReservacionActivity extends AppCompatActivity {

    private Context mContext;

    private FloatingActionButton fabFechaInicio;
    private FloatingActionButton fabFechaFin;

    private TextView tvFechaInicio;
    private TextView tvFechaFin;

    private String mFechaInicio;
    private String mFechaFin;

    private EditText etHabitaciones;
    private TextInputLayout tilHabitaciones;
    private int mIdPaquete;
    private int mIdUsuario;
    private Gson mGson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservacion);


        if (getIntent() != null) {
            mIdPaquete = getIntent().getIntExtra(Constantes.ID_PAQUETE, -1);
        }

        mContext = this;

        mIdUsuario = Utilidades.getLoggedUserId(mContext);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
            toolbar.setTitle("");
        }


        fabFechaInicio = (FloatingActionButton) findViewById(R.id.fab_fecha_inicio);
        fabFechaFin = (FloatingActionButton) findViewById(R.id.fab_fecha_fin);

        tilHabitaciones = (TextInputLayout) findViewById(R.id.til_habitaciones);
        etHabitaciones = (EditText) findViewById(R.id.et_habitaciones);

        tvFechaInicio = (TextView) findViewById(R.id.tv_fecha_inicio);
        tvFechaFin = (TextView) findViewById(R.id.tv_fecha_fin);


        etHabitaciones.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                tilHabitaciones.setError(null);

                if (charSequence != null) {
                    try {
                        int numeroHabitaciones = Integer.parseInt(charSequence.toString());

                        if (numeroHabitaciones > 3) {
                            Snackbar.make(getCurrentFocus(), mContext.getString(R.string.error_max_habitaciones), Snackbar.LENGTH_SHORT)
                                    .show();

                            etHabitaciones.setText("");
                            tilHabitaciones.setError(mContext.getString(R.string.error_max_habitaciones));
                        }


                    } catch (NumberFormatException e) {

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        fabFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                mFechaInicio = +dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                tvFechaInicio.setText(mFechaInicio);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        fabFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                mFechaFin = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                tvFechaFin.setText(mFechaFin);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reservacion, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_reservar:
                if (validarDatos()) {

                    String url = Conexion.getConexion() + "android/reservarP";


                    final ProgressDialog progressDialog = new ProgressDialog(mContext);
                    progressDialog.setTitle("Reserva");
                    progressDialog.setMessage("Procesando...");
                    progressDialog.setCancelable(false);
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if (progressDialog != null && progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }


                                    Respuesta respuesta = mGson.fromJson(response, Respuesta.class);

                                    if (respuesta.getStatus() == 1) {
                                        AlertDialog dialog = new AlertDialog.Builder(mContext)
                                                .setTitle("Reservacion")
                                                .setMessage("Reservacion procesada exitosamente")
                                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        finish();
                                                    }
                                                }).create();
                                        dialog.show();
                                    }

                                    Log.d("RESPUESTA", response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    if (progressDialog != null && progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }

                                    Snackbar.make(getCurrentFocus(), "Error al procesar. Intente de nuevo", Snackbar.LENGTH_SHORT)
                                            .show();

                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();

                            params.put("id_usuario", String.valueOf(mIdUsuario));
                            params.put("id_paquete", String.valueOf(mIdPaquete));
                            params.put("fecha_in", mFechaInicio);
                            params.put("fecha_out", mFechaFin);
                            params.put("huespedes", etHabitaciones.getText().toString());

                            return params;
                        }
                    };
                    Mysingleton.getmInstance(mContext).addToRequestQue(stringRequest);
                }

                return true;
            default:
                return false;
        }
    }

    private boolean validarDatos() {

        if (mFechaInicio == null) {
            Snackbar.make(getCurrentFocus(), "Seleccione una fecha de inicio", Snackbar.LENGTH_SHORT)
                    .show();
            return false;
        }

        if (mFechaFin == null) {
            Snackbar.make(getCurrentFocus(), "Seleccione una fecha de fin", Snackbar.LENGTH_SHORT)
                    .show();
            return false;
        }

        if (etHabitaciones.getText().toString().isEmpty()) {
            Snackbar.make(getCurrentFocus(), "Ingrese el numero de habitaciones", Snackbar.LENGTH_SHORT)
                    .show();
            return false;
        }

        try {
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

//            String str1 = "9/10/2015";
            Date fechaInicio = formatter.parse(mFechaInicio);

//            String str2 = "10/10/2015";
            Date fechaFin = formatter.parse(mFechaFin);

            if (fechaInicio.compareTo(fechaFin) > 0) {

                Snackbar.make(getCurrentFocus(), "La fecha de fin no puede ser mayor que la inicio", Snackbar.LENGTH_LONG)
                        .show();

//                System.out.println("date2 is Greater than my date1");
            }

        } catch (ParseException e1) {
            e1.printStackTrace();
        }


        return true;


    }
}
