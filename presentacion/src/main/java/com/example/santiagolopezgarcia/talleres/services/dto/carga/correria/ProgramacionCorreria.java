package com.example.santiagolopezgarcia.talleres.services.dto.carga.correria;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_PROGRAMACIONCORRERIA")
public class ProgramacionCorreria extends BaseDtoCarga {

    public ProgramacionCorreria() {
        IdProgramacionCorreria = "";
        CodigoPrograma = "";
        CodigoCorreria = "";
        NumeroTerminal = "";
        EstadoProgramada = "";
    }

    @Element
    public String IdProgramacionCorreria;

    @Element
    public String CodigoPrograma;

    @Element
    public String CodigoCorreria;

    @Element
    public String NumeroTerminal;

    @Element
    public String EstadoProgramada;
}