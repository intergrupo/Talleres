package com.example.dominio.modelonegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class TrabajoXOrdenTrabajo implements Serializable {

    private String codigoCorreria;
    private String codigoOrdenTrabajo;
    private Trabajo trabajo;
    private List<TareaXTrabajoOrdenTrabajo> tareaXOrdenTrabajoList;

    public TrabajoXOrdenTrabajo() {
        codigoCorreria = "";
        codigoOrdenTrabajo = "";
        trabajo = new Trabajo();
        tareaXOrdenTrabajoList = new ArrayList<>();
    }

    public void setCodigoCorreria(String codigoCorreria) {
        this.codigoCorreria = codigoCorreria;
    }

    public String getCodigoCorreria() {
        return codigoCorreria;
    }

    public void setCodigoOrdenTrabajo(String codigoOrdenTrabajo) {
        this.codigoOrdenTrabajo = codigoOrdenTrabajo;
    }

    public String getCodigoOrdenTrabajo() {
        return codigoOrdenTrabajo;
    }

    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }

    public Trabajo getTrabajo() {
        return trabajo;
    }

    public List<TareaXTrabajoOrdenTrabajo> getTareaXOrdenTrabajoList() {
        return tareaXOrdenTrabajoList;
    }

    public void setTareaXOrdenTrabajoList(List<TareaXTrabajoOrdenTrabajo> tareaXOrdenTrabajoList) {
        this.tareaXOrdenTrabajoList = tareaXOrdenTrabajoList;
    }

    public boolean esClaveVacia() {
        return codigoCorreria.isEmpty() && codigoOrdenTrabajo.isEmpty() && trabajo.getCodigoTrabajo().isEmpty();
    }
}
