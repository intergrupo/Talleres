package com.example.dominio.modelonegocio;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class Labor implements Serializable {

    private String codigoLabor;
    private String nombre;
    private String rutina;
    private String parametros;

    public Labor() {
        codigoLabor = "";
        nombre = "";
        rutina = "";
        parametros = "";
    }

    public String getCodigoLabor() {
        return codigoLabor;
    }

    public void setCodigoLabor(String codigoLabor) {
        this.codigoLabor = codigoLabor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRutina() {
        return rutina;
    }

    public void setRutina(String rutina) {
        this.rutina = rutina;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }
}