package com.example.dominio.modelonegocio;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class ArchivoAdjunto implements Serializable {

    private String nombreArchivo;
    private String rutaArchivo;

    public ArchivoAdjunto() {
        nombreArchivo = "";
        rutaArchivo = "";
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getRutaArchivo() {
        return new ParametrosTextoYListas(rutaArchivo).getCodigoFiltro();
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }
}
