package com.example.santiagolopezgarcia.talleres.services.dto.carga.administracion;

import com.example.santiagolopezgarcia.talleres.services.dto.carga.BaseDtoCarga;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by santiagolopezgarcia on 10/3/17.
 */

@Root(name = "SIRIUS_MULTIOPCION")
public class MultiOpcion extends BaseDtoCarga {

    public MultiOpcion() {
        CodigoTipoOpcion = "";
        CodigoOpcion = "";
        Descripcion = "";
    }

    @Element
    public String CodigoTipoOpcion;

    @Element
    public String CodigoOpcion;

    @Element
    public String Descripcion;
}
