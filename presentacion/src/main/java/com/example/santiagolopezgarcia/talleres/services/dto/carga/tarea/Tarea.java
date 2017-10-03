package com.example.santiagolopezgarcia.talleres.services.dto.carga.tarea;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/3/17.
 */

@Root(name = "SIRIUS_TAREA")
public class Tarea extends BaseDtoCarga {

    public Tarea() {
        CodigoTarea = "";
        Nombre = "";
        CodigoImpresion = "";
        Rutina = "";
        Parametros = "";
    }

    @Element
    public String CodigoTarea;

    @Element
    public String Nombre;

    @Element(required = false)
    public String CodigoImpresion;

    @Element(required = false)
    public String Rutina;

    @Element(required = false)
    public String Parametros;
}
