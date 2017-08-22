package com.example.dominio.modelonegocio;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class ReporteNotificacion implements Serializable {

    private String codigoCorreria;
    private String codigoOrdenTrabajo;
    private String codigoTarea;
    private String codigoLabor;
    private String codigoNotificacion;
    private String codigoItem;
    private String codigoLista;
    private String orden;
    private String descripcion;
    private boolean descarga;

    public ReporteNotificacion() {
        codigoCorreria = "";
        codigoOrdenTrabajo = "";
        codigoTarea = "";
        codigoLabor = "";
        codigoNotificacion = "";
        codigoItem = "";
        codigoLista = "";
        orden = "";
        descripcion = "";
    }

    public boolean esClaveLlena() {
        return !codigoCorreria.isEmpty() && !codigoOrdenTrabajo.isEmpty() && !codigoTarea.isEmpty() &&
                !codigoLabor.isEmpty() && !codigoNotificacion.isEmpty() && !codigoItem.isEmpty() &&
                !codigoLista.isEmpty();
    }

    public String getCodigoCorreria() {
        return codigoCorreria;
    }

    public void setCodigoCorreria(String codigoCorreria) {
        this.codigoCorreria = codigoCorreria;
    }

    public String getCodigoOrdenTrabajo() {
        return codigoOrdenTrabajo;
    }

    public void setCodigoOrdenTrabajo(String codigoOrdenTrabajo) {
        this.codigoOrdenTrabajo = codigoOrdenTrabajo;
    }

    public String getCodigoTarea() {
        return codigoTarea;
    }

    public void setCodigoTarea(String codigoTarea) {
        this.codigoTarea = codigoTarea;
    }

    public String getCodigoLabor() {
        return codigoLabor;
    }

    public void setCodigoLabor(String codigoLabor) {
        this.codigoLabor = codigoLabor;
    }

    public String getCodigoNotificacion() {
        return codigoNotificacion;
    }

    public void setCodigoNotificacion(String codigoNotificacion) {
        this.codigoNotificacion = codigoNotificacion;
    }

    public String getCodigoItem() {
        return codigoItem;
    }

    public void setCodigoItem(String codigoItem) {
        this.codigoItem = codigoItem;
    }

    public String getCodigoLista() {
        return codigoLista;
    }

    public void setCodigoLista(String codigoLista) {
        this.codigoLista = codigoLista;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean getDescarga() {
        return descarga;
    }

    public void setDescarga(boolean descarga) {
        this.descarga = descarga;
    }

}

