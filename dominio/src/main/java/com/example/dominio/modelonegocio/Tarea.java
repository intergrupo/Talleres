package com.example.dominio.modelonegocio;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class Tarea implements Serializable {

    private String codigoTarea;
    private String nombre;
    private String codigoImpresion;
    private String rutina;
    private String parametros;

    public Tarea() {
        codigoTarea = "";
        nombre = "";
        codigoImpresion = "";
        rutina = "";
        parametros = "";
    }

    public String getCodigoTarea() {
        return codigoTarea;
    }

    public void setCodigoTarea(String codigoTarea) {
        this.codigoTarea = codigoTarea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoImpresion() {
        return codigoImpresion;
    }

    public void setCodigoImpresion(String codigoImpresion) {
        this.codigoImpresion = codigoImpresion;
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

    public enum RutinaTarea {
        TAREAREVISIONES("TareaRevisiones"),
        TAREALECTURA("TareaLectura");

        private String codigo;

        RutinaTarea(String codigo) {
            this.codigo = codigo;
        }

        public String getCodigo(){
            return codigo;
        }
    }

    public enum EstadoTarea {

        NINGUNA("ENEJECUCION", "N"),
        PENDIENTE("Pendiente", "P"),
        EJECUTADA("Ejecutado", "E"),
        IMPRESA("Impresa", "I"),
        SINASIGNAR("SinAsignar", "S"),
        CANCELADA("Cancelada", "C"),
        SINLEER("Sin leer", "0"),
        LEIDA("Leida", "1"),
        CAUSANOLECTURA("Con causa de no lectura", "2"),
        CRITICADAOBSERVADA("Criticada y observada", "3"),
        CRITICADANOOBSERVADA("Criticada no observada", "4"),
        CRITICADACAMBIABLE("Criticada cambiable", "5"),
        CREARLABORES("Crear labores","L");

        private String nombre;
        private String codigo;

        EstadoTarea(String nombreClub, String codigo) {
            this.nombre = nombreClub;
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

        public static EstadoTarea getEstado(String valor) {
            switch (valor) {
                case "P":
                    return PENDIENTE;
                case "E":
                    return EJECUTADA;
                case "I":
                    return IMPRESA;
                case "S":
                    return SINASIGNAR;
                case "C":
                    return CANCELADA;
                case "L":
                    return CREARLABORES;
                case "0":
                    return SINLEER;
                case "1":
                    return LEIDA;
                case "2":
                    return CAUSANOLECTURA;
                case "3":
                    return CRITICADAOBSERVADA;
                case "4":
                    return CRITICADANOOBSERVADA;
                case "5":
                    return CRITICADACAMBIABLE;
                default:
                    return NINGUNA;
            }
        }
    }
}