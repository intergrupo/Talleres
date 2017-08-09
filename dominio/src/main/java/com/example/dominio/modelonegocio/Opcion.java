package com.example.dominio.modelonegocio;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */

public class Opcion implements Serializable {

    private int identificador;
    private String codigoOpcion;
    private String descripcion;
    private String rutina;
    private String parametros;
    private String menu;
    private String codigoTarea;
    private String codigoTrabajo;

    public Opcion() {
        this.codigoOpcion = "";
        this.descripcion = "";
        this.rutina = "";
        this.parametros = "";
        this.menu = "";
    }

    public String getCodigoOpcion() {
        return codigoOpcion;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
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

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getCodigoTarea() {
        return codigoTarea;
    }

    public void setCodigoTarea(String codigoTarea) {
        this.codigoTarea = codigoTarea;
    }

    public String getCodigoTrabajo() {
        return codigoTrabajo;
    }

    public void setCodigoTrabajo(String codigoTrabajo) {
        this.codigoTrabajo = codigoTrabajo;
    }

    public enum OpcionMenu {

        NINGUNA("ENEJECUCION", "0"),
        CORRERIAOT("Menu Correria OT", "1"),
        PRINCIPALOT("Menu Pantalla Principal OT", "2"),
        CORRERIATAREA("Menu Correria Tarea", "3"),
        PRINCIPALTAREALECTURA("Menu PantallaPrincipal Tarea", "4"),
        PRINCIPALTAREAREVISIONES("Menu PantallaPrincipal Tarea", "5"),
        INFORMACIONCORRERIA("Menu Pantalla Informacion Correria", "6");

        private String nombre;
        private String codigo;

        OpcionMenu(String nombre, String codigo) {
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

        public static OpcionMenu getEstado(int valor) {
            switch (valor) {
                case 1:
                    return CORRERIAOT;
                case 2:
                    return PRINCIPALOT;
                case 3:
                    return CORRERIATAREA;
                case 4:
                    return PRINCIPALTAREALECTURA;
                case 5:
                    return PRINCIPALTAREAREVISIONES;
                case 6:
                    return INFORMACIONCORRERIA;
                default:
                    return NINGUNA;
            }
        }
    }
}