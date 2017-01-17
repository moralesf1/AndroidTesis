package com.example.felix.androidtesis.modelo;

import java.io.Serializable;

/**
 * Created by saleventa on 1/17/17.
 */

public class Foto implements Serializable {

    private int id;
    private int id_paquete;
    private int posicion;

    private String foto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_paquete() {
        return id_paquete;
    }

    public void setId_paquete(int id_paquete) {
        this.id_paquete = id_paquete;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
