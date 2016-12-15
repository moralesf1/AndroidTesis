package com.example.felix.androidtesis;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.felix.androidtesis.Modelo.*;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Sesion extends Fragment {
    private OnLogin mOnLogin;
    Conexion c = new Conexion();
    String url = c.getConexion()+"android/authuser";
    Gson gson = new Gson();
    private AppCompatActivity mContext;

    interface OnLogin{
        /**
         * Esto se utiliza para hacer llamado a una funcion en la activity principal luego se implementa en la activity el llamado a esta interface
         *
         * @param datos
         */
        void onUserLogging(Usuario datos);
    }
    EditText usuario,password;
    Button loggin;
    TextView txtBTN;
    public static final String TAG = "Sesion";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sesion,container,false);
        loggin = (Button)v.findViewById(R.id.logginBTN);
        txtBTN = (TextView)v.findViewById(R.id.registroTXT);
        usuario = (EditText)v.findViewById(R.id.usu);
        password = (EditText)v.findViewById(R.id.pass);
        mContext = (AppCompatActivity) getActivity();
        loggin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String user = usuario.getText().toString();
                final String pass = password.getText().toString();
                Boolean check = true;
                if (user.equals("")){
                    usuario.setError("¡Este campo es obligatorio!");
                    check = false;
                }
                if (!user.equals("") && !android.util.Patterns.EMAIL_ADDRESS.matcher(user).matches()){
                    usuario.setError("Formato de correo invalido.");
                    usuario.setText("");
                    usuario.requestFocus();
                    check = false;
                }
                if (pass.equals("")){
                    password.setError("¡Este campo es obligatorio!");
                    check = false;
                }
                if (check){
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (mOnLogin != null){
                                        Usuario usuario = gson.fromJson(response,Usuario.class);
                                        mOnLogin.onUserLogging(usuario);
//                                        Log.v("respuesta ",usuario[0].getNombre());
//                                        Usuario[] usuarios = new Usuario[]{new Usuario()};
//                                        ArrayList<Usuario> usuarios1 = new ArrayList<Usuario>(Arrays.asList(usuarios));

//                                        Toast.makeText(mContext,usuario.getEmail(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    final Snackbar sn = Snackbar.make(v,"¡Error en la conxion! Intente de nuevo",Snackbar.LENGTH_LONG);
                                    sn.setAction("OK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            sn.dismiss();
                                        }
                                    }).show();
                                    error.printStackTrace();
                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<>();
                            params.put("email",user);
                            params.put("password",pass);
                            params.put("recordar","false");
                            return params;
                        }
                    };
                    Mysingleton.getmInstance(mContext).addToRequestQue(stringRequest);
                }
            }
        });
        txtBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            Registro registro = Registro.newInstance("hola","hola2");
            Registro registro = new Registro();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_inicio,registro,registro.TAG).commit();


//                if(mOnLogin != null){
//                    mOnLogin.onUserLogging("felix");
//                }
            }
        });
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLogin) {
            mOnLogin = (OnLogin) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }
}
