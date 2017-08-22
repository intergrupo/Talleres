package com.example.dominio.modelonegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class ItemXNotificacion implements Serializable {

    private String codigoNotificacion;
    private String codigoItem;
    private int orden;
    private boolean obligatorio;
    private boolean descarga;
    private List<ReporteNotificacion> listaReporteNotificacion;

    public ItemXNotificacion() {
        codigoNotificacion = "";
        codigoItem = "";
        listaReporteNotificacion = new ArrayList<>();
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

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public List<ReporteNotificacion> getListaReporteNotificacion() {
        return listaReporteNotificacion;
    }

    public void setListaReporteNotificacion(List<ReporteNotificacion> listaReporteNotificacion) {
        this.listaReporteNotificacion = listaReporteNotificacion;
    }

    public boolean isObligatorio() {
        return obligatorio;
    }

    public boolean isDescarga() {
        return descarga;
    }

    public void setDescarga(boolean descarga) {
        this.descarga = descarga;
    }

    public boolean esClaveLlena(){
        return !codigoNotificacion.isEmpty() && !codigoItem.isEmpty();
    }
}