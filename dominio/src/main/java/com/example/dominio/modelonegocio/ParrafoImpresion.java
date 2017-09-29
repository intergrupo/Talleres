package com.example.dominio.modelonegocio;

import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class ParrafoImpresion implements Serializable {

    private String codigoImpresion;
    private String codigoParrafo;

    @Nullable
    private String parrafo;


    public ParrafoImpresion() {
        codigoImpresion = "";
        codigoParrafo = "";
        parrafo = "";
    }

    public String getCodigoImpresion() {
        return codigoImpresion;
    }

    public void setCodigoImpresion(String codigoImpresion) {
        this.codigoImpresion = codigoImpresion;
    }

    public String getCodigoParrafo() {
        return codigoParrafo;
    }

    public void setCodigoParrafo(String codigoParrafo) {
        this.codigoParrafo = codigoParrafo;
    }

    @Nullable
    public String getParrafo() {
        return parrafo;
    }

    public void setParrafo(@Nullable String parrafo) {
        this.parrafo = parrafo;
    }

    public boolean esClaveLlena(){
        return !codigoImpresion.isEmpty() && !codigoParrafo.isEmpty();
    }
}
