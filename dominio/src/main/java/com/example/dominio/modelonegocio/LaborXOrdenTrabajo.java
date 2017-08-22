package com.example.dominio.modelonegocio;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class LaborXOrdenTrabajo implements Serializable {

    private String codigoCorreria;
    private String codigoOrdenTrabajo;
    private Trabajo trabajo;
    private Tarea tarea;
    private Labor labor;
    private String codigoUsuarioLabor;
    private EstadoLaborXOrdenTrabajo estadoLaborXOrdenTrabajo;
    private Date fechaInicioLabor;
    private Date fechaUltimoLabor;
    private String parametros;

    public LaborXOrdenTrabajo() {
        codigoCorreria = "";
        codigoOrdenTrabajo = "";
        trabajo = new Trabajo();
        tarea = new Tarea();
        labor = new Labor();
        codigoUsuarioLabor = "";
        estadoLaborXOrdenTrabajo = EstadoLaborXOrdenTrabajo.PENDIENTE;
        parametros = "";
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

    public Trabajo getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public Labor getLabor() {
        return this.labor;
    }

    public void setLabor(Labor labor) {
        this.labor = labor;
    }

    public String getCodigoUsuarioLabor() {
        return codigoUsuarioLabor;
    }

    public void setCodigoUsuarioLabor(String codigoUsuarioLabor) {
        this.codigoUsuarioLabor = codigoUsuarioLabor;
    }

    public EstadoLaborXOrdenTrabajo getEstadoLaborXOrdenTrabajo() {
        return estadoLaborXOrdenTrabajo;
    }

    public void setEstadoLaborXOrdenTrabajo(EstadoLaborXOrdenTrabajo estadoLaborXOrdenTrabajo) {
        this.estadoLaborXOrdenTrabajo = estadoLaborXOrdenTrabajo;
    }

    public Date getFechaInicioLabor() {
        return fechaInicioLabor;
    }

    public void setFechaInicioLabor(Date fechaInicioLabor) {
        this.fechaInicioLabor = fechaInicioLabor;
    }

    public Date getFechaUltimoLabor() {
        return fechaUltimoLabor;
    }

    public void setFechaUltimoLabor(Date fechaUltimoLabor) {
        this.fechaUltimoLabor = fechaUltimoLabor;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public boolean esClaveLlena() {
        return !codigoCorreria.isEmpty() &&
                !codigoOrdenTrabajo.isEmpty() &&
                !trabajo.getCodigoTrabajo().isEmpty() &&
                !tarea.getCodigoTarea().isEmpty() &&
                !labor.getCodigoLabor().isEmpty();
    }

    public enum EstadoLaborXOrdenTrabajo {

        PENDIENTE("Pendiente", "P"),
        EJECUTADA("Ejecutado", "E"),
        IMPRESA("Impresa", "I"),
        CERRADA("Cerrada", "C");

        private String nombre;
        private String codigo;

        EstadoLaborXOrdenTrabajo(String nombre, String codigo) {
            this.nombre = nombre;
            this.codigo = codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public String setNombre(String nombre) {
            return this.nombre = nombre;
        }

        public String getCodigo() {
            return codigo;
        }

        public static EstadoLaborXOrdenTrabajo getEstado(int valor) {
            switch (valor) {
                case 1:
                    return PENDIENTE;
                case 2:
                    return EJECUTADA;
                case 3:
                    return IMPRESA;
                case 4:
                    return CERRADA;
                default:
                    return PENDIENTE;
            }
        }

        public static EstadoLaborXOrdenTrabajo getEstado(String valor) {
            switch (valor) {
                case "P":
                    return PENDIENTE;
                case "E":
                    return EJECUTADA;
                case "I":
                    return IMPRESA;
                case "C":
                    return CERRADA;
                default:
                    return PENDIENTE;
            }
        }
    }
}