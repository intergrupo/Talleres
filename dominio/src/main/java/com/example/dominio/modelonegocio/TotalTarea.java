package com.example.dominio.modelonegocio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class TotalTarea {

    private String nombre;
    private int cantidad;
    private List<TotalTareaXEstado> listaTareaXEstado;

    public TotalTarea() {
        nombre = "";
        listaTareaXEstado = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombreTarea) {
        this.nombre = nombreTarea;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public List<TotalTareaXEstado> getListaTareaXEstado() {
        return listaTareaXEstado;
    }

    public void setListaTareaXEstado(List<TotalTareaXEstado> listaTareaXEstado) {
        this.listaTareaXEstado = listaTareaXEstado;
    }
}