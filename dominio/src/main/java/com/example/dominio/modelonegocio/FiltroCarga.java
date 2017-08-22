package com.example.dominio.modelonegocio;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class FiltroCarga {
    String codigoCorreria;
    String codigoOrdenTrabajo;
    String codigoTrabajo;
    String codigoTarea;

    public FiltroCarga(String codigoCorreria, String codigoOrdenTrabajo, String codigoTrabajo, String codigoTarea) {
        this.codigoCorreria = codigoCorreria;
        this.codigoOrdenTrabajo = codigoOrdenTrabajo;
        this.codigoTrabajo = codigoTrabajo;
        this.codigoTarea = codigoTarea;
    }

    public String getCodigoCorreria() {
        return codigoCorreria;
    }

    public String getCodigoOrdenTrabajo() {
        return codigoOrdenTrabajo;
    }

    public String getCodigoTrabajo() {
        return codigoTrabajo;
    }

    public String getCodigoTarea() {
        return codigoTarea;
    }
}
