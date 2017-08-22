package com.example.dominio.modelonegocio;

import java.util.regex.Pattern;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class ParametrosOpcionNotificacion {

    private String codigoNotificacion;
    private String codigoTarea;
    private String codigoLabor;

    public ParametrosOpcionNotificacion(String parametros) {
        codigoNotificacion = "";
        codigoTarea = "";
        codigoLabor = "";
        String[] claves = parametros.split(Pattern.quote("_"));
        if (claves.length == 2) {
            codigoNotificacion = parametros.split(Pattern.quote("_"))[0];
            if (claves[1].split(Pattern.quote(",")).length == 2) {
                codigoTarea = claves[1].split(Pattern.quote(","))[0];
                codigoLabor = claves[1].split(Pattern.quote(","))[1];
            }else {
                codigoLabor = claves[1].split(Pattern.quote(","))[0];
            }
        }
    }

    public String getCodigoNotificacion() {
        return codigoNotificacion;
    }

    public void setCodigoNotificacion(String codigoNotificacion) {
        this.codigoNotificacion = codigoNotificacion;
    }

    public String getCodigoLabor() {
        return codigoLabor;
    }

    public void setCodigoLabor(String codigoLabor) {
        this.codigoLabor = codigoLabor;
    }

    public String getCodigoTarea() {
        return codigoTarea;
    }

    public void setCodigoTarea(String codigoTarea) {
        this.codigoTarea = codigoTarea;
    }
}