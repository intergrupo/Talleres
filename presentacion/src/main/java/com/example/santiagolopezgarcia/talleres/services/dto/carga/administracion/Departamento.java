package com.example.santiagolopezgarcia.talleres.services.dto.carga.administracion;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_DEPARTAMENTO")
public class Departamento extends BaseDtoCarga {

    public Departamento() {
        CodigoDepartamento = "";
        Descripcion = "";
    }

    @Element
    public String CodigoDepartamento;
    @Element
    public String Descripcion;
}