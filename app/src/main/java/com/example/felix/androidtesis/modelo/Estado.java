package com.example.felix.androidtesis.modelo;

import java.io.Serializable;

/**
 * Created by saleventa on 1/17/17.
 */

public class Estado implements Serializable {

    private int id;
    private String estado;
    private int pais_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getPais_id() {
        return pais_id;
    }

    public void setPais_id(int pais_id) {
        this.pais_id = pais_id;
    }
}
