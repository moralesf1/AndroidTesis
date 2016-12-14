package com.example.felix.androidtesis;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registrar extends AppCompatActivity {

    EditText nombre,apellido,correo,clave,con_clave;
    String name,surname,email,pass,rep_pass;
    AlertDialog.Builder builder;
    String url = "http://192.168.1.112:8000/android/nuevoU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        nombre = (EditText)findViewById(R.id.nombre);
        apellido = (EditText)findViewById(R.id.apellido);
        correo = (EditText)findViewById(R.id.correo);
        clave = (EditText)findViewById(R.id.clave);
        con_clave = (EditText)findViewById(R.id.rep_clave);
        builder = new AlertDialog.Builder(Registrar.this);

    }

    public void registrar_usu(View view) {
        name = nombre.getText().toString();
        surname  = apellido.getText().toString();
        email = correo.getText().toString();
        pass = clave.getText().toString();
        rep_pass = con_clave.getText().toString();
        Boolean check = true;
        if (name.equals("")){
            nombre.setError("Este campo es obligatorio.");
            check = false;
            nombre.requestFocus();
        }
        if (surname.equals("")){
            apellido.setError("Este campo es obligatorio.");
            check = false;
        }
        if (email.equals("")){
            correo.setError("Este campo es obligatorio.");
            check = false;
        }
        if (!email.equals("") && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            correo.setError("Formato de correo invalido.");
            correo.setText("");
            correo.requestFocus();
            check = false;
        }
        if (pass.equals("")){
            clave.setError("Este campo es obligatorio.");
            check = false;
        }
        if (rep_pass.equals("")){
            con_clave.setError("Este campo es obligatorio.");
            check = false;
        }
        if (!rep_pass.equals("") && !pass.equals("") && !pass.equals(rep_pass)){
            clave.setText("");
            con_clave.setText("");
            clave.requestFocus();
            clave.setError("Las contraseñas no coinciden.");
            check = false;
        }
        if (check){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray  jsonArray = new JSONArray("["+response+"]");
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                if (jsonObject.has("email")){
                                    correo.setError(jsonObject.getString("email").replace("[","").replace("]","").replace("\"",""));
                                    correo.setText("");
                                    correo.requestFocus();
                                }
                                if (jsonObject.has("status")){
                                    Toast.makeText(getApplicationContext(),"¡Registro exitoso!",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Registrar.this,MainActivity.class));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Ocurrio un error con la conexion",Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("nombre",name);
                    params.put("apellido",surname);
                    params.put("email",email);
                    params.put("clave",pass);
                    return params;
                }
            };
            Mysingleton.getmInstance(Registrar.this).addToRequestQue(stringRequest);
        }
    }

    public void volver(View view) {
        startActivity(new Intent(Registrar.this,MainActivity.class));
    }
}
