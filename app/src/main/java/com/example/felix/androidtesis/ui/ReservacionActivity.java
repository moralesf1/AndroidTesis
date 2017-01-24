package com.example.felix.androidtesis.ui;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.felix.androidtesis.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ReservacionActivity extends AppCompatActivity {

    private Context mContext;

    private FloatingActionButton fabFechaInicio;
    private FloatingActionButton fabFechaFin;

    private TextView tvFechaInicio;
    private TextView tvFechaFin;

    private String mFechaInicio;
    private String mFechaFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservacion);

        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("");
            toolbar.setTitle("");
        }


        fabFechaInicio = (FloatingActionButton) findViewById(R.id.fab_fecha_inicio);
        fabFechaFin = (FloatingActionButton) findViewById(R.id.fab_fecha_fin);

        tvFechaInicio = (TextView) findViewById(R.id.tv_fecha_inicio);
        tvFechaFin = (TextView) findViewById(R.id.tv_fecha_fin);

        fabFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                mFechaInicio = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
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
                                mFechaFin = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
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
}
