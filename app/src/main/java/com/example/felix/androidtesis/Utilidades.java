package com.example.felix.androidtesis;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by saleventa on 1/25/17.
 */

public class Utilidades {

    public static SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences("usuario", Context.MODE_PRIVATE);
    }

    public static boolean isUserLogged(Context context) {
        SharedPreferences pref = getSharedPref(context);
        int id = pref.getInt("id", -1);
        return id != -1;
    }

    public static int getLoggedUserId(Context context) {
        SharedPreferences pref = getSharedPref(context);
        return pref.getInt("id", -1);
    }


}
