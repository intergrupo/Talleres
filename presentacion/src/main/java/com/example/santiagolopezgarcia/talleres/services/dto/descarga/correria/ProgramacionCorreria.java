package com.example.santiagolopezgarcia.talleres.services.dto.descarga.correria;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_PROGRAMACIONCORRERIA")
public class ProgramacionCorreria {

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
