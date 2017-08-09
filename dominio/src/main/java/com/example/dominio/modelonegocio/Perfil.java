package com.example.dominio.modelonegocio;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */

public class Perfil implements Serializable {

    private String codigoPerfil;
    private String descripcion;
    private ListaOpcion listaOpciones;

    public Perfil() {
        codigoPerfil = "";
        descripcion = "";
        listaOpciones = new ListaOpcion();
    }

    public String getCodigoPerfil() {
        return codigoPerfil;
    }

    public void setCodigoPerfil(String codigoPerfil) {
        this.codigoPerfil = codigoPerfil;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ListaOpcion getListaOpciones() {
        return listaOpciones;
    }

    public void setListaOpciones(ListaOpcion listaOpciones) {
        this.listaOpciones = listaOpciones;
    }
}
