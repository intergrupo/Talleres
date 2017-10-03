package com.example.dominio.modelonegocio;

import java.util.regex.Pattern;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class ParametrosCorreria {

    private TipoVizualizacion tipoVizualizacion;
    private ParametrosOpcionLlave2 parametrosOpcionLlave2;

    public ParametrosCorreria(String parametro) {
        tipoVizualizacion = TipoVizualizacion.Tarea;
        parametrosOpcionLlave2 = new ParametrosOpcionLlave2("");
        if (!parametro.isEmpty()) {
            String[] parametros = parametro.split(Pattern.quote("|"));
            String[] visualizacion = parametros[0].split(Pattern.quote(","));

            if(parametros.length == 2) {
                parametrosOpcionLlave2 = new ParametrosOpcionLlave2(parametros[1]);
            }

            String tipo = visualizacion[0];
            switch (tipo) {
                case "T":
                    tipoVizualizacion = TipoVizualizacion.Tarea;
                    break;
                case "O":
                    tipoVizualizacion = TipoVizualizacion.OrdenTrabajo;
                    break;
            }
        }
    }

    public TipoVizualizacion getTipoVizualizacion() {
        return tipoVizualizacion;
    }

    public enum TipoVizualizacion {
        Tarea,
        OrdenTrabajo
    }

    public ParametrosOpcionLlave2 getParametrosOpcionLlave2() {
        return parametrosOpcionLlave2;
    }
}