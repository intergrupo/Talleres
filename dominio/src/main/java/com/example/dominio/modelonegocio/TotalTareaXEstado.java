package com.example.dominio.modelonegocio;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class TotalTareaXEstado {

    private Estado estado;
    private int cantidad;

    public TotalTareaXEstado() {
        estado =  new Estado();
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
