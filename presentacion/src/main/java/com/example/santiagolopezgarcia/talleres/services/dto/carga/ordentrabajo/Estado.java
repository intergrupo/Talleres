package com.example.santiagolopezgarcia.talleres.services.dto.carga.ordentrabajo;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

@Root(name = "SIRIUS_ESTADO")
public class Estado extends BaseDtoCarga {

    public Estado() {
        CodigoEstado = "";
        Descripcion = "";
        GrupoEstado = "N";
    }

    @Element
    public String CodigoEstado;

    @Element
    public String Descripcion;

    @Element
    public String GrupoEstado;
}