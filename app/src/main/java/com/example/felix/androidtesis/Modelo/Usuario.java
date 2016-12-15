package com.example.felix.androidtesis.Modelo;

import java.util.ArrayList;

/**
 * Created by saleventa9 on 12/15/2016.
 */

public class Usuario {
    public String email,nombre,apellido;
    public int id;

    public ArrayList<Usuario> items;
    public Usuario() {
    }

    public Usuario(String email, String nombre, String apellido, int id) {
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
