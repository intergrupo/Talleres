package com.example.santiagolopezgarcia.talleres.services.dto.carga.correria;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_CONTRATO")
public class Contrato extends BaseDtoCarga {

    public Contrato() {
        CodigoContrato = "";
        Nombre = "";
    }

    @Element
    public String CodigoContrato;

    @Element
    public String Nombre;
}
