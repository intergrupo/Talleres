package com.example.dominio.modelonegocio;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class NotificacionOrdenTrabajo implements Serializable {

    public static final String TAREA_CANCELADA = "TAREA CANCELADA";
    public static final String ERROR_TAREA_NO_EXISTE = "ERROR TAREA NO EXISTE";
    public static final String ERROR_TAREA_NO_CANCELAR = "ERROR TAREA NO SE PUEDE CANCELAR";
    public static final String ORDEN_CANCELADA = "ORDEN CANCELADA";
    public static final String ORDEN_NO_CANCELAR = "ORDEN NO SE PUEDE CANCELAR";
    public static final String ERROR_ORDEN_NO_EXISTE = "ERROR ORDEN NO EXISTE";

    private String codigoCorreria;
    private String codigoOrdenTrabajo;
    private String codigoTarea;
    private String operacion;
    private String resultado;

    public NotificacionOrdenTrabajo() {
        codigoCorreria = "";
        codigoOrdenTrabajo = "";
        codigoTarea = "";
        operacion = "";
        resultado = "";
    }

    public String getCodigoCorreria() {
        return codigoCorreria;
    }

    public void setCodigoCorreria(String codigoCorreria) {
        this.codigoCorreria = codigoCorreria;
    }

    public String getCodigoOrdenTrabajo() {
        return codigoOrdenTrabajo;
    }

    public void setCodigoOrdenTrabajo(String codigoOrdenTrabajo) {
        this.codigoOrdenTrabajo = codigoOrdenTrabajo;
    }

    public String getCodigoTarea() {
        return codigoTarea;
    }

    public void setCodigoTarea(String codigoTarea) {
        this.codigoTarea = codigoTarea;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}