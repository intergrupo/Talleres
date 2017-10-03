package com.example.dominio.modelonegocio;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public enum ClasificacionVista implements Serializable {

    NINGUNA("N"),
    ORDENTRABAJO("OT"),
    TAREA("T");

    private String codigo;

    ClasificacionVista(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public static ClasificacionVista getClasificacion(String valor) {
        switch (valor) {
            case "OT":
                return ORDENTRABAJO;
            case "T":
                return TAREA;
            default:
                return NINGUNA;
        }
    }
}
