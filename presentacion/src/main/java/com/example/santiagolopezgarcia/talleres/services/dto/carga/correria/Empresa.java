package com.example.santiagolopezgarcia.talleres.services.dto.carga.correria;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_EMPRESA")
public class Empresa extends BaseDtoCarga {

    public Empresa() {
        CodigoEmpresa = "";
        Descripcion = "";
        Logo = "";
    }

    @Element
    public String CodigoEmpresa;

    @Element(required = false)
    public String Descripcion;

    @Element(required = false)
    public String Logo;
}