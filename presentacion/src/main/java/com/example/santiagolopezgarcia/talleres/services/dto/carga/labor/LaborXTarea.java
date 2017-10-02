package com.example.santiagolopezgarcia.talleres.services.dto.carga.labor;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_LABORXTAREA")
public class LaborXTarea extends BaseDtoCarga {

    public LaborXTarea() {
        CodigoTarea = "";
        CodigoLabor = "";
        Obligatoria = "";
        Parametros = "";
    }

    @Element()
    public String CodigoTarea;

    @Element()
    public String CodigoLabor;

    @Element(required = false)
    public String Obligatoria;

    @Element(required = false)
    public int Orden;

    @Element(required = false)
    public String Parametros;
}