package com.example.felix.androidtesis.modelo;

import java.io.Serializable;

/**
 * Created by saleventa on 1/17/17.
 */

public class Ciudad implements Serializable {

    private int id;
    private int estado_id;
    private String ciudad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstado_id() {
        return estado_id;
    }

    public void setEstado_id(int estado_id) {
        this.estado_id = estado_id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}
