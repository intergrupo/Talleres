package com.example.dominio.modelonegocio;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class LaborXTarea implements Serializable {

    private Tarea tarea;
    private Labor labor;
    private boolean obligatoria;
    private int orden;
    private String parametros;

    public LaborXTarea() {
        tarea = new Tarea();
        labor = new Labor();
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public Labor getLabor() {
        return labor;
    }

    public void setLabor(Labor labor) {
        this.labor = labor;
    }

    public boolean isObligatoria() {
        return obligatoria;
    }

    public void setObligatoria(boolean obligatoria) {
        this.obligatoria = obligatoria;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public boolean esClaveLlena(){
        return !tarea.getCodigoTarea().isEmpty() && !labor.getCodigoLabor().isEmpty();
    }
}
