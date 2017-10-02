package com.example.santiagolopezgarcia.talleres.services.dto.carga.impresion;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_PARRAFOIMPRESION")
public class ParrafoImpresion extends BaseDtoCarga {

    public ParrafoImpresion() {
        CodigoImpresion = "";
        CodigoParrafo = "";
        Parrafo = "";
    }

    @Element()
    public String CodigoImpresion;

    @Element()
    public String CodigoParrafo;

    @Element(required = false)
    public String Parrafo;

}