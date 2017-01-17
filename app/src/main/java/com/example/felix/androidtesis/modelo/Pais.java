package com.example.felix.androidtesis.modelo;

import java.io.Serializable;

/**
 * Created by saleventa on 1/17/17.
 */

public class Pais implements Serializable {


    private int id;
    private String pais;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
