package com.example.santiagolopezgarcia.talleres.services.dto.carga.impresion;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_SECCIONIMPRESION")
public class SeccionImpresion extends BaseDtoCarga {

    public SeccionImpresion() {
        CodigoImpresion = "";
        CodigoSeccion = "";
        Descripcion = "";
        Rutina = "";
        Parametros = "";
    }

    @Element()
    public String CodigoImpresion;

    @Element()
    public String CodigoSeccion;

    @Element(required = false)
    public String Descripcion;

    @Element()
    public String Rutina;

    @Element(required = false)
    public String Parametros;
}
