package com.example.santiagolopezgarcia.talleres.integracion.carga;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class SolicitudRemplazoMaestros {
    private String mensaje;
    private String versionSistema;
    private String versionTerminal;

    public SolicitudRemplazoMaestros() {
        StringBuilder mensajeBuilder = new StringBuilder();
        mensajeBuilder.append("MAESTROS DESACTUALIZADOS, Actualizar maestros BORRA todas las correrías e información de la terminal.");
        mensajeBuilder.append(" ¿Desea actualizar Maestros?\n");
        mensajeBuilder.append("Versiones no corresponden.\n");

        mensaje = mensajeBuilder.toString();
    }

    public String getMensaje() {
        mensaje += "Sistema: " + versionSistema;
        mensaje += "\nTerminal: " + versionTerminal;

        return mensaje;
    }

    public void setVersionSistema(String versionSistema) {
        this.versionSistema = versionSistema;
    }

    public void setVersionTerminal(String versionTerminal) {
        this.versionTerminal = versionTerminal;
    }

    public boolean solicitarRemplazoMaestros() {
        return (this.versionTerminal != null && this.versionSistema != null);
    }
}
