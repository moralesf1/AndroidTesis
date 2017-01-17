package com.example.felix.androidtesis;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.felix.androidtesis.ui.PaquetesFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText usuario, password;
    Conexion c = new Conexion();
    String url = c.getConexion() + "android/authuser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario = (EditText) findViewById(R.id.usu);
        password = (EditText) findViewById(R.id.pass);
        Log.v("conexion ", c.getConexion());





    }

    public void registrar(View v) {
        startActivity(new Intent(MainActivity.this, Registrar.class));
    }

    public void login(View v) {
        final String user = usuario.getText().toString();
        final String pass = password.getText().toString();
        Boolean check = true;
        if (user.equals("")) {
            usuario.setError("¡Este campo es obligatorio!");
            check = false;
        }
        if (!user.equals("") && !android.util.Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
            usuario.setError("Formato de correo invalido.");
            usuario.setText("");
            usuario.requestFocus();
            check = false;
        }
        if (pass.equals("")) {
            password.setError("¡Este campo es obligatorio!");
            check = false;
        }
        if (check) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONArray jsonArray = null;
                            try {
                                jsonArray = new JSONArray("[" + response + "]");
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                if (jsonObject.has("status")) {
                                    Toast.makeText(getApplicationContext(), "Credenciales Invalidas!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error en la conexion", Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", user);
                    params.put("password", pass);
                    params.put("recordar", "false");
                    return params;
                }
            };
            Mysingleton.getmInstance(MainActivity.this).addToRequestQue(stringRequest);
        }

    }
}
