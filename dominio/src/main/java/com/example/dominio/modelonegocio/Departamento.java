package com.example.dominio.modelonegocio;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 8/22/17.
 */

public class Departamento implements Serializable {

    private String codigoDepartamento;
    private String descripcion;
    private Municipio municipio;

    public Departamento() {
        codigoDepartamento = "";
        descripcion = "";
        municipio = new Municipio();
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public boolean esCLaveLlena(){
        return !codigoDepartamento.isEmpty() && !municipio.getCodigoMunicipio().isEmpty();
    }
}