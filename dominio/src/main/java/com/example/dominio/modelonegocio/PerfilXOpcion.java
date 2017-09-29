package com.example.dominio.modelonegocio;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class PerfilXOpcion implements Serializable {

    private Opcion opcion;
    private Perfil perfil;

    public PerfilXOpcion() {
        this.opcion = new Opcion();
        this.perfil = new Perfil();
    }

    public Opcion getOpcion() {
        return opcion;
    }

    public void setOpcion(Opcion opcion) {
        this.opcion = opcion;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public boolean esClaveLlena(){
        return !opcion.getCodigoOpcion().isEmpty() && !perfil.getCodigoPerfil().isEmpty();
    }
}
