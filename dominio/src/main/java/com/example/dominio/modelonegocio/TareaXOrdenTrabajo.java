package com.example.dominio.modelonegocio;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class TareaXOrdenTrabajo extends TareaXTrabajoOrdenTrabajo implements Serializable {

    private OrdenTrabajo ordenTrabajo;

    public OrdenTrabajo getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public TareaXOrdenTrabajo() {
        super();
        ordenTrabajo = new OrdenTrabajo();
    }
}

