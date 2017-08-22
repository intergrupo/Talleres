package com.example.dominio.modelonegocio;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class ParametrosNotificacionFecha implements Serializable {

    public static final String FECHA = "F";
    public static final String FECHA_Y_HORA = "D";
    public static final String HORA = "H";
    private boolean adicionar;
    private boolean editar;
    private boolean eliminar;
    private boolean consulta;
    private String tipoFecha;

    public ParametrosNotificacionFecha(String parametros) {
        tipoFecha = "";
        if (parametros.split(Pattern.quote("|")).length > 1) {
            tipoFecha = parametros.split(Pattern.quote("|"))[1];
            for (String opcion : parametros.split(Pattern.quote("|"))[0].split(Pattern.quote(","))) {
                switch (opcion) {
                    case "A":
                        adicionar = true;
                        break;
                    case "E":
                        editar = true;
                        break;
                    case "D":
                        eliminar = true;
                        break;
                    case "L":
                        consulta = true;
                        break;
                }
            }
        } else {
            for (String opcion : parametros.split(Pattern.quote(","))) {
                switch (opcion) {
                    case "A":
                        adicionar = true;
                        break;
                    case "E":
                        editar = true;
                        break;
                    case "D":
                        eliminar = true;
                        break;
                    case "L":
                        consulta = true;
                        break;
                }
            }
        }
    }

    public boolean isAdicionar() {
        return adicionar;
    }

    public boolean isEditar() {
        return editar;
    }

    public boolean isEliminar() {
        return eliminar;
    }

    public String getTipoFecha() {
        return tipoFecha;
    }

    public boolean isConsulta() {
        return consulta;
    }
}
