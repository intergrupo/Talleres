package com.example.dominio.modelonegocio;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class TareaXTrabajo implements Serializable {

    private Tarea tarea;
    private Trabajo trabajo;

    public TareaXTrabajo() {
        this.tarea = new Tarea();
        this.trabajo = new Trabajo();
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public Trabajo getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }

    public boolean esClaveLlena(){
        return !tarea.getCodigoTarea().isEmpty() && !trabajo.getCodigoTrabajo().isEmpty();
    }
}