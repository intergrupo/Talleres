package com.example.dominio.modelonegocio;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class MultiOpcion implements Serializable {

    private String codigoTipoOpcion;
    private String codigoOpcion;
    private String descripcion;

    public MultiOpcion() {

        codigoTipoOpcion = "";
        codigoOpcion = "";
        descripcion = "";
    }

    public String getCodigoTipoOpcion() {
        return codigoTipoOpcion;
    }

    public void setCodigoTipoOpcion(String codigoTipoOpcion) {
        this.codigoTipoOpcion = codigoTipoOpcion;
    }

    public String getCodigoOpcion() {
        return codigoOpcion;
    }

    public void setCodigoOpcion(String codigoOpcion) {
        this.codigoOpcion = codigoOpcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean esClaveLlena(){
        return !codigoOpcion.isEmpty() && !codigoTipoOpcion.isEmpty();
    }
}
