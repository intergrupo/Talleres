package com.example.dominio.modelonegocio;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class ProgramacionCorreria implements Serializable {

    private String idProgramacionCorreria;
    private String numeroTerminal;
    private String codigoCorreria;
    private String estadoProgramada;
    private String codigoPrograma;

    public ProgramacionCorreria() {
        idProgramacionCorreria = "";
        numeroTerminal = "";
        codigoCorreria = "";
        estadoProgramada = "";
        codigoPrograma = "";
    }

    public String getIdProgramacionCorreria() {
        return idProgramacionCorreria;
    }

    public void setIdProgramacionCorreria(String idProgramacionCorreria) {
        this.idProgramacionCorreria = idProgramacionCorreria;
    }

    public String getNumeroTerminal() {
        return numeroTerminal;
    }

    public void setNumeroTerminal(String numeroTerminal) {
        this.numeroTerminal = numeroTerminal;
    }

    public String getCodigoCorreria() {
        return codigoCorreria;
    }

    public void setCodigoCorreria(String codigoCorreria) {
        this.codigoCorreria = codigoCorreria;
    }

    public String getEstadoProgramada() {
        return estadoProgramada;
    }

    public void setEstadoProgramada(String estadoProgramada) {
        this.estadoProgramada = estadoProgramada;
    }

    public String getCodigoPrograma() {
        return codigoPrograma;
    }

    public void setCodigoPrograma(String codigoprograma) {
        this.codigoPrograma = codigoprograma;
    }

    public boolean esClaveLlena() {
        return !idProgramacionCorreria.isEmpty() && !numeroTerminal.isEmpty()
                && !codigoCorreria.isEmpty() && !codigoPrograma.isEmpty();
    }
}
