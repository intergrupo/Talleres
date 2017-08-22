package com.example.dominio.modelonegocio;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class Estado implements Serializable {

    private String codigoEstado;
    private String descripcion;
    private GrupoEstado grupoEstado;

    public Estado() {
        codigoEstado = "";
        descripcion = "";
        grupoEstado = GrupoEstado.NINGUNO;
    }

    public String getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(String codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public GrupoEstado getGrupoEstado() {
        return grupoEstado;
    }

    public void setGrupoEstado(GrupoEstado grupoEstado) {
        this.grupoEstado = grupoEstado;
    }

    public enum GrupoEstado {

        NINGUNO ("Ninguno", "00"),
        TAREASREVISIONES("Tareas Revisiones", "01"),
        LABORES("Labores", "02"),
        TAREASLECTURA("Tareas Lectura", "03"),
        OT("OT", "04");

        private String nombre;
        private String codigo;

        GrupoEstado(String nombre, String codigo) {
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

        public static GrupoEstado getEstado(int valor) {
            switch (valor) {
                case 1:
                    return TAREASREVISIONES;
                case 2:
                    return LABORES;
                case 3:
                    return TAREASLECTURA;
                case 4:
                    return OT;
                default:
                    return NINGUNO;
            }
        }

        public static GrupoEstado getEstado(String valor) {
            switch (valor) {
                case "01":
                    return TAREASREVISIONES;
                case "02":
                    return LABORES;
                case "03":
                    return TAREASLECTURA;
                case "04":
                    return OT;
                default:
                    return NINGUNO;
            }
        }
    }
}