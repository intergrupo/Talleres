package com.example.dominio.modelonegocio;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 9/26/17.
 */

public class TrabajoXContrato implements Serializable {

    private Contrato contrato;
    private Trabajo trabajo;

    public TrabajoXContrato() {
        this.contrato = new Contrato();
        this.trabajo = new Trabajo();
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public Trabajo getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }

    public boolean esClaveLlena(){
        return !contrato.getCodigoContrato().isEmpty() && !trabajo.getCodigoTrabajo().isEmpty();
    }
}