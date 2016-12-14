package com.example.felix.androidtesis;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Sesion extends Fragment {
    EditText usuario,password;
    Button loggin;
    TextView txtBTN;

    private OnLogin mOnLogin;


    interface OnLogin{
        /**
         * Esto se utiliza para hacer llamado a una funcion en la activity principal luego se implementa en la activity el llamado a esta interface
         *
         * @param usuario
         * @param correo
         */
        void onUserLogging(String usuario, String correo);
    }

    public static final String TAG = "Sesion";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sesion,container,false);
        loggin = (Button)view.findViewById(R.id.logginBTN);
        txtBTN = (TextView)view.findViewById(R.id.registroTXT);
        loggin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Iniciar sesion btn fragment",Toast.LENGTH_LONG).show();
            }
        });
        txtBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnLogin != null){
                    mOnLogin.onUserLogging("felix", "felix@f.com");
                }
            }
        });
        return view;
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
