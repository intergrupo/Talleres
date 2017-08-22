package com.example.dominio.modelonegocio;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class Carga implements Serializable {

    private String versionSoftware;
    private String versionMaestros;

    public Carga() {
        versionSoftware = "";
        versionMaestros = "";
    }

    public String getVersionSoftware() {
        return versionSoftware;
    }

    public void setVersionSoftware(String versionSoftware) {
        this.versionSoftware = versionSoftware;
    }

    public String getVersionMaestros() {
        return versionMaestros;
    }

    public void setVersionMaestros(String versionMaestros) {
        this.versionMaestros = versionMaestros;
    }
}