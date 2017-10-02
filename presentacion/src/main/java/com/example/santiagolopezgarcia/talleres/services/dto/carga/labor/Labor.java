package com.example.santiagolopezgarcia.talleres.services.dto.carga.labor;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_LABOR")
public class Labor extends BaseDtoCarga {

    public Labor() {
        CodigoLabor = "";
        Nombre = "";
        Rutina = "";
        Parametros = "";
    }

    @Element()
    public String CodigoLabor;

    @Element()
    public String Nombre;

    @Element(required = false)
    public String Rutina;

    @Element(required = false)
    public String Parametros;
}
