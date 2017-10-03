package com.example.santiagolopezgarcia.talleres.services.dto.carga.acceso;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/3/17.
 */

@Root(name = "SIRIUS_OPCION")
public class Opcion extends BaseDtoCarga {

    public Opcion() {
        CodigoOpcion = "";
        Descripcion = "";
        Rutina = "";
        Parametros = "";
        Menu = "";
    }

    @Element
    public String CodigoOpcion;

    @Element
    public String Descripcion;

    @Element
    public String Rutina;

    @Element(required = false)
    public String Parametros;

    @Element
    public String Menu;
}