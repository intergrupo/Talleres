package com.example.santiagolopezgarcia.talleres.services.dto.carga.administracion;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_MUNICIPIO")
public class Municipio extends BaseDtoCarga {

    public Municipio() {
        CodigoMunicipio = "";
        Descripcion = "";
    }

    @Element
    public String CodigoMunicipio;

    @Element
    public String Descripcion;
}
