package com.example.santiagolopezgarcia.talleres.services.dto.carga.trabajo;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/3/17.
 */

@Root(name = "SIRIUS_TRABAJO")
public class Trabajo extends BaseDtoCarga {

    public Trabajo() {
        CodigoTrabajo = "";
        Descripcion = "";
        Parametros = "";
    }

    @Element
    public String CodigoTrabajo;
    @Element
    public String Descripcion;
    @Element(required = false)
    public String Parametros;
}